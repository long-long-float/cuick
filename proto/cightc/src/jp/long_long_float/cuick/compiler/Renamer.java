package jp.long_long_float.cuick.compiler;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.entity.Function;
import jp.long_long_float.cuick.entity.Parameter;
import jp.long_long_float.cuick.utility.ErrorHandler;

public class Renamer extends Visitor {
    
    public Renamer(ErrorHandler h) {
        super(h);
    }
    
    public void rename(AST ast) {
        for(Function func : ast.funcs()) {
            for(Parameter param : func.parameters()) {
                if(param.name().equals("this")) {
                    param.rename("this_");
                }
            }
        }
    }
    
}
