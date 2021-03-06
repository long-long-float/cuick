package jp.long_long_float.cuick.ast;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.type.Type;

public class FuncallNode extends ExprNode {
    protected ExprNode expr;
    protected List<Type> templTypes;
    protected List<ExprNode> args;
    protected BlockNode block;
    
    public FuncallNode(ExprNode expr, List<Type> templTypes, List<ExprNode> args, BlockNode block) {
        this.expr = expr;
        this.templTypes = (templTypes != null ? templTypes : new ArrayList<Type>());
        this.args = args;
        this.block = block;
    }
    
    @Override
    public Location location() {
        return expr.location();
    }
    
    public ExprNode expr() {
        return expr;
    }
    
    public List<Type> templTypes() {
        return templTypes;
    }
    
    public List<ExprNode> args() {
        return args;
    }
    
    public BlockNode block() {
        return block;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("expr", expr);
        d.printMember("templTypes", templTypes);
        d.printNodeList("args", args);
        d.printMember("block", block);
    }
}
