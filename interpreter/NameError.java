package interpreter;

import lexer.Token;

public class NameError extends RuntimeException {

    public NameError(Token token) {
        super("Name error on line " + token.line + ", column " + token.column);
    }
}