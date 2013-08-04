package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.type.Type;

public class UnaryOpNode extends ExprNode {
    protected String operator;
    protected ExprNode expr;
    protected Type opType;
    
    public UnaryOpNode(String op, ExprNode expr) {
        this.operator = op;
        this.expr = expr;
    }
    
    @Override
    public Location location() {
        return expr.location();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("operator", operator);
        d.printMember("expr", expr);
    }

}
