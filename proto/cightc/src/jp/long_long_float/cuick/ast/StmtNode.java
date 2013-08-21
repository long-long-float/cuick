package jp.long_long_float.cuick.ast;

import java.util.Arrays;

abstract public class StmtNode extends Node {

    protected Location location;
    
    public StmtNode(Location loc) {
        this.location = loc;
    }
    
    public BlockNode toBlockNode() {
        return new BlockNode(location, Arrays.asList(this));
    }
    
    @Override
    public Location location() {
        return location;
    }
}
