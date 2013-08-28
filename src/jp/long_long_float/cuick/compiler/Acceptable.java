package jp.long_long_float.cuick.compiler;

import jp.long_long_float.cuick.ast.ASTVisitor;

public interface Acceptable {
    public <T> T accept(ASTVisitor<T> visitor);
}
