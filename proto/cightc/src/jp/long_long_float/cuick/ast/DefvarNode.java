package jp.long_long_float.cuick.ast;

import java.util.List;

import jp.long_long_float.cuick.entity.Variable;
import jp.long_long_float.cuick.type.Type;

public class DefvarNode extends StmtNode {

    protected List<Variable> vars;
    protected Type type;
    
    public DefvarNode(Location loc, Type type, List<Variable> vars) {
        super(loc);
        this.vars = vars;
        this.type = type;
    }
    
    public List<Variable> vars() {
        return vars;
    }
    
    public Type type() {
        return type;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("vars", vars);
    }

}
