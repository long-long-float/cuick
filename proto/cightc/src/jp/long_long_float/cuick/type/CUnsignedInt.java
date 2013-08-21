package jp.long_long_float.cuick.type;

import java.util.Arrays;

import jp.long_long_float.cuick.ast.Location;

public class CUnsignedInt extends BasicType implements IInteger {
    public CUnsignedInt() {
        this(null);
    }
    
    public CUnsignedInt(Location loc) {
        super(Arrays.asList("unsigned", "int"), null);
    }
}
