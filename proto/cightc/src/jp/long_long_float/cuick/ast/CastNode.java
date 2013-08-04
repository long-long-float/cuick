package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.type.Type;

public class CastNode extends ExprNode {

    protected TypeNode typeNode;
    protected ExprNode expr;
    
    public CastNode(TypeNode t, ExprNode expr) {
        this.typeNode = t;
        this.expr = expr;
    }
    
    public CastNode(Type t, ExprNode expr) {
        this(new TypeNode(t), expr);
    }
    
    @Override
    public Location location() {
        return typeNode.location();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("typeNode", typeNode);
        d.printMember("expr", expr);
    }

}
