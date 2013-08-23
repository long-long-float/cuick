package jp.long_long_float.cuick.compiler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.ast.ASTVisitor;
import jp.long_long_float.cuick.ast.ArefNode;
import jp.long_long_float.cuick.ast.AsOpNode;
import jp.long_long_float.cuick.ast.AssignNode;
import jp.long_long_float.cuick.ast.BinaryOpNode;
import jp.long_long_float.cuick.ast.BlockNode;
import jp.long_long_float.cuick.ast.BreakNode;
import jp.long_long_float.cuick.ast.BuiltInCodeStmt;
import jp.long_long_float.cuick.ast.CaseNode;
import jp.long_long_float.cuick.ast.CastNode;
import jp.long_long_float.cuick.ast.CondExprNode;
import jp.long_long_float.cuick.ast.ContinueNode;
import jp.long_long_float.cuick.ast.DefvarNode;
import jp.long_long_float.cuick.ast.DoWhileNode;
import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.ExprStmtNode;
import jp.long_long_float.cuick.ast.ForNode;
import jp.long_long_float.cuick.ast.FuncallNode;
import jp.long_long_float.cuick.ast.IfNode;
import jp.long_long_float.cuick.ast.LiteralNode;
import jp.long_long_float.cuick.ast.MemberNode;
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
import jp.long_long_float.cuick.cppStructure.CodeBuilder;
import jp.long_long_float.cuick.cppStructure.ForStmt;
import jp.long_long_float.cuick.cppStructure.Struct;
import jp.long_long_float.cuick.entity.Function;
import jp.long_long_float.cuick.entity.Parameter;
import jp.long_long_float.cuick.entity.Variable;
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
    
    public <T> String join(Iterable<? extends Acceptable> iterable, String separator) {
        StringBuilder sb = new StringBuilder();
        Iterator<? extends Acceptable> itr = iterable.iterator();
        for(int i = 0;itr.hasNext();i++) {
            sb.append((i > 0 ? separator : "") + itr.next().accept(this));
        }
        return sb.toString();
    }
    
    public String visit(AST ast) {
        CodeBuilder cb = new CodeBuilder();
        deployHeaders(cb);
        deployBuiltInCodes(cb);
        deployTuples(cb);
        deployFunctions(cb, ast);
        
        return cb.toString();
    }
    
    private void deployHeaders(CodeBuilder cb) {
        String[] headers = new String[]{"iostream", "vector", "map", "algorithm", "cmath"};
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
        cb.addLine(ent.type() + " " + ent.name() + "(" + join(ent.parameters(), ", ") + ")");
        cb.addLine(ent.body().accept(this));
        return cb.toString();
    }
    
    public String visit(Variable ent) {
        String ret = ent.name();
        if(!ent.constructorArgs().isEmpty()) {
            ret += "(" + join(ent.constructorArgs(), ", ") + ")";
        }
        if(ent.isArray()) {
            ret += "[" + (ent.ListUtilsize() != null ? ent.ListUtilsize() : "") + "]";
        }
        List<ExprNode> init = ent.init();
        if(!init.isEmpty()) {
            ret += " = ";
            if(init.size() == 1) {
                ret += init.get(0).accept(this);
            }
            else {
                ret += "{" + join(init, ", ") + "}";
            }
        }
        return ret;
    }
    
    public String visit(Parameter ent) {
        return ent.type() + " " + ent.name();
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
    /*
    public String visit(ForEachNode node) {
        return node.enumerable().accept(this);
    }
    */
    public String visit(DefvarNode node) {
        System.err.println(node.type().toString());
        return node.type().toString() + " " + join(node.vars(), ", ") + ";";
    }
    
    public String visit(IfNode n) {
        String ret = "if" + "(" + n.cond().accept(this) + ")" + n.thenBody().accept(this);
        if(n.elseBody() != null) {
            ret += "else" + n.elseBody().accept(this);
        }
        return ret;
    }

    public String visit(SwitchNode n) {
        CodeBuilder cb = new CodeBuilder();
        cb.add("switch" + "(" + n.cond().accept(this) + ")");
        cb.beginBlock();
        cb.addLine(join(n.cases(), "\n"));
        cb.endBlock(false);
        return cb.toString();
    }

    public String visit(CaseNode n) {
        CodeBuilder cb = new CodeBuilder();
        for(ExprNode expr : n.values()) {
            cb.addLine("case " + expr.accept(this) + ":");
        }
        cb.add(n.body().accept(this));
        return cb.toString();
    }

    public String visit(WhileNode n) {
        return "while" + "(" + n.cond().accept(this) + ")" + n.body().accept(this);
    }

    public String visit(DoWhileNode n) {
        return "do" + n.body().accept(this) + "while" + "(" + n.cond().accept(this) + ");";
    }

    public String visit(BreakNode n) {
        return "break;";
    }

    public String visit(ContinueNode n) {
        return "continue;";
    }

    public String visit(ReturnNode n) {
        return "return" + (n.expr() != null ? " " + n.expr().accept(this) : "") + ";";
    }
    
    //expressions
    
    public String visit(BinaryOpNode node) {
        return "" + node.left().accept(this) + " " + node.operator() + " " + node.right().accept(this);
    }
    
    public String visit(PowerOpNode node) {
        //XXX ASTTranslatorでしたいっ…!
        return "std::pow((double)" + node.left().accept(this) + ", (double)" + node.right().accept(this) + ")";
    }
    
    public String visit(SuffixOpNode node) {
        return node.expr().accept(this) + node.operator();
    }
    
    public String visit(FuncallNode node) {
        String ret = node.expr().accept(this);
        if(node.templTypes().size() > 0) {
            ret += "<" + StringUtils.join(node.templTypes(), ", ") + ">";
        }
        ret += "(" + join(node.args(), ", ") + ")";
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
    
    public String visit(CondExprNode n) {
        return "" + n.cond().accept(this) + " ? " + n.thenExpr().accept(this) + " : " + n.elseExpr().accept(this);
    }
    
    public String visit(AssignNode n) {
        return n.lhs().accept(this) + " = " + n.rhs().accept(this);
    }

    public String visit(OpAssignNode n) {
        return n.lhs().accept(this) + " " + n.operator() + " " + n.rhs().accept(this);
    }

    public String visit(UnaryOpNode node) {
        return node.operator() + node.expr().accept(this);
    }

    public String visit(PrefixOpNode node) {
        return node.operator() + node.expr().accept(this);
    }

    public String visit(ArefNode node) {
        return node.expr().accept(this) + "[" + node.index().accept(this) + "]";
    }

    public String visit(MemberNode node) {
        return node.expr().accept(this) + "." + node.member();
    }

    public String visit(PtrMemberNode node) {
        return node.expr().accept(this) + "." + node.member();
    }
    
    public String visit(StaticMemberNode node) {
        return node.expr().accept(this) + "::" + node.member();
    }

    public String visit(CastNode node) {
        return "(" + node.type() + ")" + node.expr().accept(this);
    }

    public String visit(SizeofExprNode node) {
        return "sizeof" + "(" + node.expr().accept(this) + ")";
    }

    public String visit(SizeofTypeNode node) {
        return "sizeof" + "(" + node.type() + ")";
    }
    
    public String visit(StringLiteralNode node) {
        return node.value();
    }
    
    public String visit(RangeNode node) {
        return null;
    }
    
    public String visit(AsOpNode node) {
        return node.expr().accept(this);
    }
}
