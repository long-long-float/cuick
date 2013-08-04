package jp.long_long_float.cuick.foreach;

import java.util.List;

import jp.long_long_float.cuick.ast.ExprNode;

public class VariableSetEnumerable extends Enumerable {
    private List<ExprNode> exprs;
    
    public VariableSetEnumerable(List<ExprNode> exprs) {
        this.exprs = exprs;
    }
}
