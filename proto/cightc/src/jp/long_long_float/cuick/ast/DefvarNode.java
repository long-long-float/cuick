package jp.long_long_float.cuick.ast;

import java.util.List;

import jp.long_long_float.cuick.entity.Variable;
import jp.long_long_float.cuick.type.Type;

import org.apache.commons.lang3.StringUtils;

public class DefvarNode extends StmtNode {

    protected List<Variable> vars;
    protected Type type;
    
    public DefvarNode(Location loc, Type type, List<Variable> vars) {
        super(loc);
        this.vars = vars;
        this.type = type;
    }
    
    @Override
    public String toString() {
        return type.toString() + " " + StringUtils.join(vars, ", ") + ";";
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("vars", vars);
    }

    public List<Variable> vars() {
        return vars;
    }

}
