package jp.long_long_float.cuick.compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.ast.AtTestCase;
import jp.long_long_float.cuick.exception.CompileException;
import jp.long_long_float.cuick.exception.ConfigException;
import jp.long_long_float.cuick.exception.FileException;
import jp.long_long_float.cuick.exception.SemanticException;
import jp.long_long_float.cuick.exception.SyntaxException;
import jp.long_long_float.cuick.parser.Parser;
import jp.long_long_float.cuick.utility.ErrorHandler;
import jp.long_long_float.cuick.utility.Pair;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.ExampleMode;

public class Compiler {
    static public void main(String[] args) throws FileException {
        new Compiler("cuick").commandMain(args);
    }
    
    private final ErrorHandler errorHandler;
    
    public Compiler(String programName) {
        this.errorHandler = new ErrorHandler(programName);
    }
    
    public void commandMain(String[] args) throws FileException {
        Options opts = null;
        try {
            opts = Options.parse(args);
        }
        catch (CmdLineException err) {
            errorHandler.error(err.getMessage());
            errorHandler.error("cuickc --help");
            System.exit(1);
        }
        
        if(opts.isHelpFlag()) {
            System.out.println("cuickc " + opts.getParser().printExample(ExampleMode.ALL) + " source...");
            System.out.println();
            opts.getParser().printUsage(System.out);
            System.exit(0);
        }
        
        if(opts.isVarsionFlag()) {
            System.out.println("cuick 1.0");
            System.exit(0);
        }
        
        ConfigLoader cl = null;
        try {
            cl = new ConfigLoader(errorHandler);
        } catch (FileNotFoundException e) {
            errorHandler.error(Config.FILE_NAME + " is not existed!");
            throw new FileException("file error");
        } catch (IOException e) {
            errorHandler.error("IO error" + e.getMessage());
            throw new FileException("file error");
        }
        
        for(String sourceFile : opts.getSourceFiles()) {
            try {
                build(sourceFile, opts, cl);
            }
            catch (ConfigException ex) {
                errorHandler.error(ex.getMessage());
                System.exit(1);
            }
            catch (CompileException ex) {
                errorHandler.error(ex.getMessage());
                System.exit(1);
            }
        }
        System.exit(0);
    }
    
    private void build(String sourceFile, Options opts, ConfigLoader cl) throws CompileException {
        String destFile = sourceFile.replaceFirst(".cuick$", ".cpp");
        
        compile(sourceFile, destFile, opts);
        
        Map<String, String> constants = new HashMap<>();
        constants.put("FILE_BASENAME", sourceFile.replaceFirst(".cuick$", ""));
        constants.put("DEST_FILE", sourceFile.replaceFirst(".cuick$", ".cpp"));
        
        Config config = cl.load(constants);
        
        if(opts.isWithCompileFlag()) {
            //c++ to binary
            ProcessBuilder pb = new ProcessBuilder(config.compiler);
            try {
                Process p = pb.start();
                int i = p.waitFor();
                System.out.println(i);
            } catch (IOException | InterruptedException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }
        
        //auto test
        Table table = Table.getInstance();
        if(table.isEnabledTest()) {
            for(Pair<AtTestCase, AtTestCase> testCase : table.getTestCases()) {
                testCase.getFirst().fileName();
            }
        }
    }

    private void compile(String srcPath, String destPath, Options opts) throws CompileException{
        AST ast = parseFile(srcPath, opts);
        ast.dump();
        System.out.println("===============localResolve================");
        ast = localResolve(ast, opts);
        ast.dump();
        System.out.println("===============typeResolve================");
        ast = typeResolve(ast, opts);
        ast.dump();
        new ParentSetter(errorHandler).parentSet(ast);
        System.out.println("===============ASTTranslator================");
        new ASTTranslator(errorHandler).translate(ast);
        ast.dump();
        System.out.println("===============localResolve================");
        ast = localResolve(ast, opts);
        ast.dump();
        /*System.out.println("===============typeResolve================");
        ast = typeResolve(ast, opts);
        ast.dump();*/
        System.out.println("===============rename================");
        ast = rename(ast, opts);
        ast.dump();
        
        
        writeFile(destPath, ast, opts);
    }

    private void writeFile(String path, AST ast, Options opts) throws FileException {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(new File(path)));
            try {
                //writer.write(cb.toString());
                //System.out.println(codeGenerate(ast, opts));
                writer.write(codeGenerate(ast, opts));
            }
            finally {
                writer.close();
            }
        }
        catch (FileNotFoundException ex) {
            errorHandler.error("file not found: " + path);
            throw new FileException("file error");
        }
        catch (IOException ex) {
            errorHandler.error("IO error" + ex.getMessage());
            throw new FileException("file error");
        }
    }
    
    public AST parseFile(String path, Options opts) throws SyntaxException, FileException {
        return Parser.parseFile(new File(path), errorHandler);
    }
    
    public AST localResolve(AST ast, /*TypeTable types, */Options opts) throws SemanticException {
        new LocalResolver(errorHandler).resolve(ast);
        return ast;
    }
    
    public AST rename(AST ast, Options opts) {
        new Renamer(errorHandler).rename(ast);
        return ast;
    }
    
    public AST typeResolve(AST ast, Options opts) {
        new TypeResolver(errorHandler).resolve(ast);
        return ast;
    }
    
    public String codeGenerate(AST ast, Options opts) {
        return new CodeGenerator(errorHandler).generate(ast);
    }
}
