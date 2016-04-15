package parser;

import interpreter.TypeError;
import lexer.Token;
import lexer.Tokenizer;

import java.util.HashMap;

/**
 * Represents a parsed Decaf Parenthesis.
 * Created by Christian Yarros on 3/9/2016.
 */
public class Parenthesis extends Operand {

    private Expression expression;

    /**
     * Parenthesis -> LPAREN Expression RPAREN
     *
     * @param tokenizer To provide tokens
     */

    public Parenthesis(Tokenizer tokenizer) {
        tokenizer.next(Token.Type.LPAREN);
        expression = Expression.getExpression(tokenizer);
        tokenizer.next(Token.Type.RPAREN);
    }

    /**
     * Evaluate the Decaf expression represented by this object.
     * @param state The current program state
     */
    public Object evaluate(HashMap<String, Object> state) {

        // Evaluate the expression and return its value.
        return expression.evaluate(state);
    }


    /**
     * @return The token that begins this expression.
     */
    @Override
    public Token firstToken() {
        return expression.firstToken();
    }
}