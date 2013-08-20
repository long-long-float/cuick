package jp.long_long_float.cuick.compiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.ast.ASTVisitor;
import jp.long_long_float.cuick.ast.BinaryOpNode;
import jp.long_long_float.cuick.ast.BlockNode;
import jp.long_long_float.cuick.ast.BuiltInCodeStmt;
import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.ExprStmtNode;
import jp.long_long_float.cuick.ast.ForEachNode;
import jp.long_long_float.cuick.ast.ForNode;
import jp.long_long_float.cuick.ast.FuncallNode;
import jp.long_long_float.cuick.ast.LiteralNode;
import jp.long_long_float.cuick.ast.RangeNode;
import jp.long_long_float.cuick.ast.StmtNode;
import jp.long_long_float.cuick.ast.SuffixOpNode;
import jp.long_long_float.cuick.ast.TypeNode;
import jp.long_long_float.cuick.ast.VariableNode;
import jp.long_long_float.cuick.cppStructure.CodeBuilder;
import jp.long_long_float.cuick.cppStructure.ForStmt;
import jp.long_long_float.cuick.cppStructure.Struct;
import jp.long_long_float.cuick.entity.Function;
import jp.long_long_float.cuick.entity.Parameter;
import jp.long_long_float.cuick.entity.Params;
import jp.long_long_float.cuick.entity.Variable;
import jp.long_long_float.cuick.foreach.RangeEnumerable;
import jp.long_long_float.cuick.type.BasicType;
import jp.long_long_float.cuick.type.FunctionType;
import jp.long_long_float.cuick.type.PrimitiveTypes;
import jp.long_long_float.cuick.type.Type;
import jp.long_long_float.cuick.utility.ErrorHandler;

import org.apache.commons.lang3.StringUtils;

public class CodeGenerator extends ASTVisitor<String, String> {

    public CodeGenerator(ErrorHandler h) {
        super(h);
    }
    
    public String generate(AST ast) {
        return ast.accept(this);
    }
    
    public String visit(AST ast) {
        CodeBuilder cb = new CodeBuilder();
        deployHeaders(cb);
        deployBuiltInCodes(cb);
        deployTuples(cb);
        deployFunctions(cb, ast);
        //main
        Params params = new Params(null, Arrays.asList(new Parameter(new TypeNode(PrimitiveTypes.cint.type()), "argc")));
        BlockNode body = new BlockNode(null, ast.stmts());
        Function main = new Function(new TypeNode(new FunctionType(PrimitiveTypes.cint.type(), params.parametersType())), "main", params, body);
        cb.addLine(main.accept(this));
        
        return cb.toString();
    }
    
    private void deployHeaders(CodeBuilder cb) {
        String[] headers = new String[]{"iostream", "vector", "map", "algorithm"};
        for(String header : headers) {
            cb.addLine("#include<" + header + ">");
        }
    }
    
    private void deployBuiltInCodes(CodeBuilder cb) {
        for(BuiltInCodeStmt code : Table.getInstance().getBuiltInCodeStmt()) {
            cb.addLine(code.accept(this));
        }
    }
    
    private void deployTuples(CodeBuilder cb) {
        List<Type> tuples = Table.getInstance().getTuples();
        //tupleの依存関係を作る
        List<List<Integer>> tupleTree = new ArrayList<List<Integer>>(tuples.size());
        List<Integer> parentNums = new ArrayList<Integer>(tuples.size());
        for(int i = 0;i < tuples.size();i++) {
            tupleTree.add(new ArrayList<Integer>());
            parentNums.add(0);
        }
        for(int tupleID = 0;tupleID < tuples.size();tupleID++) {
            for(Type member : tuples.get(tupleID).getTemplateTypes()) {
                int index;
                if((index = tuples.indexOf(member)) != -1) {
                    tupleTree.get(tupleID).add(index);
                    parentNums.set(index, parentNums.get(index) + 1);
                }
            }
        }
        List<Integer> root = new ArrayList<Integer>();
        for(int i = 0;i < parentNums.size();i++) {
            if(parentNums.get(i) == 0) root.add(i);
        }
        //木の末端にあるものから配置
        Queue<Integer> queue = new LinkedList<Integer>();
        for(Integer rootID : root) {
            queue.offer(rootID);
        }
        List<Integer> deployedTupleIDs = new ArrayList<Integer>();
        while(!queue.isEmpty()) {
            int tupleID = queue.poll();
            if(!deployedTupleIDs.contains(tupleID)) {
                deployedTupleIDs.add(tupleID);
            }
            for(Integer i : tupleTree.get(tupleID)) {
                queue.offer(i);
            }
        }
        for(int i = deployedTupleIDs.size() - 1;i >= 0;i--) {
            int tupleID = deployedTupleIDs.get(i);
            Struct struct = new Struct("tuple" + tupleID);
            List<Type> templTypes = tuples.get(tupleID).getTemplateTypes();
            for(int memberID = 0;memberID < templTypes.size();memberID++) {
                Type member = templTypes.get(memberID);
                String name = member.toString();
                int index;
                if((index = tuples.indexOf(member)) != -1) {
                    name = "tuple" + index;
                }
                struct.addMember(name, "item" + memberID);
            }
            cb.addLine(struct.toString());
        }
    }
    
