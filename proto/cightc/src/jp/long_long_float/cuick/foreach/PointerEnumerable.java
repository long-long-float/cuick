package jp.long_long_float.cuick.foreach;

import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.RangeNode;

public class PointerEnumerable extends Enumerable {
    private ExprNode pointer;
    private RangeNode range;
    
    public PointerEnumerable(ExprNode pointer, RangeNode range) {
        this.pointer = pointer;
        this.range = range;
    }
}
