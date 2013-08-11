package jp.long_long_float.cuick.compiler;

import java.io.File;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.exception.FileException;
import jp.long_long_float.cuick.exception.SyntaxException;
import jp.long_long_float.cuick.parser.Parser;
import jp.long_long_float.cuick.utility.ErrorHandler;

public class Compiler {
    static public void main(String[] args) {
        try {
            AST ast = new Compiler("cuickc").parseFile(args[0]);
            ast.dump();
            
        } catch (SyntaxException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (FileException e) {
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
