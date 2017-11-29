// TokenType is used to enumerate each different type of token and the criteria to match it
// each enum option contains a string, `pattern`, that is a regex which defines what that token looks like
// it is used by the Lexer class to split a string into an array of tokens
// remember that in Java, each enum case is sortof like an object that can hold its own data -- this is very different from enums in C
enum TokenType {
    DOUBLE("[0-9]+\\.[0-9]+"),
	INT("[0-9]+"),
    PLUS("\\+"),
    MINUS("-"),
    TIMES("\\*"),
    DIVIDE("/"),
    ID("x"),
    LPAREN("\\("),
    RPAREN("\\)"),
    EXPO("\\^"),
    COMMA(","),
    FUNCTION("sin\\(|cos\\(|tan\\(|atan\\(|atan2\\(|ln\\(");



    public final String pattern;

    private TokenType(String pattern) {
        this.pattern = pattern;
    }
}