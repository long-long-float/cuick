package jp.long_long_float.cuick.type;


import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.utility.ListUtils;

public class CUnsignedInt extends BasicType implements IInteger {
    public CUnsignedInt() {
        this(null);
    }
    
    public CUnsignedInt(Location loc) {
        super(ListUtils.asList("unsigned", "int"), null);
    }
}
