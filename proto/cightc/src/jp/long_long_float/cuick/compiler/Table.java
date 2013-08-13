package jp.long_long_float.cuick.compiler;

import java.util.ArrayList;
import java.util.List;

import jp.long_long_float.cuick.ast.BuiltInCodeStmt;
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
    private List<BuiltInCodeStmt> builtInCodes = new ArrayList<BuiltInCodeStmt>();
    
    public void entryTuple(Type tuple) {
        //この時点では完全な型ではないので(templateがない)
        //if(tuples.indexOf(tuple) == -1) {
        tuples.add(tuple);
        //}
    }
    
    public void entryFunction(Function func) {
        if(!functions.contains(func)) {
            functions.add(func);
        }
    }
    
    public void entryBuiltInCodeStmt(BuiltInCodeStmt code) {
        if(!builtInCodes.contains(code)) {
            builtInCodes.add(code);
        }
    }
    
    /**
     * 一意なtupleのリストを返す
     */
    public List<Type> getTuples() {
        List<Type> ret = new ArrayList<Type>();
        for(Type tuple : tuples) {
            if(!ret.contains(tuple)) {
                ret.add(tuple);
            }
        }
        return ret;
    }
    
    public List<Function> getFunctions() {
        return functions;
    }
    
    public List<BuiltInCodeStmt> getBuiltInCodeStmt() {
        return builtInCodes;
    }
}
