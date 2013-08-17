package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.cppStructure.ForStmt;
import jp.long_long_float.cuick.entity.Variable;
import jp.long_long_float.cuick.type.Type;

public class ForNode extends StmtNode {

    protected Type varType;
    protected Variable var;
    protected ExprNode cond;
    protected ExprNode incr;
    protected StmtNode body;
    
    public ForNode(Location loc, Type type, Variable var, ExprNode cond, ExprNode incr, StmtNode body) {
        super(loc);
        this.varType = type;
        this.var = var;
        this.cond = cond;
        this.incr = incr;//new ExprStmtNode(incr.location(), incr);
        this.body = body;
    }
    
    @Override
    public String toString() {
        ForStmt forStmt = new ForStmt(varType + " " + var, cond.toString(), incr.toString());
        forStmt.setBody(body.toString());
        return forStmt.toString();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("varType", varType);
        d.printMember("var", var);
        d.printMember("cond", cond);
        d.printMember("incr", incr);
        d.printMember("body", body);
    }

    public ExprNode cond() {
        return cond;
    }

    public ExprNode incr() {
        return incr;
    }

    public StmtNode body() {
        return body;
    }
}
