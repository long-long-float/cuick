package jp.long_long_float.cuick.foreach;

import jp.long_long_float.cuick.ast.RangeNode;

public class RangeEnumerable extends Enumerable {
    private RangeNode range;
    
    public RangeEnumerable(RangeNode range) {
        this.range = range;
    }
}
