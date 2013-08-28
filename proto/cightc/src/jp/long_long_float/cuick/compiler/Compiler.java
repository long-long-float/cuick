package jp.long_long_float.cuick.compiler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
import jp.long_long_float.cuick.utility.FileUtils;
import jp.long_long_float.cuick.utility.InputThread;
import jp.long_long_float.cuick.utility.Pair;

import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.ExampleMode;

import scala.Tuple3;

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
    
    private Tuple3<String, String, Integer> exec(String[] commands, String input) {
        //StringBuilder stdout = new StringBuilder(), stderr = new StringBuilder();
        
        //DefaultExecutor exec = new DefaultExecutor();
        /*
        PipedOutputStream pos = new PipedOutputStream();
        PipedOutputStream poserr = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(pos, poserr, pis);
        exec.setStreamHandler(streamHandler);
        
        PipedInputStream pis1 = null, piserr = null;
        PipedOutputStream pos1 = null;
        */
        /*
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        CommandLine commandLine = new CommandLine(args[0]);
        if(args.length >= 1) {
            commandLine.addArguments(Arrays.copyOfRange(args, 1, args.length));
        }

        String out = "", err = "";
        try (InputPipe stdout = new InputPipe(); InputPipe stderr = new InputPipe(); 
                OutputPipe stdin = new OutputPipe()) {
            
            PumpStreamHandler streamHandler = null;
            if(input != null) {
                streamHandler = new PumpStreamHandler(
                    stdout.getPipedOutputStream(), stderr.getPipedOutputStream(),
                    stdin.getPipedInputStream());
            }
            else {
                streamHandler = new PumpStreamHandler(
                        stdout.getPipedOutputStream(), stderr.getPipedOutputStream());
            }
            exec.setStreamHandler(streamHandler);
            
            exec.execute(commandLine, resultHandler);
            
            if(input != null) {
                stdin.write(input);
                
            }
            
            resultHandler.waitFor();
            
            

            String sepa = System.getProperty("line.separator");
            out = StringUtils.join(stdout, sepa);
            err = StringUtils.join(stderr, sepa);

            resultHandler.waitFor();
            
        }  catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            errorHandler.error("interrupted!");
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }*/
        /*
        BufferedReader br = null, brerr = null;
        try {
            exec.execute(commandLine, resultHandler);
            //pis1 = new PipedInputStream(pos);
            br = new BufferedReader(new InputStreamReader(new PipedInputStream(pos)));
            piserr = new PipedInputStream(poserr);
            brerr = new BufferedReader(new InputStreamReader(piserr));
            
            resultHandler.waitFor();
            
            String sepa = System.getProperty("line.separator");
            while(br.ready()) {
                stdout.append(br.readLine() + sepa);
            }
            while(brerr.ready()) {
                stderr.append(brerr.readLine() + sepa);
            }
            
            resultHandler.waitFor();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            errorHandler.error("interrupted!");
            return null;
        } finally {
            try {
                pis1.close();
                br.close();
            } catch (IOException e) {
            }
            try {
                piserr.close();
                brerr.close();
            }catch (IOException e) {
            }
        }
        */
        //int code = resultHandler.getExitValue();
        
        //return new Tuple3<String, String, Integer>(stdout.toString(), stderr.toString(), code);
        //return new Tuple3<String, String, Integer>(out, err, code);
        
        ProcessBuilder pb = new ProcessBuilder(commands);
        
        Process process = null;
        try {
            process = pb.start();
        } catch(IOException ex) {
            ex.printStackTrace();
            return null;
        }
        
        OutputStream pos = process.getOutputStream();
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(pos)));
        
        InputThread outThread = new InputThread(process.getInputStream());
        InputThread errThread = new InputThread(process.getErrorStream());
        outThread.start();
        errThread.start();
        
        if(input != null) {
            pw.println(input);
            pw.close();
        }
        /*
        try (InputStream stdout = new InputStream(process.getInputStream());
                InputStream stderr = new InputStream(process.getErrorStream())) {
            String sepa = System.getProperty("line.separator");
            out = StringUtils.join(stdout, sepa);
            err = StringUtils.join(stderr, sepa);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        */
        try {
            process.waitFor();
            outThread.join();
            errThread.join();
        } catch (InterruptedException e) {
            errorHandler.error("interrupted!");
            return null;
        }
        
        String sepa = System.getProperty("line.separator");
        String out = StringUtils.join(outThread.getBuffer(), sepa);
        String err = StringUtils.join(errThread.getBuffer(), sepa);
        int code = process.exitValue();
        return new Tuple3<String, String, Integer>(out, err, code);
    }
    
    public void sample() {

        String command = "test.exe";

        List<String> args = new LinkedList<String>();

        args.add("1");
        args.add("2");
        args.add("0");

        ProcessBuilder pb = new ProcessBuilder(command);
        java.lang.Process process = null;

        try {
            process = pb.start();
        } catch (IOException ex) {
            //--
        }
        OutputStream os = process.getOutputStream();
        PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(os)));

        final InputStream is = process.getInputStream();
        new Thread(new Runnable() {
            public void run() {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (java.io.IOException e) {
                }
            }
        }).start();

        for (String arg : args) {
            pw.println(arg);
        }

        pw.close();

        int returnCode = -1;
        try {
            returnCode = process.waitFor();
        } catch (InterruptedException ex) {
            //--
        }
        System.out.println(returnCode);
    }
    
    private void build(String sourceFile, Options opts, ConfigLoader cl) throws CompileException {
        String destFile = sourceFile.replaceFirst(".cuick$", ".cpp");
        
        compile(sourceFile, destFile, opts);
        
        Map<String, String> constants = new HashMap<>();
        constants.put("FILE_BASENAME", sourceFile.replaceFirst(".cuick$", ""));
        constants.put("DEST_FILE", sourceFile.replaceFirst(".cuick$", ".cpp"));
        
        Config config = cl.load(constants);
        
        if(opts.isWithCompileFlag()) {
            Tuple3<String, String, Integer> ret = exec(config.compiler, null);
            if(ret == null) return;
            if(!ret._1().isEmpty()) System.out.println(ret._1());
            if(!ret._2().isEmpty()) System.err.println(ret._2());
            if(ret._3() != 0) {
                errorHandler.error(config.compiler[0] + " returned " + ret._3());
                return;
            }
        }
        
        //auto test
        Table table = Table.getInstance();
        if(table.isEnabledTest()) {
            for(Pair<AtTestCase, AtTestCase> testCase : table.getTestCases()) {
                System.out.println(config.test.exefile);
                String input = "", output = "";
                try {
                    input = FileUtils.readFromFile(testCase.getFirst().fileName());
                    output = FileUtils.readFromFile(testCase.getSecond().fileName());
                } catch (IOException e) {
                    // TODO 自動生成された catch ブロック
                    e.printStackTrace();
                    continue;
                }
                Tuple3<String, String, Integer> ret = exec(new String[] {config.test.exefile}, input);
                if(ret == null) continue;
                
                System.out.println("stdout => " + ret._1());
                System.err.println("stderr => " + ret._2());
                System.err.println("exit value => " + ret._3());
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
        /*System.out.println("===============localResolve================");
        ast = localResolve(ast, opts);
        ast.dump();
        System.out.println("===============typeResolve================");
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
