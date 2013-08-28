package jp.long_long_float.cuick.type;

import jp.long_long_float.cuick.ast.Location;

public class CInt extends BasicType implements IInteger{

    public CInt() {
        this(null);
    }
    
    public CInt(Location loc) {
        super("int", loc);
    }

}
