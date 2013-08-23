package jp.long_long_float.cuick.compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.exception.CompileException;
import jp.long_long_float.cuick.exception.FileException;
import jp.long_long_float.cuick.exception.OptionParseError;
import jp.long_long_float.cuick.exception.SemanticException;
import jp.long_long_float.cuick.exception.SyntaxException;
import jp.long_long_float.cuick.parser.Parser;
import jp.long_long_float.cuick.utility.ErrorHandler;

public class Compiler {
    static public void main(String[] args) {
        new Compiler("cuick").commandMain(args);
    }
    
    private final ErrorHandler errorHandler;
    
    public Compiler(String programName) {
        this.errorHandler = new ErrorHandler(programName);
    }
    
    public void commandMain(String[] args) {
        Options opts = parseOptions(args);
        try {
            build(args[0], opts);
            System.exit(0);
        }
        catch (CompileException ex) {
            errorHandler.error(ex.getMessage());
            System.exit(1);
        }
    }
    
    private void build(String src, Options opts) throws CompileException {
        compile(src, src.replaceFirst(".cuick$", ".cpp"), opts);
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
        System.out.println("===============typeResolve================");
        ast = typeResolve(ast, opts);
        ast.dump();
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
                System.out.println(codeGenerate(ast, opts));
                //writer.write(codeGenerate(ast, opts));
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

    private Options parseOptions(String[] args) {
        try {
            return Options.parse(args);
        }
        catch (OptionParseError err) {
            errorHandler.error(err.getMessage());
            errorHandler.error("cuickc --help");
            System.exit(1);
            return null;
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
