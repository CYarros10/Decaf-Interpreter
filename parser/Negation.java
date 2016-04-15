package parser;

import interpreter.TypeError;
import lexer.Token;
import lexer.Tokenizer;

import java.util.HashMap;

/**
 * Represents a parsed Decaf Negation.
 * Created by ltorrey on 3/28/2016.
 */
public class Negation extends Expression {

    private Token operator;
    private Operand operand;

    /**
     * Negation -> (MINUS|NOT)? Operand
     */
    public Negation(Tokenizer tokenizer) {
        if (tokenizer.hasNext(Token.Type.MINUS, Token.Type.NOT))
            operator = tokenizer.next();
        operand = Operand.getOperand(tokenizer);
    }

    /**
     * Evaluate the Decaf expression represented by this object.
     * @param state The current program state
     */
    public Object evaluate(HashMap<String, Object> state) {

        // No operator case
        Object value = operand.evaluate(state);
        if (operator == null)
            return value;

        // Valid operator cases
        if (operator.type == Token.Type.MINUS && value instanceof Number)
            return minus((Number)value);

        if (operator.type == Token.Type.NOT && value instanceof Boolean)
            return not((Boolean)value);

        // Invalid operator case
        throw new TypeError(operand.firstToken());
    }

    /**
     * Meaning of the MINUS operator.
     * @param x A number
     * @return The negative of that number (type preserved)
     */
    private Number minus(Number x) {
        if (x instanceof Integer)
            return -x.intValue();
        else
            return -x.doubleValue();
    }

    /**
     * Meaning of the NOT operator.
     * @param x A boolean
     * @return The opposite of that boolean
     */
    private Boolean not(Boolean x) {
        return !x;
    }

    /**
     * @return The token that begins this expression.
     */
    public Token firstToken() {
        if (operator != null)
            return operator;
        else
            return operand.firstToken();
    }
}