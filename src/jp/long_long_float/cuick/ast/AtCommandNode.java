package jp.long_long_float.cuick.ast;

abstract public class AtCommandNode extends Node {

    protected Location location;
    
    public AtCommandNode(Location loc) {
        this.location = loc;
    }
    
    @Override
    public Location location() {
        return location;
    }

}
