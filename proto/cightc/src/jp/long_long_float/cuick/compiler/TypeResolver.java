package jp.long_long_float.cuick.compiler;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.ast.ArefNode;
import jp.long_long_float.cuick.ast.AssignNode;
import jp.long_long_float.cuick.ast.BinaryOpNode;
import jp.long_long_float.cuick.ast.CastNode;
import jp.long_long_float.cuick.ast.CondExprNode;
import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.FuncallNode;
import jp.long_long_float.cuick.ast.LiteralNode;
import jp.long_long_float.cuick.ast.MemberNode;
import jp.long_long_float.cuick.ast.OpAssignNode;
import jp.long_long_float.cuick.ast.PrefixOpNode;
import jp.long_long_float.cuick.ast.PtrMemberNode;
import jp.long_long_float.cuick.ast.RangeNode;
import jp.long_long_float.cuick.ast.SizeofExprNode;
import jp.long_long_float.cuick.ast.SizeofTypeNode;
import jp.long_long_float.cuick.ast.StmtNode;
import jp.long_long_float.cuick.ast.StringLiteralNode;
import jp.long_long_float.cuick.ast.SuffixOpNode;
import jp.long_long_float.cuick.ast.UnaryOpNode;
import jp.long_long_float.cuick.ast.VariableNode;
import jp.long_long_float.cuick.entity.Function;
import jp.long_long_float.cuick.foreach.RangeEnumerable;
import jp.long_long_float.cuick.utility.ErrorHandler;

public class TypeResolver extends Visitor {
    
    public TypeResolver(ErrorHandler h) {
        super(h);
    }
    
    public void resolve(AST ast) {
        for(StmtNode stmt : ast.stmts()) {
            stmt.accept(this);
        }
        for(Function func : ast.funcs()) {
            func.body().accept(this);
        }
    }
    
    public void typeEqualCheck(ExprNode expr, ExprNode expr1, ExprNode expr2) {
        if(!expr1.type().equals(expr2.type())) {
            warn(expr.location(), "Types are difficult.");
        }
    }
    
    public Void visit(CondExprNode n) {
        super.visit(n);
        System.out.println(n.thenExpr().type()); //TODO
        typeEqualCheck(n, n.thenExpr(), n.elseExpr());
        n.setType(n.thenExpr().type());
        return null;
    }

    public Void visit(AssignNode n) {
        super.visit(n);
        typeEqualCheck(n, n.lhs(), n.rhs());
        n.setType(n.lhs().type());
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

    /*
    public Void visit(DereferenceNode node) {
        visitExpr(node.expr());
        return null;
    }

    public Void visit(AddressNode node) {
        visitExpr(node.expr());
        return null;
    }
    */

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
        super.visit(node);
        if(node.entity() != null) {
            node.setType(node.entity().type());
        }
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
    
    //enumerables
    
    public Void visit(RangeEnumerable node) {
        visitExpr(node.range());
        return null;
    }
}
