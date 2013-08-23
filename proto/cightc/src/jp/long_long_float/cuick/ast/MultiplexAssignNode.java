package jp.long_long_float.cuick.ast;

import java.util.List;

public class MultiplexAssignNode extends StmtNode {

    protected List<ExprNode> lhses, rhses;
    
    public MultiplexAssignNode(List<ExprNode> lhses, List<ExprNode> rhses) {
        super(lhses.get(0).location());
        this.lhses = lhses;
        this.rhses = rhses;
    }
    
    public List<ExprNode> lhses() {
        return lhses;
    }
    
    public List<ExprNode> rhses() {
        return rhses;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("lhses", lhses);
        d.printMember("rhses", rhses);
    }

}
