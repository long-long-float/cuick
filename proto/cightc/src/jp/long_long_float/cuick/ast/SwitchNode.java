package jp.long_long_float.cuick.ast;

import java.util.List;

public class SwitchNode extends StmtNode {

    protected ExprNode cond;
    protected List<CaseNode> cases;

    public SwitchNode(Location loc, ExprNode cond, List<CaseNode> cases) {
        super(loc);
        this.cond = cond;
        this.cases = cases;
    }
    
    @Override
    protected void _dump(Dumper d) {
        d.printMember("cond", cond);
        d.printNodeList("cases", cases);
    }

}
