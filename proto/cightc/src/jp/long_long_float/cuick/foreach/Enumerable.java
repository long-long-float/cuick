package jp.long_long_float.cuick.foreach;

import jp.long_long_float.cuick.ast.ForEachNode;
import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.ast.Node;

abstract public class Enumerable extends Node{
    
    protected Location location;
    
    public Enumerable(Location loc) {
        this.location = loc;
    }

    abstract public String toString(ForEachNode forEachNode);

    @Override
    public Location location() {
        return location;
    }
}
