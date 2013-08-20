package jp.long_long_float.cuick.type;

import jp.long_long_float.cuick.ast.Location;

public class CChar extends BasicType implements IInteger{
    public CChar() {
        this(null);
    }
    
    public CChar(Location loc) {
        super("char", loc);
    }
}
