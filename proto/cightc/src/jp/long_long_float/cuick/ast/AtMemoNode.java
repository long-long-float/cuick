package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.entity.Function;

public class AtMemoNode extends AtCommandNode {

    protected ExprNode hash, max;
    protected Function func;
    
    public AtMemoNode(Location loc, ExprNode hash, ExprNode max, Function func) {
        super(loc);
        this.hash = hash;
        this.max = max;
        this.func = func;
    }
    
    @Override
    protected void _dump(Dumper d) {
        d.printMember("hash", hash);
        d.printMember("max", max);
        d.printMember("func", func);
    }

}
