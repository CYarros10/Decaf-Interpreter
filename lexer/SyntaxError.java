package lexer;

/**
 * Custom type of unchecked exception that we will throw
 * to indicate when we detect problems in Decaf syntax.
 * Created by ltorrey on 3/2/2016.
 */
public class SyntaxError extends RuntimeException {

    /**
     * Represents a syntax error at some location in a Decaf program.
     * @param line Line number (1,2,...) of the location.
     * @param column Column number (1,2,...) of the location.
     */
    public SyntaxError(int line, int column) {
        super("Syntax error on line "+line+", column "+column);
    }

    /**
     * Represents a syntax error due to a Decaf token that shouldn't be where it is.
     * @param token The offending token.
     */
    public SyntaxError(Token token) {
        this(token.line, token.column);
    }
}
