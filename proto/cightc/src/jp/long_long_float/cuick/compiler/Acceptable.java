package jp.long_long_float.cuick.compiler;

import jp.long_long_float.cuick.ast.ASTVisitor;

public interface Acceptable {
    public <S, E> E accept(ASTVisitor<S, E> visitor);
}
