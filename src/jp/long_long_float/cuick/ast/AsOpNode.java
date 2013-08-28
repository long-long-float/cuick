package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.type.Type;

public class AsOpNode extends ExprNode {

    private ExprNode expr;
    private Type asType;
    
    public AsOpNode(ExprNode expr, Type type) {
        this.expr = expr;
        this.asType = type;
    }
    
    public ExprNode expr() {
        return expr;
    }
    
    public Type asType() {
        return asType;
    }

    @Override
    public Location location() {
        return expr.location();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("expr", expr);
        d.printMember("asType", asType);
    }

}
