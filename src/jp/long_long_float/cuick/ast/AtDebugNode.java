package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.compiler.Table;


public class AtDebugNode extends AtCommandNode {

    public AtDebugNode(Location location) {
        super(location);
        
        Table.getInstance().setDebugMode(true);
    }
    
    @Override
    protected void _dump(Dumper d) {
    }

}
