package jp.long_long_float.cuick.entity;

import jp.long_long_float.cuick.ast.Dumpable;
import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.ast.TypeNode;
import jp.long_long_float.cuick.type.Type;

abstract public class Entity implements Dumpable{
    protected String name;
    protected TypeNode typeNode;
    
    public Entity(TypeNode type, String name) {
        this.name = name;
        this.typeNode = type;
    }
    
    public TypeNode typeNode() {
        return typeNode;
    }
    
    public Type type() {
        return typeNode.type();
    }
    
    public Location location() {
        return typeNode.location();
    }

    @Override
    public void dump(Dumper d) {
        d.printClass(this, location());
        _dump(d);
    }
    
    abstract protected void _dump(Dumper d);
}
