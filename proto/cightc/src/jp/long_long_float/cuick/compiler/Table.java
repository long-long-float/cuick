package jp.long_long_float.cuick.compiler;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.entity.Function;
import jp.long_long_float.cuick.type.Type;

//https://sites.google.com/site/leihcrev/java/validsingleton

public final class Table {
    private static final class TableHolder {
        private static final Table instance = new Table();
    }
    
    public static Table getInstance() {
        return Table.TableHolder.instance;
    }
    
    private Table() {
        
    }
    
    private List<Type> tuples = new ArrayList<Type>();
    private List<Function> functions = new ArrayList<Function>();
    
    public void entryTuple(Type tuple) {
        if(tuples.indexOf(tuple) == -1) {
            tuples.add(tuple);
        }
    }
    
    public void entryFunction(Function func) {
        if(functions.indexOf(func) == -1) {
            functions.add(func);
        }
    }
    
    public List<Type> getTuples() {
        return tuples;
    }
    
    public List<Function> getFunctions() {
        return functions;
    }
}
