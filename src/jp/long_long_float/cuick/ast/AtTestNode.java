package jp.long_long_float.cuick.ast;

import java.util.List;

import jp.long_long_float.cuick.compiler.Table;

public class AtTestNode extends AtCommandNode {

    protected List<AtTestCase> inCases, outCases;
    
    public AtTestNode(Location loc, List<AtTestCase> inCases, List<AtTestCase> outCases) {
        super(loc);
        this.inCases = inCases;
        this.outCases = outCases;
        
        Table.getInstance().setTestCases(loc, inCases, outCases);
    }
    
    @Override
    protected void _dump(Dumper d) {
        d.printMember("inCases", inCases);
        d.printMember("outCases", outCases);
    }

}
