package jp.long_long_float.cuick.compiler;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.ast.BlockNode;
import jp.long_long_float.cuick.entity.Function;
import jp.long_long_float.cuick.entity.Parameter;
import jp.long_long_float.cuick.entity.Variable;
import jp.long_long_float.cuick.type.NamedType;
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
        
        for(Function func : ast.funcs()) {
            func.body().accept(this);
        }
    }
    
    public Void visit(BlockNode node) {
        for(Variable var : node.variables()) var.accept(this);
        return null;
    }
    
    public Void visit(Variable ent) {
        if(ent.type().typeString().equals("tuple")) {
            ent.setType(new NamedType(Table.getInstance().getRealTupleName(ent.type()), null));
        }
        return null;
    }
    
    public Void visit(Parameter ent) {
        return null;
    }
    
}
