package jp.long_long_float.cuick.ast;

import java.util.List;

public class CaseNode extends StmtNode {

    protected List<ExprNode> values;
    protected BlockNode body;

    public CaseNode(Location loc, List<ExprNode> values, BlockNode body) {
        super(loc);
        this.values = values;
        this.body = body;
    }
    
    @Override
    protected void _dump(Dumper d) {
        d.printNodeList("values", values);
        d.printMember("body", body);
    }

    public List<ExprNode> values() {
        return values;
    }

    public StmtNode body() {
        return body;
    }

}
