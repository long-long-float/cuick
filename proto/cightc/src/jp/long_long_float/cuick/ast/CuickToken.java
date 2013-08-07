package jp.long_long_float.cuick.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.long_long_float.cuick.parser.ParserConstants;
import jp.long_long_float.cuick.parser.Token;
import jp.long_long_float.cuick.utility.TextUtils;

public class CuickToken implements Iterable<CuickToken>{
    protected Token token;
    protected boolean isSpecial;
    
    public CuickToken(Token token) {
        this(token, false);
    }
    
    public CuickToken(Token token, boolean isSpecial) {
        this.token = token;
        this.isSpecial = isSpecial;
    }
    
    public String toString() {
        return token.image;
    }
    
    public boolean isSpecial() {
        return this.isSpecial;
    }
    
    public int kindID() {
        return token.kind;
    }
    
    public String kindName() {
        return ParserConstants.tokenImage[token.kind];
    }
    
    public int lineno() {
        return token.beginLine;
    }
    
    public int column() {
        return token.beginColumn;
    }
    
    public String image() {
        return token.image;
    }
    
    public String dumpedImage() {
        return TextUtils.dumpString(token.image);
    }
    
    @Override
    public Iterator<CuickToken> iterator() {
        return buildTokenList(token, false).iterator();
    }
    
    protected List<CuickToken> tokensWithoutFirstSpecials() {
        return buildTokenList(token, true);
    }
    
    /**
     * TokenのListを生成する
     */
    protected List<CuickToken> buildTokenList(Token first, boolean rejectFirstSpecials) {
        List<CuickToken> result = new ArrayList<CuickToken>();
        boolean rejectSpecials = rejectFirstSpecials;
        for(Token t = first; t != null; t = t.next) {
            if (t.specialToken != null && !rejectSpecials) {
                Token s = specialTokenHead(t.specialToken);
                for(;s != null;s = s.next) {
                    result.add(new CuickToken(s));
                }
            }
            result.add(new CuickToken(t));
            rejectSpecials = false;
        }
        return result;
    }
    
    protected Token specialTokenHead(Token firstSpecial) {
        Token s = firstSpecial;
        while(s.specialToken != null) s = s.specialToken;
        return s;
    }
    
    public String includedLine() {
        StringBuffer buf = new StringBuffer();
        for(CuickToken t : tokensWithoutFirstSpecials()) {
            int index = t.image().indexOf("\n");
            if(index >= 0) {
                buf.append(t.image().substring(0, index));
                break;
            }
            buf.append(t.image());
        }
        return buf.toString();
    }

}
