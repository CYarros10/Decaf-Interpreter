package lexer;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Like a Scanner but especially for Decaf tokens.
 * Started by ltorrey on 2/29/2016 and completed by Christian Yarros.
 */
public class Tokenizer {

    ArrayList<Token> tokens = new ArrayList<>();

    // Instance variables for syntax errors
    int row = 1;
    int column = 1;
    /**
     * Read all lines from the scanner and make a list of tokens.
     * Don't include the comment and whitespace types in the list.
     * Syntax error if there's a token that matches none of the types.
     * @param scanner Points at a code file or string
     */
    public Tokenizer(Scanner scanner) {
        row = 0;

        // take an entire line of code for evaluation
        while (scanner.hasNextLine()) {
            String code = scanner.nextLine();
            column = 1;

            // increment row for each line
            row++;

            // Continue while the line has characters left.
            while (code.length() != 0) {

                // Keep track of the number of tokens checked. if all have been checked then
                // there must be an error (illegal value)
                int tokenCount = 0;

                // iterate through the token types
                for (Token.Type type : Token.Type.values()) {
                    tokenCount ++;

                    // Get the type of the current text
                    String text = type.getMatch(code);

                    // If a token was matched, create it and
                    // add it to the list of tokens
                    if (text != null) {

                        // Create token, move column count to current position,
                        // set String code to everything after current token.
                        Token token = new Token(type, text, row, column);
                        column += text.length(); // move column index
                        code = code.substring(text.length(), code.length());

                        // If the current token isnt whitespace or a comment, add it
                        // to the tokens list.
                        if (!type.equals(Token.Type.WHITESPACE) && !type.equals(Token.Type.COMMENT)) {
                            tokens.add(token);
                        }

                        // break out of the for loop since a token was found.
                        break;
                    }

                    // if all tokens have been checked and no match,
                    // throw a syntax error.
                    if (tokenCount == Token.Type.values().length) {
                        throw new SyntaxError(row,column);
                    }
                }
            }
        }
    }

    /**
     * @return Whether the list contains another token.
     */
    public boolean hasNext() {
        if (tokens.isEmpty())
            return false;
        else
            return true;
    }

    /**
     * @param types Zero or more token types (variable-arity)
     * @return Whether the first token in the list is one of these types.
     */
    public boolean hasNext(Token.Type ... types) {
        if (tokens.isEmpty())
            return false;
        else {
            for (int i = 0; i < types.length; i ++) {
                if (tokens.get(0).type.equals(types[i]))
                    return true;
            }
        }
        return false;
    }
    /**
     * Consumes and returns a token.
     * Syntax error if there isn't one.
     * @return The first token in the list.
     */
    public Token next() {
        if (tokens.isEmpty())
            throw new SyntaxError(row,column); //fix this
        else
            return tokens.remove(0);
    }

    /**
     * Consumes and returns a token.
     * Syntax error if there isn't one of these types.
     * @param types Zero or more token types (variable-arity)
     * @return The first token in the list.
     */
    public Token next(Token.Type ... types) {
        if (tokens.isEmpty())
            throw new SyntaxError(row,column);
        else {
            for (int i = 0; i < types.length; i ++) {
                if (tokens.get(0).type.equals(types[i]))
                    return tokens.remove(0);
            }
        }
        throw new SyntaxError(tokens.get(0));
    }
}
