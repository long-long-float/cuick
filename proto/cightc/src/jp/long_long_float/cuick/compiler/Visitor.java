package jp.long_long_float.cuick.compiler;

import java.util.List;

import jp.long_long_float.cuick.ast.ASTVisitor;
import jp.long_long_float.cuick.ast.AddressNode;
import jp.long_long_float.cuick.ast.ArefNode;
import jp.long_long_float.cuick.ast.AsOpNode;
import jp.long_long_float.cuick.ast.AssignNode;
import jp.long_long_float.cuick.ast.AtInputNode;
import jp.long_long_float.cuick.ast.AtWhileNode;
import jp.long_long_float.cuick.ast.BinaryOpNode;
import jp.long_long_float.cuick.ast.BlockNode;
import jp.long_long_float.cuick.ast.BreakNode;
import jp.long_long_float.cuick.ast.BuiltInCode;
import jp.long_long_float.cuick.ast.BuiltInCodeStmt;
import jp.long_long_float.cuick.ast.CaseNode;
import jp.long_long_float.cuick.ast.CastNode;
import jp.long_long_float.cuick.ast.CondExprNode;
import jp.long_long_float.cuick.ast.ContinueNode;
import jp.long_long_float.cuick.ast.DefvarNode;
import jp.long_long_float.cuick.ast.DereferenceNode;
import jp.long_long_float.cuick.ast.DoWhileNode;
import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.ExprStmtNode;
import jp.long_long_float.cuick.ast.ForEachNode;
import jp.long_long_float.cuick.ast.ForNode;
import jp.long_long_float.cuick.ast.FuncallNode;
import jp.long_long_float.cuick.ast.IfNode;
import jp.long_long_float.cuick.ast.LiteralNode;
import jp.long_long_float.cuick.ast.MemberNode;
import jp.long_long_float.cuick.ast.MultiplexAssignNode;
import jp.long_long_float.cuick.ast.OpAssignNode;
import jp.long_long_float.cuick.ast.PowerOpNode;
import jp.long_long_float.cuick.ast.PrefixOpNode;
import jp.long_long_float.cuick.ast.PtrMemberNode;
import jp.long_long_float.cuick.ast.RangeNode;
import jp.long_long_float.cuick.ast.ReturnNode;
import jp.long_long_float.cuick.ast.SizeofExprNode;
import jp.long_long_float.cuick.ast.SizeofTypeNode;
import jp.long_long_float.cuick.ast.StaticMemberNode;
import jp.long_long_float.cuick.ast.StmtNode;
import jp.long_long_float.cuick.ast.StringLiteralNode;
import jp.long_long_float.cuick.ast.SuffixOpNode;
import jp.long_long_float.cuick.ast.SwitchNode;
import jp.long_long_float.cuick.ast.UnaryOpNode;
import jp.long_long_float.cuick.ast.VariableNode;
import jp.long_long_float.cuick.ast.WhileNode;
import jp.long_long_float.cuick.entity.Variable;
import jp.long_long_float.cuick.foreach.Enumerable;
import jp.long_long_float.cuick.foreach.PointerEnumerable;
import jp.long_long_float.cuick.foreach.RangeEnumerable;
import jp.long_long_float.cuick.foreach.VariableSetEnumerable;
import jp.long_long_float.cuick.utility.ErrorHandler;

public class Visitor extends ASTVisitor<Void, Void> {
    
    protected Visitor(ErrorHandler h) {
        super(h);
    }
    
    protected void visitStmt(StmtNode stmt) {
        if(stmt != null) {
            stmt.accept(this);
        }
    }
    
    protected void visitStmts(List< ? extends StmtNode> stmts) {
        for(StmtNode s : stmts) {
            visitStmt(s);
        }
    }
    
    protected void visitExpr(ExprNode expr) {
        expr.accept(this);
    }
    
    protected void visitExprs(List<? extends ExprNode> exprs) {
        for (ExprNode e : exprs) {
            visitExpr(e);
        }
    }
    
    private void visitEnume(Enumerable enumerable) {
        enumerable.accept(this);
    }
    
    //at commands
    
    public Void visit(AtInputNode node) {
        return null;
    }
    
    public Void visit(AtWhileNode node) {
        visitExpr(node.cond());
        return null;
    }
    
    //statements
    
    public Void visit(BuiltInCodeStmt node) {
        return null;
    }
    
    public Void visit(BuiltInCode node) {
        return null;
    }
    
    public Void visit(MultiplexAssignNode node) {
        visitExprs(node.lhses());
        visitExprs(node.rhses());
        return null;
    }
    
