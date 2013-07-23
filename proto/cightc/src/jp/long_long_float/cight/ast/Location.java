package jp.long_long_float.cight.ast;

import jp.long_long_float.cight.parser.Token;

public class Location {
    protected String sourceName;
    protected CuickToken token;
    
    public Location(String sourceName, Token token) {
        this(sourceName, new CuickToken(token));
    }
    
    public Location(String sourceName, CuickToken token) {
        this.sourceName = sourceName;
        this.token = token;
    }
    
    public String sourceName() {
        return sourceName;
    }
    
    public CuickToken token() {
        return token;
    }
    
    public int lineno() {
        return token.lineno();
    }
    
    public int column() {
        return token.column();
    }
    
    public String line() {
        return token.includedLine();
    }
    
    public String numberedLine() {
        return "line " + token.lineno() + ": " + line();
    }
    
    public String toString() {
        return sourceName + ":" + token.lineno();
    }
}
