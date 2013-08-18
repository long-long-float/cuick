package jp.long_long_float.cuick.foreach;

import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.ForEachNode;
import jp.long_long_float.cuick.ast.RangeNode;
import jp.long_long_float.cuick.cppStructure.ForStmt;

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
    public String toString(ForEachNode forEachNode) {
        String var = forEachNode.var().name();
        ForStmt forStmt = new ForStmt(
                "int " + var + " = " + range.begin(), 
                var + " " + range.getOperator() + " " + range.end(),
                var + "++");
        forStmt.setBody(forEachNode.body().toString());
        return forStmt.toString();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("range", range);
    }
}
