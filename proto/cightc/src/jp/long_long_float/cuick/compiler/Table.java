package jp.long_long_float.cuick.compiler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.long_long_float.cuick.ast.AtTestCase;
import jp.long_long_float.cuick.ast.BuiltInCodeStmt;
import jp.long_long_float.cuick.type.Type;
import jp.long_long_float.cuick.utility.Pair;

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
    private List<BuiltInCodeStmt> builtInCodes = new ArrayList<BuiltInCodeStmt>();
    private Pair<List<AtTestCase>, List<AtTestCase>> testCases = null;
    
    public void entryTuple(Type tuple) {
        //この時点では完全な型ではないので(templateがない)
        //if(tuples.indexOf(tuple) == -1) {
        tuples.add(tuple);
        //}
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
    
    public String getRealTupleName(Type tuple) {
        return "tuple" + getTuples().indexOf(tuple);
    }

    public List<BuiltInCodeStmt> getBuiltInCodeStmt() {
        return builtInCodes;
    }

    public void setTestCases(List<AtTestCase> inCases, List<AtTestCase> outCases) {
        testCases = new Pair<List<AtTestCase>, List<AtTestCase>>(inCases, outCases);
    }
    
    public boolean equalsInCasesAndOutCases() {
        return testCases.getFirst().size() == testCases.getSecond().size();
    }

    public boolean isEnabledTest() {
        return testCases != null;
    }
    
    public List<Pair<AtTestCase, AtTestCase>> getTestCases() {
        if(!equalsInCasesAndOutCases()) return null;
        
        List<Pair<AtTestCase, AtTestCase>> ret = new ArrayList<Pair<AtTestCase,AtTestCase>>(testCases.getFirst().size());
        Iterator<AtTestCase> inItr = testCases.getFirst().iterator(), outItr = testCases.getSecond().iterator();
        while(inItr.hasNext()) {
            ret.add(new Pair<AtTestCase, AtTestCase>(inItr.next(), outItr.next()));
        }
        return ret;
    }
}
