package jp.long_long_float.cuick.type;

import jp.long_long_float.cuick.ast.Location;

public class NamedType extends Type {
    protected String name;
    protected Location location;
    
    public NamedType(String name, Location loc) {
        this.name = name;
        this.location = loc;
    }
}
