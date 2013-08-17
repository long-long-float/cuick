package jp.long_long_float.cuick.ast;

import java.util.List;

import jp.long_long_float.cuick.cppStructure.CodeBuilder;
import jp.long_long_float.cuick.cppStructure.CodeBuilder.BlockCallback;
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
    
    public BlockNode(Location loc, List<StmtNode> stmts) {
        super(loc);
        this.stmts = stmts;
    }

    public List<StmtNode> stmts() {
        return stmts;
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
}
