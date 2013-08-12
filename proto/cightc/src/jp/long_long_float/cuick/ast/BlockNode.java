package jp.long_long_float.cuick.ast;

import java.util.List;

import jp.long_long_float.cuick.entity.LocalScope;
import jp.long_long_float.cuick.entity.Variable;

public class BlockNode extends StmtNode {

    protected List<Variable> variables;
    protected List<StmtNode> stmts;
    protected LocalScope scope;
    
    public BlockNode(Location loc, List<Variable> vars, List<StmtNode> stmts) {
        super(loc);
        this.variables = vars;
        this.stmts = stmts;
    }
    
    public List<StmtNode> stmts() {
        return stmts;
    }
    
    protected void _dump(Dumper d) {
        d.printNodeList("variables", variables);
        d.printNodeList("stmts", stmts);
    }
}
