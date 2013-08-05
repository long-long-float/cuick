package jp.long_long_float.cuick.ast;

import java.util.List;

import jp.long_long_float.cuick.parser.Token;

public class AtTestCase extends Node {

    Location location;
    List<Token> lines;
    
    public AtTestCase(Location loc, List<Token> lines) {
        this.location = loc;
        this.lines = lines;
    }
    
    @Override
    public Location location() {
        return location;
    }

    @Override
    protected void _dump(Dumper d) {
        d.printMember("lines", lines);
    }

}
