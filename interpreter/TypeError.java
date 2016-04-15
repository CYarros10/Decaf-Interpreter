package interpreter;

import lexer.Token;

public class TypeError extends RuntimeException {

    public TypeError(Token token) {
        super("Type error on line " + token.line + ", column " + token.column);
    }
}