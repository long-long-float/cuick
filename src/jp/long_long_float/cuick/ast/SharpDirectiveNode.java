package jp.long_long_float.cuick.ast;

public abstract class SharpDirectiveNode extends Node {

    private Location location;
    
    public SharpDirectiveNode(Location loc) {
        this.location = loc;
    }
    
    @Override
    public Location location() {
        return location;
    }

}