    public Void visit(DefvarNode node) {
        for(Variable var : node.vars()) {
            visitExprs(var.init());
        }
        return null;
    }
    
    public Void visit(BlockNode node) {
        visitStmts(node.stmts());
        return null;
    }
    
    public Void visit(ExprStmtNode node) {
        visitExpr(node.expr());
        return null;
    }
    
    public Void visit(IfNode n) {
        visitExpr(n.cond());
        visitStmt(n.thenBody());
        if (n.elseBody() != null) {
            visitStmt(n.elseBody());
        }
        return null;
    }

    public Void visit(SwitchNode n) {
        visitExpr(n.cond());
        visitStmts(n.cases());
        return null;
    }

    public Void visit(CaseNode n) {
        visitExprs(n.values());
        visitStmt(n.body());
        return null;
    }

    public Void visit(WhileNode n) {
        visitExpr(n.cond());
        visitStmt(n.body());
        return null;
    }

    public Void visit(DoWhileNode n) {
        visitStmt(n.body());
        visitExpr(n.cond());
        return null;
    }

    public Void visit(ForNode n) {
        //visitStmt(n.init());
        visitExpr(n.cond());
        visitExpr(n.incr());
        visitStmt(n.body());
        return null;
    }
    
    public Void visit(ForEachNode n) {
        visitStmt(n.body());
        visitEnume(n.enumerable());
        if(n.lastBody() != null) {
            visitStmt(n.lastBody());
        }
        return null;
    }

    public Void visit(BreakNode n) {
        return null;
    }

    public Void visit(ContinueNode n) {
        return null;
    }

    /*
    public Void visit(GotoNode n) {
        return null;
    }

    public Void visit(LabelNode n) {
        visitStmt(n.stmt());
        return null;
    }
    */

    public Void visit(ReturnNode n) {
        if (n.expr() != null) {
            visitExpr(n.expr());
        }
        return null;
    }
    
    //expressions
    

    public Void visit(CondExprNode n) {
        visitExpr(n.cond());
        visitExpr(n.thenExpr());
        visitExpr(n.elseExpr());
        return null;
    }

    /*
    public Void visit(LogicalOrNode node) {
        visitExpr(node.left());
        visitExpr(node.right());
        return null;
    }

    public Void visit(LogicalAndNode node) {
        visitExpr(node.left());
        visitExpr(node.right());
        return null;
    }
    */

    public Void visit(AssignNode n) {
        visitExpr(n.lhs());
        visitExpr(n.rhs());
        return null;
    }

    public Void visit(OpAssignNode n) {
        visitExpr(n.lhs());
        visitExpr(n.rhs());
        return null;
    }

    public Void visit(BinaryOpNode n) {
        visitExpr(n.left());
        visitExpr(n.right());
        return null;
    }
    
    public Void visit(PowerOpNode node) {
        visitExpr(node.left());
        visitExpr(node.right());
        return null;
    }

    public Void visit(UnaryOpNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(PrefixOpNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(SuffixOpNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(FuncallNode node) {
        visitExpr(node.expr());
        visitExprs(node.args());
        return null;
    }

    public Void visit(ArefNode node) {
        visitExpr(node.expr());
        visitExpr(node.index());
        return null;
    }

    public Void visit(MemberNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(PtrMemberNode node) {
        visitExpr(node.expr());
        return null;
    }
    
    public Void visit(StaticMemberNode node) {
        return null;
    }

    public Void visit(DereferenceNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(AddressNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(CastNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(SizeofExprNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(SizeofTypeNode node) {
        return null;
    }

    public Void visit(VariableNode node) {
        return null;
    }
    /*
    public Void visit(IntegerLiteralNode node) {
        return null;
    }
    */
    
    public Void visit(LiteralNode node) {
        return null;
    }
    
    public Void visit(StringLiteralNode node) {
        return null;
    }
    
    public Void visit(RangeNode node) {
        visitExpr(node.begin());
        visitExpr(node.end());
        return null;
    }
    
    public Void visit(AsOpNode node) {
        visitExpr(node.expr());
        return null;
    }
    
    //enumerables
    
    public Void visit(RangeEnumerable node) {
        visitExpr(node.range());
        return null;
    }
    
    public Void visit(VariableSetEnumerable node) {
        visitExprs(node.exprs());
        return null;
    }
    
    public Void visit(PointerEnumerable node) {
        visitExpr(node.pointer());
        visitExpr(node.range());
        return null;
    }

    
}
