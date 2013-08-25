package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.type.CInt;

public class AtInputArrayVariableNode extends AtInputAbstractVariableNode {

    protected RangeNode range = null;
    
    public AtInputArrayVariableNode(Location loc, String name, RangeNode range) {
        super(loc, name);
        this.range = range;
    }
    
    public AtInputArrayVariableNode(Location loc, String name, ExprNode expr) {
        this(loc, name, new RangeNode(new LiteralNode(null, new CInt(), "0"), "...", expr));
    }
    
    public RangeNode range() {
        return range;
    }
    
    @Override
    protected void _dump(Dumper d) {
        d.printMember("name", varName());
        d.printMember("range", range);
    }

}
