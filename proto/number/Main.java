public class Main {
    public static void main(String[] args) {
        try {
            Parser parser = new Parser(System.in);
            parser.expr();
        } catch(TokenMgrError ex) {
            System.out.println("Lexer Error : " + ex.getMessage());
        } catch (ParseException ex) {
            System.out.println("Parse Error : " + ex.getMessage());
        }
    }
}