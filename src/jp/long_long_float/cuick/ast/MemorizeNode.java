package jp.long_long_float.cuick.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemorizeNode extends Node {

    private Location location;
    private ExprNode hash, max, init;
    
    @Override
    public Location location() {
        return location;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("hash", hash);
        d.printMember("max", max);
        d.printMember("init", init);
    }

}
