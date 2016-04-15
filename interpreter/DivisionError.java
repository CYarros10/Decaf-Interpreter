package interpreter;

import lexer.Token;

public class DivisionError extends RuntimeException {

    public DivisionError(Token token) {
        super("Division by zero on line " + token.line + ", column " + token.column);
    }
}