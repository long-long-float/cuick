package jp.long_long_float.cuick.compiler;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.ast.AddressNode;
import jp.long_long_float.cuick.ast.ArefNode;
import jp.long_long_float.cuick.ast.AsOpNode;
import jp.long_long_float.cuick.ast.AssignNode;
import jp.long_long_float.cuick.ast.BinaryOpNode;
import jp.long_long_float.cuick.ast.CastNode;
import jp.long_long_float.cuick.ast.CondExprNode;
import jp.long_long_float.cuick.ast.DereferenceNode;
import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.FuncallNode;
import jp.long_long_float.cuick.ast.LiteralNode;
import jp.long_long_float.cuick.ast.MemberNode;
import jp.long_long_float.cuick.ast.OpAssignNode;
import jp.long_long_float.cuick.ast.PrefixOpNode;
import jp.long_long_float.cuick.ast.PtrMemberNode;
import jp.long_long_float.cuick.ast.SizeofExprNode;
import jp.long_long_float.cuick.ast.SizeofTypeNode;
import jp.long_long_float.cuick.ast.StmtNode;
import jp.long_long_float.cuick.ast.StringLiteralNode;
import jp.long_long_float.cuick.ast.SuffixOpNode;
import jp.long_long_float.cuick.ast.UnaryOpNode;
import jp.long_long_float.cuick.ast.VariableNode;
import jp.long_long_float.cuick.entity.Function;
import jp.long_long_float.cuick.type.CChar;
import jp.long_long_float.cuick.type.CInt;
import jp.long_long_float.cuick.type.CUnsignedInt;
import jp.long_long_float.cuick.type.Type;
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
        if(expr1 == null || expr1.type() == null || expr2 == null || expr2.type() == null) return;
        if(!expr1.type().equals(expr2.type())) {
            warn(expr.location(), "Types are difficult.");
        }
    }
    
    public Type selectType(ExprNode expr1, ExprNode expr2) {
        if(expr1 != null && expr1.type() != null) return expr1.type();
        return expr2.type();
    }
    
    public Void visit(CondExprNode n) {
        super.visit(n);
        typeEqualCheck(n, n.thenExpr(), n.elseExpr());
        n.setType(n.thenExpr().type());
        return null;
    }

    public Void visit(AssignNode n) {
        super.visit(n);
        typeEqualCheck(n, n.getLhs(), n.getRhs());
        n.setType(n.getLhs().type());
        return null;
    }

    public Void visit(OpAssignNode n) {
        super.visit(n);
        typeEqualCheck(n, n.getLhs(), n.getRhs());
        n.setType(selectType(n.getLhs(), n.getRhs()));
        return null;
    }

    public Void visit(BinaryOpNode n) {
        super.visit(n);
        typeEqualCheck(n, n.left(), n.right());
        n.setType(selectType(n.left(), n.right()));
        return null;
    }

    public Void visit(UnaryOpNode node) {
        super.visit(node);
        node.setType(node.expr().type());
        return null;
    }

    public Void visit(PrefixOpNode node) {
        super.visit(node);
        node.setType(node.expr().type());
        return null;
    }

    public Void visit(SuffixOpNode node) {
        super.visit(node);
        node.setType(node.expr().type());
        return null;
    }

    public Void visit(FuncallNode node) {
        super.visit(node);
        node.setType(node.type());
        return null;
    }

    public Void visit(ArefNode node) {
        super.visit(node);
        node.setType(node.expr().type());
        return null;
    }

    public Void visit(MemberNode node) {
        super.visit(node);
        node.setType(node.type());
        return null;
    }

    public Void visit(PtrMemberNode node) {
        super.visit(node);
        node.setType(node.type());
        return null;
    }

    public Void visit(DereferenceNode node) {
        super.visit(node);
        node.setType(node.expr().type());
        return null;
    }

    public Void visit(AddressNode node) {
        super.visit(node);
        node.setType(new CInt(node.location()).increasePointer());
        return null;
    }

    public Void visit(CastNode node) {
        super.visit(node);
        node.setType(node.destType());
        return null;
    }

    public Void visit(SizeofExprNode node) {
        super.visit(node);
        node.setType(new CUnsignedInt(node.location()));
        return null;
    }

    public Void visit(SizeofTypeNode node) {
        super.visit(node);
        node.setType(new CUnsignedInt(node.location()));
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
        super.visit(node);
        node.setType(node.typeNode().type());
        return null;
    }
    
    public Void visit(StringLiteralNode node) {
        super.visit(node);
        node.setType(new CChar(node.location()).increasePointer());
        return null;
    }
    
    public Void visit(AsOpNode node) {
        super.visit(node);
        node.setType(node.asType());
        return null;
    }
}
