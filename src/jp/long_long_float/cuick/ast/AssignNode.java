package jp.long_long_float.cuick.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssignNode extends AbstractAssignNode {
    
    private ExprNode lhs, rhs;
    
    @Override
    public Location location() {
        return lhs.location();
    }
    
    @Override
    protected void _dump(Dumper d) {
        _dumpDefault(d);
    }
}
