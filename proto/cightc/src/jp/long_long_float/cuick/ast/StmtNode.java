package jp.long_long_float.cuick.ast;

abstract public class StmtNode extends Node {

    protected Location location;
    
    public StmtNode(Location loc) {
        this.location = loc;
    }
    
    @Override
    public Location location() {
        return location;
    }
}
