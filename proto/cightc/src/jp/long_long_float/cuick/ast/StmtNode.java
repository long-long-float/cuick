package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.utility.ListUtils;


abstract public class StmtNode extends Node {

    protected Location location;
    
    public StmtNode(Location loc) {
        this.location = loc;
    }
    
    public BlockNode toBlockNode() {
        return new BlockNode(location, null, ListUtils.asList(this));
    }
    
    public String getIdentityName(String name) {
        int id = 0;
        for(;isDefinedVariable(name + id);id++) ;
        return name;
    }
    
    @Override
    public Location location() {
        return location;
    }
}
