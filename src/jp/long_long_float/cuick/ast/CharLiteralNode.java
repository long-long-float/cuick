package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.type.CChar;

public class CharLiteralNode extends LiteralNode {

    public CharLiteralNode(Location location, String ch) {
        super(location, new CChar(location), ch);
    }

}
