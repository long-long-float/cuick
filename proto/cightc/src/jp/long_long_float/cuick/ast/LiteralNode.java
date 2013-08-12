package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.type.Type;

public class LiteralNode extends ExprNode {
    protected Location location;
    protected TypeNode typeNode;
    protected String value;
    
    public LiteralNode(Location loc, Type type, String value) {
        super();
        this.location = loc;
        this.typeNode = new TypeNode(type);
        this.value = value;
    }
    
    @Override
    public Location location() {
        return location;
    }
    
    @Override
    public String toString() {
        return value;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("value", value);
    }

}
