package jp.long_long_float.cuick.ast;

import java.util.List;

import jp.long_long_float.cuick.type.Type;

import org.apache.commons.lang3.StringUtils;

public class FuncallNode extends ExprNode {
    protected ExprNode expr;
    protected List<Type> templTypes;
    protected List<ExprNode> args;
    protected BlockNode block;
    
    public FuncallNode(ExprNode expr, List<Type> templTypes, List<ExprNode> args, BlockNode block) {
        this.expr = expr;
        this.templTypes = templTypes;
        this.args = args;
        this.block = block;
    }
    
    @Override
    public Location location() {
        return expr.location();
    }
    
    @Override
    public String toString() {
        String ret = expr.toString();
        if(templTypes != null && templTypes.size() > 0) {
            ret += "<" + StringUtils.join(templTypes, ", ") + ">";
        }
        ret += "(" + StringUtils.join(args, ", ") + ")";
        if(block != null) {
            ret += block.toString();
        }
        return ret;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("expr", expr);
        d.printNodeList("args", args);
    }

}
