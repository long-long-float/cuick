package jp.long_long_float.cuick.ast;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.entity.LocalScope;
import jp.long_long_float.cuick.entity.Variable;
import jp.long_long_float.cuick.utility.ListUtils;

public class BlockNode extends StmtNode {

    protected List<Variable> variables;
    protected List<StmtNode> stmts;
    protected LocalScope scope;
    
    public BlockNode(Location loc, List<Variable> vars, List<StmtNode> stmts) {
        super(loc);
        this.variables = vars != null ? vars : new ArrayList<Variable>();
        this.stmts = stmts != null ? stmts : new ArrayList<StmtNode>();
    }

    public List<StmtNode> stmts() {
        return stmts;
    }
    
    public void addStmtFront(StmtNode stmt) {
        stmts.add(0, stmt);
    }

    public void setStmt(List<StmtNode> stmts) {
        this.stmts = stmts;
    }
    
    /**
     * targetの位置にstmtsを追加します
     */
    public void insertStmts(StmtNode target, List<StmtNode> stmts) {
        this.stmts.addAll(this.stmts.indexOf(target), stmts);
    }
    
    @Override
    public BlockNode toBlockNode() {
        return this;
    }
    
    protected void _dump(Dumper d) {
        d.printNodeList("variables", variables);
        d.printNodeList("stmts", stmts);
    }

    public List<Variable> variables() {
        return variables;
    }

    public void setScope(LocalScope scope) {
        this.scope = scope;
    }
    
    public LocalScope scope() {
        return scope;
    }
    
    /**
     * 変数を定義します。DefvarNodeをstmtsに追加します
     */
    public void defineVariable(Variable var) {
        addStmtFront(new DefvarNode(null, var.type(), ListUtils.asList(var)));
    }
    
    @Override
    public BlockNode parentBlockNode(int depth) {
        return depth == 0 ? this : parent.parentBlockNode(depth - 1);
    }
    
    @Override
    public boolean isDefinedVariable(String name) {
        for(Variable var : variables) {
            if(var.name().equals(name)) return true;
        }
        return super.isDefinedVariable(name);
    }
}
