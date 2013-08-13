package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.compiler.Table;

public class BuiltInCodeStmt extends StmtNode {

    protected String code;
    
    public BuiltInCodeStmt(Location loc, String code) {
        super(loc);
        this.code = code;
        
        Table.getInstance().entryBuiltInCodeStmt(this);
    }
    
    @Override
    public String toString() {
        return code.replace("`", "");
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("code", code);
    }

}
