package jp.long_long_float.cuick.entity;

import java.util.List;

import jp.long_long_float.cuick.ast.Dumpable;
import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.Location;

public class Params extends ParamSlots<Parameter> implements Dumpable{
    public Params(Location loc, List<Parameter> paramDescs) {
        super(loc, paramDescs, false);
    }
    
    public List<Parameter> parameters() {
        return paramDescriptors;
    }

    @Override
    public void dump(Dumper d) {
        d.printNodeList("paramters", parameters());
    }
}
