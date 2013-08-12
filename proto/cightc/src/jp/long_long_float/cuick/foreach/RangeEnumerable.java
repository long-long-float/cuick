package jp.long_long_float.cuick.foreach;

import jp.long_long_float.cuick.ast.ForEachNode;
import jp.long_long_float.cuick.ast.RangeNode;
import jp.long_long_float.cuick.cppStructure.ForStmt;

public class RangeEnumerable extends Enumerable {
    private RangeNode range;
    
    public RangeEnumerable(RangeNode range) {
        this.range = range;
    }
    
    @Override
    public String toString(ForEachNode forEachNode) {
        String var = forEachNode.varName();
        ForStmt forStmt = new ForStmt(
                "int " + var + " = " + range.begin(), 
                var + " " + (range.isIncludingEnd() ? "<=" : "<") + " " + range.end(),
                var + "++");
        forStmt.setBody(forEachNode.body().toString());
        return forStmt.toString();
    }
}
