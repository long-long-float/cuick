package jp.long_long_float.cuick.foreach;

import java.util.List;

import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.ForEachNode;

public class VariableSetEnumerable extends Enumerable {
    private List<ExprNode> exprs;
    
    public VariableSetEnumerable(List<ExprNode> exprs) {
        this.exprs = exprs;
    }

    @Override
    public String toString(ForEachNode forEachNode) {
        return toString();
    }
}
