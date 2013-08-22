package jp.long_long_float.cuick.ast;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.cppStructure.CodeBuilder;
import jp.long_long_float.cuick.cppStructure.CodeBuilder.BlockCallback;
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
        this.stmts = stmts;
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
    
    @Override
    public BlockNode toBlockNode() {
        return this;
    }
    
    @Override
    public String toString() {
        CodeBuilder cb = new CodeBuilder();
        //System.out.println(CodeContext.getInstance().getIndent());
        cb.block(new BlockCallback() {
            @Override
            public void call(CodeBuilder cb) {
                for(StmtNode stmt : stmts) {
                    cb.addLine(stmt.toString());
                }
            }
        }, true);
        return cb.toString();
    }
    
    protected void _dump(Dumper d) {
        //d.printNodeList("variables", variables);
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
    
    public void defineVariable(Variable var) {
        addStmtFront(new DefvarNode(null, var.type(), ListUtils.asList(var)));
        variables.add(var);
    }
}
