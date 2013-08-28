package jp.long_long_float.cuick.entity;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.ast.Dumpable;
import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.type.ParamTypes;
import jp.long_long_float.cuick.type.Type;

public class Params extends ParamSlots<Parameter> implements Dumpable{
    public Params(Location loc, List<Parameter> paramDescs) {
        super(loc, paramDescs, false);
    }
    
    public List<Parameter> parameters() {
        return paramDescriptors;
    }
    
    public ParamTypes parametersType() {
        List<Type> types = new ArrayList<Type>();
        for(Parameter param : paramDescriptors) {
            types.add(param.type());
        }
        return new ParamTypes(location, types, vararg);
    }
    
    //for extend
    public void addParamFront(Parameter param) {
        paramDescriptors.add(0, param);
    }

    @Override
    public void dump(Dumper d) {
        d.printNodeList("paramters", parameters());
    }
}
