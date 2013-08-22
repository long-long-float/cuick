package jp.long_long_float.cuick.compiler;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.ast.ASTVisitor;
import jp.long_long_float.cuick.ast.ArefNode;
import jp.long_long_float.cuick.ast.BinaryOpNode;
import jp.long_long_float.cuick.ast.BlockNode;
import jp.long_long_float.cuick.ast.DefvarNode;
import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.ExprStmtNode;
import jp.long_long_float.cuick.ast.ForEachNode;
import jp.long_long_float.cuick.ast.ForNode;
import jp.long_long_float.cuick.ast.LiteralNode;
import jp.long_long_float.cuick.ast.Node;
import jp.long_long_float.cuick.ast.RangeNode;
import jp.long_long_float.cuick.ast.SizeofExprNode;
import jp.long_long_float.cuick.ast.StmtNode;
import jp.long_long_float.cuick.ast.SuffixOpNode;
import jp.long_long_float.cuick.ast.TypeNode;
import jp.long_long_float.cuick.ast.VariableNode;
import jp.long_long_float.cuick.entity.Function;
import jp.long_long_float.cuick.entity.Variable;
import jp.long_long_float.cuick.foreach.PointerEnumerable;
import jp.long_long_float.cuick.foreach.RangeEnumerable;
import jp.long_long_float.cuick.foreach.VariableSetEnumerable;
import jp.long_long_float.cuick.type.CInt;
import jp.long_long_float.cuick.type.Type;
import jp.long_long_float.cuick.utility.ErrorHandler;
import jp.long_long_float.cuick.utility.ListUtils;

public class ASTTranslator extends ASTVisitor<Node, Node> {

    public ASTTranslator(ErrorHandler h) {
        super(h);
    }
    
    public void translate(AST ast) {
        List<StmtNode> stmts = new ArrayList<StmtNode>(ast.stmts().size());
        for(StmtNode stmt : ast.stmts()) {
            stmts.add((StmtNode)stmt.accept(this));
        }
        ast.setStmt(stmts);
        for(Function func : ast.funcs()) {
            func.body().accept(this);
        }
    }
    
    @Override
    public Node visit(Node node) {
        Node ret = super.visit(node);
        if(ret instanceof StmtNode) {
            new LocalResolver(errorHandler).resolve((StmtNode)ret);
        }
        return ret;
    }
    
    public Node visit(DefvarNode node) {
        return node;
    }
    
    public Node visit(BlockNode node) {
        List<StmtNode> stmts = new ArrayList<StmtNode>(node.stmts().size());
        for(StmtNode stmt : node.stmts()) {
            stmts.add((StmtNode)stmt.accept(this));
        }
        node.setStmt(stmts);
        return node;
    }
    
    public Node visit(ExprStmtNode node) {
        return node;
    }
    
    public Node visit(ForEachNode node) {
        Node ret = null;
        if(node.enumerable() instanceof VariableSetEnumerable) {
            VariableSetEnumerable enume = (VariableSetEnumerable)node.enumerable();
            if(enume.exprs().size() == 1) {
                ExprNode rightVar = enume.exprs().get(0);
                if(rightVar.type() == null) {
                    error(node.location(), "Type is undefined! Please use \"as\" oerator.");
                }
                
                if(rightVar.type().hasType("int")) {
                    //VariableNode leftVar = new VariableNode(null, enume.forEachNode().var().name());
                    if(rightVar.type().isPointer()) {
                        PointerEnumerable new_enume = new PointerEnumerable(rightVar,
                                new BinaryOpNode(
                                        new SizeofExprNode(rightVar), "/", 
                                        new SizeofExprNode(new ArefNode(rightVar, new LiteralNode(null, new CInt(), "0")))));
                        node.setEnumerable(new_enume);
                        ret = node.accept(this);
                    }
                    else {
                        RangeEnumerable new_enume = new RangeEnumerable(new RangeNode(
                            new LiteralNode(null, node.var().type(), "0"), "...", enume.exprs().get(0)));
                        node.setEnumerable(new_enume);
                        ret = node.accept(this);
                    }
                }
            }
        }
        else if(node.enumerable() instanceof RangeEnumerable) {
            RangeEnumerable enume = (RangeEnumerable) node.enumerable();
            String varName = node.var().name();
            VariableNode var = new VariableNode(null, varName);
            RangeNode range = enume.range();
            ForNode forNode = new ForNode(node.location(), 
                    new Variable(new TypeNode(new CInt()), varName, null, false, null, ListUtils.asList(range.begin())),
                    new BinaryOpNode(var, range.getOperator(), range.end()), 
                    new SuffixOpNode("++", var), 
                    node.body());
            ret = forNode;
        }
        else if(node.enumerable() instanceof PointerEnumerable) {
            PointerEnumerable enume = (PointerEnumerable) node.enumerable();
            
            ExprNode pointer = enume.pointer();
            Type varType = node.var().type();
            if(varType == null) {
                varType = pointer.type();
            }
            
            //TODO iを他とかぶらないようにする
            int id = 0;
            for(;node.scope().isDefinedVariable("counter" + id);id++) ;
            VariableNode counterVar = new VariableNode(null, "counter" + id);
            Variable var = new Variable(new TypeNode(varType.decreasePointer()), node.var().name(), null, false, null, ListUtils.asList((ExprNode)new ArefNode(pointer, counterVar)));
            
            BlockNode body = node.body().toBlockNode();
            var.setType(var.type().setReference());
            body.defineVariable(var);
            node.setBody(body);
            
            RangeNode range = enume.range();
            
            ForNode forNode = new ForNode(node.location(), 
                    new Variable(new TypeNode(new CInt()), counterVar.name(), null, false, null, ListUtils.asList(range.begin())), 
                    new BinaryOpNode(counterVar, range.getOperator(), range.end()), 
                    new SuffixOpNode("++", counterVar), 
                    node.body());
            ret = forNode;
        }
        node.setBody((StmtNode)node.body().accept(this));
        if(ret == null) {
            throw new Error(node.enumerable().getClass().getSimpleName() + " is not supported!");
        }
        return ret;
    }
}
