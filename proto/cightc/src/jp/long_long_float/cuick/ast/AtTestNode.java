package jp.long_long_float.cuick.ast;

import java.util.List;

public class AtTestNode extends AtCommandNode {

    protected List<AtTestCase> inCases, outCases;
    
    public AtTestNode(Location loc, List<AtTestCase> inCases, List<AtTestCase> outCases) {
        super(loc);
        this.inCases = inCases;
        this.outCases = outCases;
    }
    
    @Override
    protected void _dump(Dumper d) {
        d.printMember("inCases", inCases);
        d.printMember("outCases", outCases);
    }

}
