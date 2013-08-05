package jp.long_long_float.cuick.ast;

public class AtWhileNode extends AtCommandNode {

    protected ExprNode cond;
    
    public AtWhileNode(Location loc, ExprNode cond) {
        super(loc);
        this.cond = cond;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("cond", cond);
    }

}
