package jp.long_long_float.cuick.type;

import java.util.List;

import jp.long_long_float.cuick.ast.Location;
import jp.long_long_float.cuick.entity.ParamSlots;

public class ParamTypes extends ParamSlots<Type> {
    public ParamTypes(Location loc, List<Type> paramDescs, boolean vararg) {
        super(loc, paramDescs, vararg);
    }
}
