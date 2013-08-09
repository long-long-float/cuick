package jp.long_long_float.cuick.compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.exception.FileException;
import jp.long_long_float.cuick.exception.SyntaxException;
import jp.long_long_float.cuick.parser.Parser;
import jp.long_long_float.cuick.utility.ErrorHandler;

public class Compiler {
    static public void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(args[0]));
            for(String line = br.readLine();line != null;line = br.readLine()) {
                System.out.println(line);
            }
            br.close();
        } catch (FileNotFoundException e1) {
            // TODO 自動生成された catch ブロック
            e1.printStackTrace();
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        try {
            new Compiler("cuickc").parseFile(args[0]).dump();
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
