package jp.long_long_float.cuick.type;

import jp.long_long_float.cuick.ast.Location;

public abstract class Type {
    private Location location;
    
    public Type(Location location) {
        this.location = location;
    }
    
    public Location location() {
        return location;
    }
}
