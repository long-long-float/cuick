package jp.long_long_float.cuick.ast;

public abstract class AtInputAbstractVariableNode extends Node {

    protected Location location;
    
    public AtInputAbstractVariableNode(Location loc) {
        this.location = loc;
    }
    
    @Override
    public Location location() {
        return location;
    }
}
