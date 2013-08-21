package jp.long_long_float.cuick.foreach;

import jp.long_long_float.cuick.ast.BinaryOpNode;
import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.ForEachNode;
import jp.long_long_float.cuick.ast.ForNode;
import jp.long_long_float.cuick.ast.RangeNode;
import jp.long_long_float.cuick.ast.SuffixOpNode;
import jp.long_long_float.cuick.ast.TypeNode;
import jp.long_long_float.cuick.ast.VariableNode;
import jp.long_long_float.cuick.entity.Variable;
import jp.long_long_float.cuick.type.BasicType;
import jp.long_long_float.cuick.utility.ListUtils;

public class RangeEnumerable extends Enumerable {
    private RangeNode range;
    
    public RangeEnumerable(RangeNode range) {
        super(range.location());
        this.range = range;
    }
    
    public RangeNode range() {
        return range;
    }
    
    @Override
    @Deprecated
    public String toString(ForEachNode forEachNode) {
        String varName = forEachNode.var().name();
        VariableNode var = new VariableNode(null, varName);
        ForNode forNode = new ForNode(null, 
                //variable(BasicTypes.int, varName, null, false, null, ListUtils.asList(range.begin()))
                new Variable(new TypeNode(new BasicType("int", null)), varName, null, false, null, ListUtils.asList(range.begin())),
                new BinaryOpNode(var, range.getOperator(), range.end()), 
                new SuffixOpNode("++", var), 
                forEachNode.body());
        /*
        String var = forEachNode.var().name();
        ForStmt forStmt = new ForStmt(
                "int " + var + " = " + range.begin(), 
                var + " " + range.getOperator() + " " + range.end(),
                var + "++");
        forStmt.setBody(forEachNode.body().toString());
        */
        return forNode.toString();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("range", range);
    }
}
