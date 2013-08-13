package jp.long_long_float.cuick.ast;

public class NullStmt extends StmtNode {

    public NullStmt(Location loc) {
        super(loc);
    }
    
    @Override
    public String toString() {
        return ";";
    }

    @Override
    protected void _dump(Dumper d) {
    }

}
