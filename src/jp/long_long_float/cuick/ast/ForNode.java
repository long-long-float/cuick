package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.cppStructure.ForStmt;
import jp.long_long_float.cuick.entity.Variable;

public class ForNode extends StmtNode {

    //protected Type varType;
    protected Variable var;
    protected ExprNode cond;
    protected ExprNode incr;
    protected BlockNode body;
    
    public ForNode(Location loc, Variable var, ExprNode cond, ExprNode incr, StmtNode body) {
        super(loc);
        this.var = var;
        this.cond = cond;
        this.incr = incr;//new ExprStmtNode(incr.location(), incr);
        this.body = body.toBlockNode();
        if(!this.body.isDefinedVariable(var.name())) this.body.variables().add(var);
    }
    
    @Override
    public String toString() {
        ForStmt forStmt = new ForStmt(var.type() + " " + var, cond.toString(), incr.toString());
        forStmt.setBody(body.toString());
        return forStmt.toString();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("var", var);
        d.printMember("cond", cond);
        d.printMember("incr", incr);
        d.printMember("body", body);
    }
    
    public Variable var() {
        return var;
    }

    public ExprNode cond() {
        return cond;
    }

    public ExprNode incr() {
        return incr;
    }

    public BlockNode body() {
        return body;
    }
}
