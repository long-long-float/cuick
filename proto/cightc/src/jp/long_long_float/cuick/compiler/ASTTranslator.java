package jp.long_long_float.cuick.compiler;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.ast.ArefNode;
import jp.long_long_float.cuick.ast.BinaryOpNode;
import jp.long_long_float.cuick.ast.BlockNode;
import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.ForEachNode;
import jp.long_long_float.cuick.ast.LiteralNode;
import jp.long_long_float.cuick.ast.RangeNode;
import jp.long_long_float.cuick.ast.SizeofExprNode;
import jp.long_long_float.cuick.ast.StmtNode;
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

public class ASTTranslator extends Visitor {

    public ASTTranslator(ErrorHandler h) {
        super(h);
    }
    
    public void translate(AST ast) {
        for(StmtNode stmt : ast.stmts()) {
            stmt.accept(this);
        }
        for(Function func : ast.funcs()) {
            func.body().accept(this);
        }
    }
    
    public Void visit(VariableSetEnumerable enume) {
        if(enume.exprs().size() == 1) {
            ExprNode rightVar = enume.exprs().get(0);
            if(rightVar.type() == null) {
                throw new Error("Type is undefined! Please use \"as\" oerator.");
            }
            
            if(rightVar.type().hasType("int")) {
                //VariableNode leftVar = new VariableNode(null, enume.forEachNode().var().name());
                if(rightVar.type().isPointer()) {
                    PointerEnumerable new_enume = new PointerEnumerable(rightVar,
                            new BinaryOpNode(
                                    new SizeofExprNode(rightVar), "/", 
                                    new SizeofExprNode(new ArefNode(rightVar, new LiteralNode(null, new CInt(), "0")))));
                    new_enume.setForEachNode(enume.forEachNode());
                    new_enume.accept(this);
                    enume.forEachNode().setEnumerable(new_enume);
                }
                else {
                    RangeEnumerable new_enume = new RangeEnumerable(new RangeNode(
                        new LiteralNode(null, enume.forEachNode().var().type(), "0"), "...", enume.exprs().get(0)));
                    new_enume.setForEachNode(enume.forEachNode());
                    new_enume.accept(this);
                    enume.forEachNode().setEnumerable(new_enume);
                }
            }
        }
        return null;
    }
    
    public Void visit(PointerEnumerable enume) {
        ForEachNode forEachNode = enume.forEachNode();
        //TODO iを他とかぶらないようにする
        VariableNode counterVar = new VariableNode(null, "i");
        
        ExprNode pointer = enume.pointer();
        Type varType = forEachNode.var().type();
        if(varType == null) {
            varType = pointer.type();
        }
        System.out.println(varType);
        Variable var = new Variable(new TypeNode(varType.decreasePointer()), forEachNode.var().name(), null, false, null, ListUtils.asList((ExprNode)new ArefNode(pointer, counterVar)));
        
        BlockNode body = forEachNode.body().toBlockNode();
        System.out.println(var.type());
        var.setType(var.type().setReference());
        body.defineVariable(var);
        return null;
    }

}
