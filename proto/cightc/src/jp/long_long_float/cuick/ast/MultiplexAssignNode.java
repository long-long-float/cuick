package jp.long_long_float.cuick.ast;

import java.util.List;

public class MultiplexAssignNode extends ExprNode {

    protected List<ExprNode> lhses, rhses;
    
    public MultiplexAssignNode(List<ExprNode> lhses, List<ExprNode> rhses) {
        this.lhses = lhses;
        this.rhses = rhses;
    }
    
    @Override
    public Location location() {
        return lhses.get(0).location();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("lhses", lhses);
        d.printMember("rhses", rhses);
    }

}
