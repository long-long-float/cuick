package jp.long_long_float.cuick.entity;

import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.TypeNode;

public class Parameter extends Variable{
    protected ExprNode defaultValue;
    
    public Parameter(TypeNode type, String name, boolean isArray, ExprNode arraySize, ExprNode defaultValue) {
        super(type, name, null, isArray, arraySize, null);
        this.defaultValue = defaultValue;
    }

    public Parameter(TypeNode type, String name) {
        this(type, name, false, null, null);
    }
}
