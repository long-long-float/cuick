package jp.long_long_float.cuick.foreach;

import java.util.List;

import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.ForEachNode;
import jp.long_long_float.cuick.ast.LiteralNode;
import jp.long_long_float.cuick.ast.RangeNode;

public class VariableSetEnumerable extends Enumerable {
    private List<ExprNode> exprs;
    
    public VariableSetEnumerable(List<ExprNode> exprs) {
        super(exprs.get(0).location());
        this.exprs = exprs;
    }

    @Override
    public String toString(ForEachNode forEachNode) {
        //TODO int以外の整数に対応
        if(exprs.size() == 1) {
            ExprNode var = exprs.get(0);
            if(var.type() == null) {
                throw new Error("Type is undefined! Please use \"as\" oerator.");
            }
            
            if(var.type().toString().equals("int")) {
                RangeEnumerable enume = new RangeEnumerable(new RangeNode(
                        new LiteralNode(null, forEachNode.var().type(), "0"), "...", exprs.get(0)));
                return enume.toString(forEachNode);
            }
        }
        return toString();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("exprs", exprs);
    }

    public List<ExprNode> exprs() {
        return exprs;
    }
}
