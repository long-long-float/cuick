package jp.long_long_float.cuick.entity;

import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.TypeNode;

public class Variable extends Entity {

    protected ExprNode initializer;
    
    public Variable(TypeNode type, String name, ExprNode init) {
        super(type, name);
        this.initializer = init;
    }
    
    @Override
    protected void _dump(Dumper d) {
        // TODO 自動生成されたメソッド・スタブ

    }

}
