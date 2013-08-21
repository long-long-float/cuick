package jp.long_long_float.cuick.foreach;

import jp.long_long_float.cuick.ast.ForEachNode;
import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.ast.Node;
import jp.long_long_float.cuick.type.Type;

abstract public class Enumerable extends Node{
    
    protected Location location;
    
    private ForEachNode forEachNode;
    
    public Enumerable(Location loc) {
        this.location = loc;
    }
    
    public void setForEachNode(ForEachNode forEachNode) {
        this.forEachNode = forEachNode;
    }
    
    public ForEachNode forEachNode() {
        return this.forEachNode;
    }

    @Deprecated
    abstract public String toString(ForEachNode forEachNode);

    @Override
    public Location location() {
        return location;
    }

    public abstract Type getVarType();
}
