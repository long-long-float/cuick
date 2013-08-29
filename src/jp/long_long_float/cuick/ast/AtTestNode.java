package jp.long_long_float.cuick.ast;

import java.util.List;

import jp.long_long_float.cuick.compiler.Table;
import lombok.Getter;

@Getter
public class AtTestNode extends AtCommandNode {

	private IntegerRangeNode range;
    private List<AtTestCase> inCases, outCases;
    
    public AtTestNode(Location loc, IntegerRangeNode range, List<AtTestCase> inCases, List<AtTestCase> outCases) {
        super(loc);
        this.range = range;
        this.inCases = inCases;
        this.outCases = outCases;
        
        Table.getInstance().setAtTestNode(this);
    }
    
    public boolean isRangeTest() {
    	return range != null;
    }

	@Override
    protected void _dump(Dumper d) {
        d.printMember("inCases", inCases);
        d.printMember("outCases", outCases);
    }

}
