package jp.long_long_float.cuick.ast;

import java.util.List;

public class AtInputNode extends StmtNode {

    protected List<AtInputAbstractVariableNode> vars;
    
    public AtInputNode(Location loc, List<AtInputAbstractVariableNode> vars) {
        super(loc);
        this.vars = vars;
    }
    
    public List<AtInputAbstractVariableNode> vars() {
        return vars;
    }
    
    @Override
    protected void _dump(Dumper d) {
        d.printMember("vars", vars);
    }

}
