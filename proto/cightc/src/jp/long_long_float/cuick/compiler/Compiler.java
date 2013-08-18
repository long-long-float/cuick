package jp.long_long_float.cuick.compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.ast.BuiltInCodeStmt;
import jp.long_long_float.cuick.ast.StmtNode;
import jp.long_long_float.cuick.cppStructure.CodeBuilder;
import jp.long_long_float.cuick.cppStructure.Function;
import jp.long_long_float.cuick.cppStructure.Struct;
import jp.long_long_float.cuick.exception.CompileException;
import jp.long_long_float.cuick.exception.FileException;
import jp.long_long_float.cuick.exception.OptionParseError;
import jp.long_long_float.cuick.exception.SemanticException;
import jp.long_long_float.cuick.exception.SyntaxException;
import jp.long_long_float.cuick.parser.Parser;
import jp.long_long_float.cuick.type.Type;
import jp.long_long_float.cuick.utility.ErrorHandler;

public class Compiler {
    static public void main(String[] args) {
        new Compiler("cuick").commandMain(args);
    }
    
    static private void deployHeaders(CodeBuilder cb) {
        String[] headers = new String[]{"iostream", "vector", "map", "algorithm"};
        for(String header : headers) {
            cb.addLine("#include<" + header + ">");
        }
    }
    
    static private void deployBuiltInCodes(CodeBuilder cb) {
        for(BuiltInCodeStmt code : Table.getInstance().getBuiltInCodeStmt()) {
            cb.addLine(code.toString());
        }
    }
    
    static private void deployTuples(CodeBuilder cb) {
        List<Type> tuples = Table.getInstance().getTuples();
        //tupleの依存関係を作る
        List<List<Integer>> tupleTree = new ArrayList<List<Integer>>(tuples.size());
        List<Integer> parentNums = new ArrayList<Integer>(tuples.size());
        for(int i = 0;i < tuples.size();i++) {
            tupleTree.add(new ArrayList<Integer>());
            parentNums.add(0);
        }
        for(int tupleID = 0;tupleID < tuples.size();tupleID++) {
            for(Type member : tuples.get(tupleID).getTemplateTypes()) {
                int index;
                if((index = tuples.indexOf(member)) != -1) {
                    tupleTree.get(tupleID).add(index);
                    parentNums.set(index, parentNums.get(index) + 1);
                }
            }
        }
        List<Integer> root = new ArrayList<Integer>();
        for(int i = 0;i < parentNums.size();i++) {
            if(parentNums.get(i) == 0) root.add(i);
        }
        //木の末端にあるものから配置
        Queue<Integer> queue = new LinkedList<Integer>();
        for(Integer rootID : root) {
            queue.offer(rootID);
        }
        List<Integer> deployedTupleIDs = new ArrayList<Integer>();
        while(!queue.isEmpty()) {
            int tupleID = queue.poll();
            if(!deployedTupleIDs.contains(tupleID)) {
                deployedTupleIDs.add(tupleID);
            }
            for(Integer i : tupleTree.get(tupleID)) {
                queue.offer(i);
            }
        }
        for(int i = deployedTupleIDs.size() - 1;i >= 0;i--) {
            int tupleID = deployedTupleIDs.get(i);
            Struct struct = new Struct("tuple" + tupleID);
            List<Type> templTypes = tuples.get(tupleID).getTemplateTypes();
            for(int memberID = 0;memberID < templTypes.size();memberID++) {
                Type member = templTypes.get(memberID);
                String name = member.toString();
                int index;
                if((index = tuples.indexOf(member)) != -1) {
                    name = "tuple" + index;
                }
                struct.addMember(name, "item" + memberID);
            }
            cb.addLine(struct.toString());
        }
    }
    
    static private void deployFunctions(CodeBuilder cb, AST ast) {
        /*for(jp.long_long_float.cuick.entity.Function func : Table.getInstance().getFunctions()) {
            cb.addLine(func.toString());
        }*/
        for(jp.long_long_float.cuick.entity.Function func : ast.funcs()) {
            cb.addLine(func.toString());
        }
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
        //ast.dump();
        ast = semanticAnalyze(ast, opts);
        ast = rename(ast, opts);
        ast.dump();
        writeFile(destPath, ast);
    }

    private void writeFile(String path, AST ast) throws FileException {
        Table table = Table.getInstance();
        //System.out.println(table.getTuples());
        //System.out.println(table.getFunctions());
        //System.out.println(table.getBuiltInCodeStmt());
        
        CodeBuilder cb = new CodeBuilder();
        deployHeaders(cb);
        deployBuiltInCodes(cb);
        deployTuples(cb);
        deployFunctions(cb, ast);
        //main
        Function main = new Function("int", "main");
        main.addArg("int", "argc").addArg("char**", "argv");
        for(StmtNode stmt : ast.stmts()) {
            main.addStmt(stmt.toString());
        }
        main.addStmt("return 0;");
        cb.addLine(main.toString());
        
        
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(new File(path)));
            try {
                writer.write(cb.toString());
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
    
    public AST semanticAnalyze(AST ast, /*TypeTable types, */Options opts) throws SemanticException {
        new LocalResolver(errorHandler).resolve(ast);
        return ast;
    }
    
    public AST rename(AST ast, Options opts) {
        new Renamer(errorHandler).rename(ast);
        return ast;
    }
}