    private void deployFunctions(CodeBuilder cb, AST ast) {
        /*for(jp.long_long_float.cuick.entity.Function func : Table.getInstance().getFunctions()) {
            cb.addLine(func.toString());
        }*/
        for(Function func : ast.funcs()) {
            cb.addLine(func.accept(this));
        }
    }
    
    //entities
    
    public String visit(Function ent) {
        CodeBuilder cb = new CodeBuilder();
        cb.addLine(ent.type() + " " + ent.name() + "(" + StringUtils.join(ent.parameters(), ", ") + ")");
        cb.addLine(ent.body().accept(this));
        return cb.toString();
    }
    
    public String visit(Variable ent) {
        String ret = ent.name();
        if(!ent.constructorArgs().isEmpty()) {
            ret += "(" + StringUtils.join(ent.constructorArgs(), ", ") + ")";
        }
        if(ent.isArray()) {
            ret += "[" + (ent.arraySize() != null ? ent.arraySize() : "") + "]";
        }
        List<ExprNode> init = ent.init();
        if(!init.isEmpty()) {
            ret += " = ";
            if(init.size() == 1) {
                ret += init.get(0).accept(this);
            }
            else {
                ret += "{" + StringUtils.join(init, ", ") + "}";
            }
        }
        return ret;
    }
    
    //statements
    
    public String visit(ExprStmtNode node) {
        return node.expr().accept(this) + ";";
    }
    
    public String visit(BlockNode node) {
        CodeBuilder cb = new CodeBuilder();
        cb.beginBlock();
        for(StmtNode stmt : node.stmts()) {
            cb.addLine(stmt.accept(this));
        }
        cb.endBlock(false);
        return cb.toString();
    }
    
    public String visit(ForNode node) {
        ForStmt forStmt = new ForStmt(node.var().type() + " " + node.var().accept(this), node.cond().accept(this), node.incr().accept(this));
        forStmt.setBody(node.body().accept(this));
        return forStmt.toString();
    }
    
    public String visit(ForEachNode node) {
        return node.enumerable().accept(this);
    }
    
    //expressions
    
    public String visit(BinaryOpNode node) {
        return "" + node.left().accept(this) + " " + node.operator() + " " + node.right().accept(this);
    }
    
    public String visit(SuffixOpNode node) {
        return node.expr().accept(this) + node.operator();
    }
    
    public String visit(FuncallNode node) {
        String ret = node.expr().accept(this);
        if(node.templTypes().size() > 0) {
            ret += "<" + StringUtils.join(node.templTypes(), ", ") + ">";
        }
        ret += "(" + StringUtils.join(node.args(), ", ") + ")";
        if(node.block() != null) {
            ret += node.block().toString();
        }
        return ret;
    }
    
    public String visit(VariableNode node) {
        return node.name();
    }
    
    public String visit(LiteralNode node) {
        return node.value();
    }
    
    //enumerables
    
    public String visit(RangeEnumerable enume) {
        ForEachNode forEachNode = enume.forEachNode();
        String varName = forEachNode.var().name();
        VariableNode var = new VariableNode(null, varName);
        RangeNode range = enume.range();
        ForNode forNode = new ForNode(null, 
                //variable(BasicTypes.int, varName, null, false, null, Arrays.asList(range.begin()))
                new Variable(new TypeNode(new BasicType("int", null)), varName, null, false, null, Arrays.asList(range.begin())),
                new BinaryOpNode(var, range.getOperator(), range.end()), 
                new SuffixOpNode("++", var), 
                forEachNode.body());
        return forNode.accept(this);
    }
}
