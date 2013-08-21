package jp.long_long_float.cuick.entity;

import jp.long_long_float.cuick.ast.ASTVisitor;
import jp.long_long_float.cuick.ast.Dumpable;
import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.ast.TypeNode;
import jp.long_long_float.cuick.compiler.Acceptable;
import jp.long_long_float.cuick.type.Type;

abstract public class Entity implements Dumpable, Acceptable{
    protected String name;
    protected TypeNode typeNode;
    protected long nRefered;
    
    public Entity(TypeNode type, String name) {
        this.name = name;
        this.typeNode = type;
        this.nRefered = 0;
    }
    
    public String name() {
        return name;
    }
    
    public TypeNode typeNode() {
        return typeNode;
    }
    
    public Type type() {
        return typeNode.type();
    }
    
    public void setType(Type type) {
        typeNode.setType(type);
    }
    
    public Location location() {
        return typeNode.location();
    }
    
    public void refered() {
        nRefered++;
    }
    
    public boolean isRefered() {
        return nRefered > 0;
    }

    @Override
    public void dump(Dumper d) {
        d.printClass(this, location());
        _dump(d);
    }
    
    abstract protected void _dump(Dumper d);

    public boolean isDefined() {
        return true;
    }
    
    public <S, E> E accept(ASTVisitor<S, E> visitor) {
        return visitor.visit(this);
    }
    
    @Deprecated
    public String toString() {
        throw new Error("toString() is deprecated!");
    }
    
}
