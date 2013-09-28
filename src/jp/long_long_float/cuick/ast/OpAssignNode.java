package jp.long_long_float.cuick.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OpAssignNode extends AbstractAssignNode {
    
    private ExprNode lhs;
    private String operator;
    private ExprNode rhs;
    
    @Override
    public Location location() {
        return lhs.location();
    }
    
    @Override
    protected void _dump(Dumper d) {
        _dumpDefault(d);
    }
}
