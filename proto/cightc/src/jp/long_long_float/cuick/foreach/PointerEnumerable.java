package jp.long_long_float.cuick.foreach;

import jp.long_long_float.cuick.ast.Dumper;
import jp.long_long_float.cuick.ast.ExprNode;
import jp.long_long_float.cuick.ast.ForEachNode;
import jp.long_long_float.cuick.ast.LiteralNode;
import jp.long_long_float.cuick.ast.RangeNode;
import jp.long_long_float.cuick.cppStructure.ForStmt;
import jp.long_long_float.cuick.type.CInt;

public class PointerEnumerable extends Enumerable {
    private ExprNode pointer;
    private RangeNode range;
    
    public PointerEnumerable(ExprNode pointer, RangeNode range) {
        super(pointer.location());
        this.pointer = pointer;
        this.range = range;
    }

    public PointerEnumerable(ExprNode pointer, ExprNode rexpr) {
        this(pointer, new RangeNode(new LiteralNode(null, new CInt(), "0"), "...", rexpr));
    }
    
    public ExprNode pointer() {
        return pointer;
    }
    
    public RangeNode range() {
        return range;
    }

    @Override
    public String toString(ForEachNode forEachNode) {
        String name = forEachNode.var().name();
        //TODO iを他とかぶらないようにする
        ForStmt forStmt = new ForStmt(
                "int i = " + range.begin(), 
                "i " + range.getOperator() + " " + range.end(), 
                "i++");
        //TODO ここでpointer.type() + "& " + name + " = " + pointer + "[i]";を追加する
        return forStmt.toString();
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("pointer", pointer);
        d.printMember("range", range);
    }
}
