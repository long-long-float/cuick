package jp.long_long_float.cuick.compiler;

import jp.long_long_float.cuick.ast.ASTVisitor;
import jp.long_long_float.cuick.ast.StmtNode;

public class Visitor implements ASTVisitor<Void, Void> {

    @Override
    public Void visit(StmtNode n) {
        String name = n.getClass().getName();
        switch(name) {
        default:
            System.out.println("=======================" + name + "====================");
        }
        return null;
    }

}
