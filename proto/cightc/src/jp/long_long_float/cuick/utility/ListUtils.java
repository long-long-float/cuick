package jp.long_long_float.cuick.utility;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    @SafeVarargs
    static public <T> List<T> toArrayList(T... items) {
        List<T> ret = new ArrayList<T>(items.length);
        for(T item : items) ret.add(item);
        return ret;
    }
}
