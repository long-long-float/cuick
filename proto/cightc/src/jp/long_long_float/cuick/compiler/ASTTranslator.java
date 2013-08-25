package jp.long_long_float.cuick.compiler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.ast.ASTVisitor;
import jp.long_long_float.cuick.ast.ArefNode;
import jp.long_long_float.cuick.ast.AssignNode;
import jp.long_long_float.cuick.ast.AtInputAbstractVariableNode;
import jp.long_long_float.cuick.ast.AtInputArrayVariableNode;
import jp.long_long_float.cuick.ast.AtInputNode;
import jp.long_long_float.cuick.ast.AtInputVariableNode;
import jp.long_long_float.cuick.ast.BinaryOpNode;
import jp.long_long_float.cuick.ast.BlockNode;
import jp.long_long_float.cuick.ast.BuiltInCodeStmt;
import jp.long_long_float.cuick.ast.DefvarNode;
import jp.long_long_float.cuick.ast.DereferenceNode;
import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.ExprStmtNode;
import jp.long_long_float.cuick.ast.ForEachNode;
import jp.long_long_float.cuick.ast.ForNode;
import jp.long_long_float.cuick.ast.FuncallNode;
import jp.long_long_float.cuick.ast.LiteralNode;
import jp.long_long_float.cuick.ast.MemberNode;
import jp.long_long_float.cuick.ast.MultiplexAssignNode;
import jp.long_long_float.cuick.ast.Node;
import jp.long_long_float.cuick.ast.PowerOpNode;
import jp.long_long_float.cuick.ast.RangeNode;
import jp.long_long_float.cuick.ast.SizeofExprNode;
import jp.long_long_float.cuick.ast.StaticMemberNode;
import jp.long_long_float.cuick.ast.StmtNode;
import jp.long_long_float.cuick.ast.StringLiteralNode;
import jp.long_long_float.cuick.ast.SuffixOpNode;
import jp.long_long_float.cuick.ast.TypeNode;
import jp.long_long_float.cuick.ast.VariableNode;
import jp.long_long_float.cuick.entity.Function;
import jp.long_long_float.cuick.entity.Parameter;
import jp.long_long_float.cuick.entity.Params;
import jp.long_long_float.cuick.entity.Variable;
import jp.long_long_float.cuick.foreach.PointerEnumerable;
import jp.long_long_float.cuick.foreach.RangeEnumerable;
import jp.long_long_float.cuick.foreach.VariableSetEnumerable;
import jp.long_long_float.cuick.type.CChar;
import jp.long_long_float.cuick.type.CInt;
import jp.long_long_float.cuick.type.FunctionType;
import jp.long_long_float.cuick.type.NamedType;
import jp.long_long_float.cuick.type.Type;
import jp.long_long_float.cuick.utility.ErrorHandler;
import jp.long_long_float.cuick.utility.ListUtils;

public class ASTTranslator extends ASTVisitor<Node, Node> {

    public ASTTranslator(ErrorHandler h) {
        super(h);
    }
    
    private void updateParents(Node node) {
        new ParentSetter(errorHandler).visit(node);
    }
    
    public void translate(AST ast) {
        if(!ast.isDefinedFunction("main")) {
            Params params = new Params(null, ListUtils.asList(
                    new Parameter(new TypeNode(new CInt()), "argc"),
                    new Parameter(new TypeNode(new CChar().increasePointer().increasePointer()), "argv")));
            BlockNode body = new BlockNode(null, null, new ArrayList<StmtNode>(ast.moveStmts()));
            body.variables().addAll(ast.vars());
            updateParents(body);
            
            Function main = new Function(new TypeNode(new FunctionType(new CInt(), params.parametersType())), "main", params, body);
            ast.addFunction(main);
        }
        else {
            if(!ast.stmts().isEmpty()) {
                error(ast.location(), "Statements mustn't be out of \"main\" function.");
            }
        }

        for(Function func : ast.funcs()) {
            func.body().accept(this);
        }
    }
    
