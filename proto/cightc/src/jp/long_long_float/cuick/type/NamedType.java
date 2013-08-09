package jp.long_long_float.cuick.type;

import jp.long_long_float.cuick.ast.Location;

public class NamedType extends Type {
    protected String name;
    
    public NamedType(String name, Location loc) {
        super(loc);
        this.name = name;
    }
    
    public String toString() {
        return name;
    }
}
