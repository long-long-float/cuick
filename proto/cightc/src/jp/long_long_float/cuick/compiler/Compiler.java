package jp.long_long_float.cuick.compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import jp.long_long_float.cuick.ast.AST;
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
            
            File file = new File(args[0] + ".cpp");
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            //headers
            String[] headers = new String[]{"iostream", "vector", "map", "algorithm"};
            for(String header : headers) {
                writer.println("#include<" + header + ">");
            }
            //tuples
            int tupleID = 0;
            for(Type tuple : Table.getInstance().getTuples()) {
                Struct struct = new Struct("tuple" + tupleID);
                tupleID++;
                int itemID = 0;
                for(Type member : tuple.getTemplateTypes()) {
                    struct.addMember(member.typeString(), "item" + itemID);
                    itemID++;
                }
                writer.println(struct.generateCode());
            }
            //main
            Function main = new Function("int", "main");
            main.addStmt("return 0;");
            writer.println(main.generateCode());
            
            writer.close();
            
            System.out.println(Table.getInstance().getTuples());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
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
