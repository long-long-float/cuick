package jp.long_long_float.cuick.ast;

import java.util.List;

import jp.long_long_float.cuick.foreach.Enumerable;
import jp.long_long_float.cuick.type.Type;
import jp.long_long_float.cuick.utility.Pair;

public class ForEachNode extends StmtNode {

    protected List<Pair<Type, String>> vars;
    protected boolean isFore;
    protected List<Enumerable> enumerables;
    protected StmtNode body;
    protected StmtNode lastBody;
    
    public ForEachNode(Location loc, List<Pair<Type, String>> vars, boolean isFore, List<Enumerable> enumerables, StmtNode body, StmtNode lastBody) {
        super(loc);
        this.vars = vars;
        this.isFore = isFore;
        this.enumerables = enumerables;
        this.body = body;
        this.lastBody = lastBody;
    }
    
    @Override
    protected void _dump(Dumper d) {
        d.printMember("vars", vars);
        d.printMember("isFore", isFore);
        d.printMember("enumerables", enumerables);
        d.printMember("body", body);
        d.printMember("lastBody", lastBody);
    }

}
