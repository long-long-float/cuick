package jp.long_long_float.cuick.ast;

import jp.long_long_float.cuick.type.BasicType;


public class StringLiteralNode extends LiteralNode {

    public StringLiteralNode(Location loc, String value) {
        super(loc, new BasicType("char", loc).addPointer(), value);
    }

}
