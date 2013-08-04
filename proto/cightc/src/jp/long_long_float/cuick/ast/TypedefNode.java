package jp.long_long_float.cuick.ast;

public class TypedefNode{
    protected String real;
    protected String name;
    private ExprNode sizeExpr;
    
    public TypedefNode(Location loc, String real, String name, ExprNode sizeExpr) {
        this.real = real;
        this.name = name;
        this.sizeExpr = sizeExpr;
    }
}
