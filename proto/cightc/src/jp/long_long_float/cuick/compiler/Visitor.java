package jp.long_long_float.cuick.compiler;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import jp.long_long_float.cuick.ast.ASTVisitor;
import jp.long_long_float.cuick.ast.BinaryOpNode;
import jp.long_long_float.cuick.ast.BlockNode;
import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.ExprStmtNode;
import jp.long_long_float.cuick.ast.ForNode;
import jp.long_long_float.cuick.ast.FuncallNode;
import jp.long_long_float.cuick.ast.LiteralNode;
import jp.long_long_float.cuick.ast.Node;
import jp.long_long_float.cuick.ast.StmtNode;
import jp.long_long_float.cuick.ast.SuffixOpNode;
import jp.long_long_float.cuick.ast.VariableNode;

public class Visitor implements ASTVisitor<Void, Void> {
    
    protected void visitStmt(StmtNode stmt) {
        stmt.accept(this);
    }
    
    protected void visitStmts(List<StmtNode> stmts) {
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
    
    @Override
    public Void visit(Node n) {
        String nodeName = n.getClass().getSimpleName();
        try {
            getClass().getMethod("visit", n.getClass()).invoke(this, n);
        } catch (IllegalAccessException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw new Error(e.getCause());
        } catch (NoSuchMethodException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
        return null;
    }

    //statements
    
    public Void visit(BlockNode node) {
        visitStmts(node.stmts());
        return null;
    }
    
    public Void visit(ExprStmtNode node) {
        visitExpr(node.expr());
        return null;
    }
    
    //expressions
    
    public Void visit(ForNode n) {
        //visitStmt(n.init());
        visitExpr(n.cond());
        visitExpr(n.incr());
        visitStmt(n.body());
        return null;
    }
    
    public Void visit(FuncallNode node) {
        visitExpr(node.expr());
        visitExprs(node.args());
        return null;
    }
    
    public Void visit(BinaryOpNode n) {
        visitExpr(n.left());
        visitExpr(n.right());
        return null;
    }
    
    public Void visit(SuffixOpNode node) {
        visitExpr(node.expr());
        return null;
    }
    
    public Void visit(VariableNode node) {
        return null;
    }
    
    public Void visit(LiteralNode node) {
        return null;
    }

}
