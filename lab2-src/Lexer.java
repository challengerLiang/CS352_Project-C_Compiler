import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;

// the Lexer class encapsulates all of the logic related to tokens --
// It handles converting an expression string into an array of tokens,
// as well as the functions that the parser uses to consume that array of tokens
public class Lexer {
    private String expression;
    public ArrayList<TokenClass> tokens;

    public Lexer(String expression) throws Exception{
        this.expression = expression.replaceAll("\\s+", ""); // remove all of the spaces from the input expression
        this.tokenize(); // initialize our token array from the expression string
    }

    // Simply prints each token. You wouldn't need anything like this except for debugging
    public void printTokens() {
        for (TokenClass t : tokens) {
            System.out.printf("%s ", t.toString());
        }
	System.out.print("\n");
    }

    // This function converts this.expression into an array of tokens
    private void tokenize() throws Exception{
        tokens = new ArrayList<TokenClass>();
        StringBuilder buffer = new StringBuilder(); // use a StringBuilder because string concatenation in Java is expensive
        
        // loop through all of the enum values (In this example, it will give NUMBER, PLUS, LPAREN, and RPAREN)
        for (TokenType tokenType : TokenType.values()) {
            // here lies the magic...
            // as an `enum`, the TokenType class provides a default String name() method which returns the literal name of the token (such as NUMBER, PLUS, etc...)
            String tokenName = tokenType.name();

            // remember that each TokenType had an associated string called "pattern" that contained the regex needed to match that particular type of token
            String tokenPattern = tokenType.pattern;

            // what we are doing is building up one really long regex from a bunch of small regexes here
            // this creates a series of "matching groups" -- see http://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html#groupname
            // It allows you to match a specific portion of a longer regex and refer to it later by name
            // the () creates a group
            // the ?<string> at the beginning gives that group the name "string"
            // after the name to before the end of the group is the actual pattern that that group should match
            buffer.append(String.format("|(?<%s>%s)", tokenName, tokenPattern));
        }

        // the result of the above loop will be the following string:
        // "|(?<NUMBER>[0-9]+(\\.[0-9]+)?)|(?<PLUS>\+)|(?<LPAREN>\()|(?<RPAREN>\))"
        
        // we create a pattern object from the above regex, minus the first character (since its an extraneous | character that was just an artifact of looping)
        Pattern pattern = Pattern.compile(buffer.substring(1));

        // the pattern object gives us access to a customized matcher object based on the pattern and the input string
        Matcher matcher = pattern.matcher(expression);
        
        // matcher.find() starts at the beginning the string or the end of the last thing found by matcher.find() and returns true if there is anything in the remainder of the string matches anything in the regex

        //denote the end
        int end = 0;
        while (matcher.find()) {       
            // figure out which token it matched
            for (TokenType tokenType : TokenType.values()) {
                if (matcher.group(tokenType.name()) != null) {
                    // the name of the group that was matched corresponds to the name of the TokenType
                    // we have found a token!
                    // a new Token object to the list of tokens with the correct type and data
                    tokens.add(new TokenClass(tokenType, matcher.group(tokenType.name())));

                    if ((end + matcher.group().length()) < matcher.end())
                        throw new Exception("Bad format");
                    end = matcher.end();
                    break;
                }
            }
        }
    }
}
