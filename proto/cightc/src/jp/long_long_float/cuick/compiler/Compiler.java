package jp.long_long_float.cuick.compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
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
import jp.long_long_float.cuick.exception.FileException;
import jp.long_long_float.cuick.exception.SyntaxException;
import jp.long_long_float.cuick.parser.Parser;
import jp.long_long_float.cuick.type.Type;
import jp.long_long_float.cuick.utility.ErrorHandler;

public class Compiler {
    static public void main(String[] args) {
        try {
            AST ast = new Compiler("cuickc").parseFile(args[0]);
            ast.dump();
            
            Table table = Table.getInstance();
            System.out.println(table.getTuples());
            System.out.println(table.getFunctions());
            System.out.println(table.getBuiltInCodeStmt());
            
            CodeBuilder cb = new CodeBuilder();
            deployHeaders(cb);
            deployBuiltInCodes(cb);
            deployTuples(cb);
            deployFunctions(cb);
            //main
            Function main = new Function("int", "main");
            main.addArg("int", "argc").addArg("char**", "argv");
            for(StmtNode stmt : ast.declarations().stmts()) {
                main.addStmt(stmt.toString());
            }
            main.addStmt("return 0;");
            cb.addLine(main.toString());
            
            File file = new File(args[0] + ".cpp");
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            writer.print(cb.toString());
            writer.close();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
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
    
    static private void deployFunctions(CodeBuilder cb) {
        for(jp.long_long_float.cuick.entity.Function func : Table.getInstance().getFunctions()) {
            cb.addLine(func.toString());
        }
    }
    
    private final ErrorHandler errorHandler;
    
    public Compiler(String programName) {
        this.errorHandler = new ErrorHandler(programName);
    }
    
    public void commandMain(String[] args) {
        
    }
    
    public AST parseFile(String path) throws SyntaxException, FileException {
        return Parser.parseFile(new File(path), errorHandler);
    }
}
