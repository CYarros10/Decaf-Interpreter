package parser;

import interpreter.TypeError;
import lexer.Token;
import lexer.Tokenizer;

import java.util.HashMap;


/**
 * Represents a parsed Decaf literal.
 * Created by Christian Yarros on 3/9/2016.
 */
public class Literal extends Operand {

    /**
     * Literal -> INT_LITERAL | FLOAT_LITERAL | TRUE | FALSE
     * @param tokenizer To provide tokens
     * @return An object of the appropriate subclass
     */

    private Token literal;

    public Literal(Tokenizer tokenizer) {

        if (tokenizer.hasNext(Token.Type.INT_LITERAL))
            literal = tokenizer.next(Token.Type.INT_LITERAL);
        else if (tokenizer.hasNext(Token.Type.FLOAT_LITERAL))
            literal = tokenizer.next(Token.Type.FLOAT_LITERAL);
        else if (tokenizer.hasNext(Token.Type.TRUE))
            literal = tokenizer.next(Token.Type.TRUE);
        else
            literal = tokenizer.next(Token.Type.FALSE);
    }


    // Create and return an object to represent the value of the literal text.
    public Object evaluate(HashMap<String, Object> state) {
        String value = literal.text;

        if (literal.type == Token.Type.INT_LITERAL) {
            return Integer.valueOf(value);
        }
        else if (literal.type == Token.Type.FLOAT_LITERAL) {
            return Float.valueOf(value);
        }
        else
            return Boolean.valueOf(value);
    }

    /**
     * @return The token that begins this expression.
     */
    @Override
    public Token firstToken() {
        return literal;
    }
}