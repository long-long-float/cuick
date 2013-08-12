package jp.long_long_float.cuick.entity;

import java.util.Iterator;
import java.util.List;

import jp.long_long_float.cuick.ast.Location;

public abstract class ParamSlots<T> implements Iterable<T> {
    protected Location location;
    protected List<T> paramDescriptors;
    protected boolean vararg;
    
    public ParamSlots(List<T> paramDescs) {
        this(null, paramDescs);
    }
    
    public ParamSlots(Location loc, List<T> paramDescs) {
        this(loc, paramDescs, false);
    }
    
    protected ParamSlots(Location loc, List<T> paramDescs, boolean vararg) {
        super();
        this.location = loc;
        this.paramDescriptors = paramDescs;
        this.vararg = vararg;
    }
    
    @Override
    public Iterator<T> iterator() {
        return paramDescriptors.iterator();
    }
}
