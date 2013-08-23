package jp.long_long_float.cuick.compiler;

import jp.long_long_float.cuick.ast.AST;
import jp.long_long_float.cuick.ast.Node;
import jp.long_long_float.cuick.ast.StmtNode;
import jp.long_long_float.cuick.entity.Function;
import jp.long_long_float.cuick.utility.ErrorHandler;

public class ParentSetter extends Visitor {

    protected ParentSetter(ErrorHandler h) {
        super(h);
    }
    
    public void parentSet(AST ast) {
        for(StmtNode stmt : ast.stmts()) stmt.accept(this);
        for(Function func : ast.funcs()) func.body().accept(this);
    }
    
    public Void visit(Node node) {
        node.setParents();
        return super.visit(node);
    }
}
