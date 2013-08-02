package jp.long_long_float.cuick.entity;

import java.util.ArrayList;
import java.util.List;

abstract public class Scope {
    protected List<LocalScope> children;
    
    public Scope() {
        children = new ArrayList<LocalScope>();
    }
}
