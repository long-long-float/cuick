package jp.long_long_float.cuick.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class IntegerRangeNode extends ExprNode {

	Location location;
	int rexpr;
	String rangeStr;
	int lexpr;
	
	public int getBegin() {
		return rexpr;
	}
	
	public int getEnd() {
		return lexpr - (rangeStr == "..." ? 1 : 0);
	}
	
	@Override
	public Location location() {
		return location;
	}

	@Override
	protected void _dump(Dumper d) {
		d.printMember("rexpr", rexpr);
		d.printMember("rangeStr", rangeStr);
		d.printMember("lexpr", lexpr);
	}

}
