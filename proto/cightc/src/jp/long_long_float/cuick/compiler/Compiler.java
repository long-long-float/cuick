package jp.long_long_float.cuick.compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import jp.long_long_float.cuick.ast.AST;
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
            
            //System.out.println(Table.getInstance().getTuples());
            
            CodeBuilder cb = new CodeBuilder();
            //headers
            String[] headers = new String[]{"iostream", "vector", "map", "algorithm"};
            for(String header : headers) {
                cb.addLine("#include<" + header + ">");
            }
            //tuples
            int tupleID = 0;
            List<Type> tuples = Table.getInstance().getTuples();
            for(Type tuple : tuples) {
                Struct struct = new Struct("tuple" + tupleID);
                tupleID++;
                int itemID = 0;
                for(Type member : tuple.getTemplateTypes()) {
                    String name = member.toString();
                    int index;
                    if((index = tuples.indexOf(member)) != -1) {
                        name = "tuple" + index;
                    }
                    struct.addMember(name, "item" + itemID);
                    itemID++;
                }
                cb.addLine(struct.generateCode());
            }
            //main
            Function main = new Function("int", "main");
            main.addStmt("return 0;");
            cb.addLine(main.generateCode());
            
            File file = new File(args[0] + ".cpp");
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            writer.print(cb.toString());
            writer.close();
            
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
