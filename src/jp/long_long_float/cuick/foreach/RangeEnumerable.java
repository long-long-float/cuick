package jp.long_long_float.cuick.foreach;

import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.RangeNode;

public class RangeEnumerable extends Enumerable {
    private RangeNode range;
    
    public RangeEnumerable(RangeNode range) {
        super(range.location());
        this.range = range;
    }
    
    public RangeNode range() {
        return range;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("range", range);
    }
}