    @Override
    public Node visit(Node node) {
        try {
            Node ret = (Node) getClass().getMethod("visit", node.getClass()).invoke(this, node);
            if(node != ret) {
                updateParents(node.parent());
            }
            return ret;
        } catch (IllegalAccessException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw new Error(e.getCause());
        } catch (NoSuchMethodException e) {
            //e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        for(Field field : node.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object child = field.get(node);
                if(child instanceof Node) {
                    field.set(node, ((Node) child).accept(this));
                }
                else if(child instanceof List<?>) {
                    List<?> childList = (List<?>) child;
                    if(childList.size() > 0 && childList.get(0) instanceof Node) {
                        List<Node> newList = new ArrayList<Node>(childList.size());
                        for(int i = 0;i < childList.size();i++) {
                            Node newItem = ((Node)childList.get(i)).accept(this);
                            if(newItem != null) newList.add(newItem);
                        }
                        field.set(node, newList);
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO 自動生成された catch ブロック
                e.printStackTrace();
            }
        }
        return node;
    }
    
    //at commands
    
    public Node visit(AtInputNode node) {
        BlockNode block = new BlockNode(null, null, null);
        for(AtInputAbstractVariableNode var : node.vars()) {
            if(var instanceof AtInputArrayVariableNode) {
                AtInputArrayVariableNode varNode = (AtInputArrayVariableNode) var;
                ForEachNode forEachNode = new ForEachNode(null, null, "i", true, new RangeEnumerable(varNode.range()),
                        new ExprStmtNode(null, new BinaryOpNode(new StaticMemberNode(
                                new VariableNode(null, "std"), "cin"), ">>", new ArefNode(varNode.getVariableNode(), new VariableNode(null, "i")))), null);
                forEachNode.setParent(block);
                block.stmts().add((StmtNode)forEachNode.accept(this));
            }
            else if(var instanceof AtInputVariableNode) {
                AtInputVariableNode varNode = (AtInputVariableNode) var;
                block.stmts().add(new ExprStmtNode(null, new BinaryOpNode(new StaticMemberNode(new VariableNode(null, "std"), "cin"), ">>", varNode.getVariableNode())));
                if(varNode.isZeroEnd()) {
                    block.stmts().add(new BuiltInCodeStmt(null, "if(!" + varNode.varName() + ") break;"));
                }
            }
        }
        return block;
    }
    
    //statements

    public Node visit(ForEachNode node) {
        Node ret = null;
        if(node.enumerable() instanceof VariableSetEnumerable) {
            VariableSetEnumerable enume = (VariableSetEnumerable)node.enumerable();
            if(enume.exprs().size() == 1) {
                ExprNode rightVar = enume.exprs().get(0);
                if(rightVar.type() == null) {
                    error(node.location(), "Type is undefined! Please use \"as\" oerator.");
                }
                
                //int型の変数
                //TODO int型以外に対応
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
                else {
                    Type itrType = rightVar.type().clone();
                    itrType.getDeepestChild().setChild(new NamedType("iterator", null));
                    Variable itr = new Variable(new TypeNode(itrType), node.getIdentityName("itr"), null, false, null, 
                            ListUtils.asList((ExprNode)new MemberNode(rightVar, "begin()")));
                    VariableNode itrNode = new VariableNode(null, itr.name());
                    
                    Type varType = node.var().type();
                    if(varType == null) {
                        Type deepestChild = rightVar.type().getDeepestChild();
                        if(deepestChild.getTemplateTypes().size() == 0) {
                            error(rightVar.location(), "variable is not container!");
                        }
                        varType = deepestChild.getTemplateTypes().get(0);
                    }
                    Variable var = new Variable(new TypeNode(varType.setReference()), node.var().name(), 
                            null, false, null, ListUtils.asList((ExprNode)new DereferenceNode(itrNode)));
                    BlockNode body = node.body().toBlockNode();
                    body.defineVariable(var);
                    ForNode forNode = new ForNode(node.location(), itr,
                                    new BinaryOpNode(itrNode, "!=", new MemberNode(rightVar, "end()")), 
                                    new SuffixOpNode("++", itrNode), body);
                    ret = forNode;
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
            
            VariableNode counterVar = new VariableNode(null, node.getIdentityName("counter"));
            Variable var = new Variable(new TypeNode(varType.decreasePointer().setReference()), node.var().name(), null, false, null, ListUtils.asList((ExprNode)new ArefNode(pointer, counterVar)));
            
            BlockNode body = node.body().toBlockNode();
            //var.setType(var.type().setReference());
            body.defineVariable(var);
            //node.setBody(body);
            
            RangeNode range = enume.range();
            
            ForNode forNode = new ForNode(node.location(), 
                    new Variable(new TypeNode(new CInt()), counterVar.name(), null, false, null, ListUtils.asList(range.begin())), 
                    new BinaryOpNode(counterVar, range.getOperator(), range.end()), 
                    new SuffixOpNode("++", counterVar), 
                    body);
            ret = forNode;
        }
        node.setBody((BlockNode)node.body().accept(this));
        if(ret == null) {
            throw new Error(node.enumerable().getClass().getSimpleName() + " is not supported!");
        }
        return ret;
    }
    
    //expressions
    
    public Node visit(FuncallNode node) {
        if(node.expr() instanceof VariableNode) {
            String name = ((VariableNode)node.expr()).name();
            if(!name.equals("print") && !name.equals("puts")) return node;
            
            boolean isPuts = name.equals("puts");
            VariableNode std = new VariableNode(null, "std");
            ExprNode endl = new StaticMemberNode(std, "endl");
            ExprNode newNode = new StaticMemberNode(std, "cout");
            for(ExprNode arg : node.args()) {
                newNode = new BinaryOpNode(newNode, "<<", arg);
                if(isPuts) {
                    newNode = new BinaryOpNode(newNode, "<<", endl);
                }
                else {
                    newNode = new BinaryOpNode(newNode, "<<", new StringLiteralNode(null, "\" \""));
                }
            }
            if(!isPuts) {
                newNode = new BinaryOpNode(newNode, "<<", endl);
            }
            return newNode;
        }
        return node;
    }
    
    public Node visit(MultiplexAssignNode node) {
        //FIXME 下のように書き換えると無限ループになる
        //BlockNode parentBlock = node.parentBlockNode(0);
        BlockNode parentBlock = new BlockNode(null, null, null);
        
        Variable temp = new Variable(new TypeNode(node.rhses().get(0).type()), 
                parentBlock.getIdentityName("temp"), null, true, 
                new LiteralNode(null, new CInt(), Integer.toString(node.rhses().size())), node.rhses());
        parentBlock.variables().add(temp);
        List<StmtNode> stmts = new ArrayList<StmtNode>();
        stmts.add(new DefvarNode(node.location(), temp.rawType(), ListUtils.asList(temp)));
        for(int i = 0;i < node.rhses().size();i++){
            //FIXME 理想
            //stmts.add(temp.at(cint(i)).assign(node.rhses().get(0)).toStmt());
            stmts.add(new ExprStmtNode(null, new AssignNode(
                    node.lhses().get(i),
                    new ArefNode(new VariableNode(null, temp.name()), new LiteralNode(null, new CInt(), Integer.toString(i))))));
        }
        //parentBlock.insertStmts(node, stmts);
        parentBlock.stmts().addAll(stmts);
        return parentBlock;
        //return null;
    }
    
    public Node visit(PowerOpNode node) {
        return new FuncallNode(new StaticMemberNode(new VariableNode(null, "std"), "pow"), null, ListUtils.asList(node.left(), node.right()), null);
    }
}
