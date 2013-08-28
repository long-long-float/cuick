package jp.long_long_float.cuick.ast;

public class TypedefNode extends StmtNode {
    protected String real;
    protected String name;
    private ExprNode sizeExpr;
    
    public TypedefNode(Location loc, String real, String name, ExprNode sizeExpr) {
        super(loc);
        this.real = real;
        this.name = name;
        this.sizeExpr = sizeExpr;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("real", real);
        d.printMember("name", name);
        if(sizeExpr != null) d.printMember("sizeExpr", sizeExpr);
    }
}
