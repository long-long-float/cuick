package jp.long_long_float.cuick.ast;

public interface ASTVisitor<S, E> {

    E visit(Node node);

}
