/* Generated By:JavaCC: Do not edit this line. ParserConstants.java */
package jp.long_long_float.cuick.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int SPACES = 1;
  /** RegularExpression Id. */
  int BLOCK_COMMENT = 4;
  /** RegularExpression Id. */
  int LINE_COMMENT = 5;
  /** RegularExpression Id. */
  int BREAK = 6;
  /** RegularExpression Id. */
  int CASE = 7;
  /** RegularExpression Id. */
  int CATCH = 8;
  /** RegularExpression Id. */
  int CHAR = 9;
  /** RegularExpression Id. */
  int CLASS = 10;
  /** RegularExpression Id. */
  int CONST = 11;
  /** RegularExpression Id. */
  int CONTINUE = 12;
  /** RegularExpression Id. */
  int DEFAULT_ = 13;
  /** RegularExpression Id. */
  int DELETE = 14;
  /** RegularExpression Id. */
  int DO = 15;
  /** RegularExpression Id. */
  int DOUBLE = 16;
  /** RegularExpression Id. */
  int ELSE = 17;
  /** RegularExpression Id. */
  int ENUM = 18;
  /** RegularExpression Id. */
  int EXPLICIT = 19;
  /** RegularExpression Id. */
  int EXPORT = 20;
  /** RegularExpression Id. */
  int EXTERN = 21;
  /** RegularExpression Id. */
  int FLOAT = 22;
  /** RegularExpression Id. */
  int FOR = 23;
  /** RegularExpression Id. */
  int FRIEND = 24;
  /** RegularExpression Id. */
  int GOTO = 25;
  /** RegularExpression Id. */
  int IF = 26;
  /** RegularExpression Id. */
  int INLINE = 27;
  /** RegularExpression Id. */
  int INT = 28;
  /** RegularExpression Id. */
  int LAST = 29;
  /** RegularExpression Id. */
  int LONG = 30;
  /** RegularExpression Id. */
  int MUTABLE = 31;
  /** RegularExpression Id. */
  int NAMESPACE = 32;
  /** RegularExpression Id. */
  int NEW = 33;
  /** RegularExpression Id. */
  int OPERATOR = 34;
  /** RegularExpression Id. */
  int PRIVATE = 35;
  /** RegularExpression Id. */
  int PROTECTED = 36;
  /** RegularExpression Id. */
  int PUBLIC = 37;
  /** RegularExpression Id. */
  int REGISTER = 38;
  /** RegularExpression Id. */
  int RETURN = 39;
  /** RegularExpression Id. */
  int SHORT = 40;
  /** RegularExpression Id. */
  int SIGNED = 41;
  /** RegularExpression Id. */
  int SIZEOF = 42;
  /** RegularExpression Id. */
  int STATIC = 43;
  /** RegularExpression Id. */
  int STRUCT = 44;
  /** RegularExpression Id. */
  int SWITCH = 45;
  /** RegularExpression Id. */
  int TEMPLATE = 46;
  /** RegularExpression Id. */
  int THROW = 47;
  /** RegularExpression Id. */
  int TRY = 48;
  /** RegularExpression Id. */
  int TYPEDEF = 49;
  /** RegularExpression Id. */
  int TYPEID = 50;
  /** RegularExpression Id. */
  int TYPENAME = 51;
  /** RegularExpression Id. */
  int UNION = 52;
  /** RegularExpression Id. */
  int UNSIGNED = 53;
  /** RegularExpression Id. */
  int USING = 54;
  /** RegularExpression Id. */
  int VIRTUAL = 55;
  /** RegularExpression Id. */
  int VOID = 56;
  /** RegularExpression Id. */
  int VOLATILE = 57;
  /** RegularExpression Id. */
  int WHILE = 58;
  /** RegularExpression Id. */
  int DOLLAR = 59;
  /** RegularExpression Id. */
  int EXTEND = 60;
  /** RegularExpression Id. */
  int AS = 61;
  /** RegularExpression Id. */
  int IDENTIFIER = 62;
  /** RegularExpression Id. */
  int INTEGER = 63;
  /** RegularExpression Id. */
  int CHARACTER = 68;
  /** RegularExpression Id. */
  int STRING = 73;
  /** RegularExpression Id. */
  int BUILT_IN_CODE = 77;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int IN_BLOCK_COMMENT = 1;
  /** Lexical state. */
  int IN_CHARACTER = 2;
  /** Lexical state. */
  int CHARACTER_TERM = 3;
  /** Lexical state. */
  int IN_STRING = 4;
  /** Lexical state. */
  int IN_BUILT_IN_CODE = 5;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "<SPACES>",
    "\"/*\"",
    "<token of kind 3>",
    "\"*/\"",
    "<LINE_COMMENT>",
    "\"break\"",
    "\"case\"",
    "\"catch\"",
    "\"char\"",
    "\"class\"",
    "\"const\"",
    "\"continue\"",
    "\"default\"",
    "\"delete\"",
    "\"do\"",
    "\"double\"",
    "\"else\"",
    "\"enum\"",
    "\"explicit\"",
    "\"export\"",
    "\"extern\"",
    "\"float\"",
    "\"for\"",
    "\"friend\"",
    "\"goto\"",
    "\"if\"",
    "\"inline\"",
    "\"int\"",
    "\"last\"",
    "\"long\"",
    "\"mutable\"",
    "\"namespace\"",
    "\"new\"",
    "\"operator\"",
    "\"private\"",
    "\"protected\"",
    "\"public\"",
    "\"register\"",
    "\"return\"",
    "\"short\"",
    "\"signed\"",
    "\"sizeof\"",
    "\"static\"",
    "\"struct\"",
    "\"switch\"",
    "\"template\"",
    "\"throw\"",
    "\"try\"",
    "\"typedef\"",
    "\"typeid\"",
    "\"typename\"",
    "\"union\"",
    "\"unsigned\"",
    "\"using\"",
    "\"virtual\"",
    "\"void\"",
    "\"volatile\"",
    "\"while\"",
    "\"$\"",
    "\"extend\"",
    "\"as\"",
    "<IDENTIFIER>",
    "<INTEGER>",
    "\"\\\'\"",
    "<token of kind 65>",
    "<token of kind 66>",
    "<token of kind 67>",
    "\"\\\'\"",
    "\"\\\"\"",
    "<token of kind 70>",
    "<token of kind 71>",
    "<token of kind 72>",
    "\"\\\"\"",
    "\"`\"",
    "<token of kind 75>",
    "\"\\\\`\"",
    "\"`\"",
    "\"(\"",
    "\")\"",
    "\"{\"",
    "\"&\"",
    "\"*\"",
    "\",\"",
    "\";\"",
    "\"[\"",
    "\"]\"",
    "\"=\"",
    "\"}\"",
    "\".\"",
    "\"::\"",
    "\"-\"",
    "\":\"",
    "\">\"",
    "\"<\"",
    "\"...\"",
    "\"..\"",
    "\"+=\"",
    "\"-=\"",
    "\"*=\"",
    "\"/=\"",
    "\"%=\"",
    "\"&=\"",
    "\"|=\"",
    "\"^=\"",
    "\"<<=\"",
    "\">>=\"",
    "\"**=\"",
    "\"?\"",
    "\"||\"",
    "\"&&\"",
    "\">=\"",
    "\"<=\"",
    "\"==\"",
    "\"!=\"",
    "\"|\"",
    "\"^\"",
    "\">>\"",
    "\"<<\"",
    "\"+\"",
    "\"**\"",
    "\"/\"",
    "\"%\"",
    "\"++\"",
    "\"--\"",
    "\"!\"",
    "\"~\"",
    "\"->\"",
    "\"@input\"",
    "\"@debug\"",
    "\"@memo\"",
    "\"@while\"",
    "\"@test\"",
    "\"in:\"",
    "\"out:\"",
  };

}
