package parser;

import interpreter.TypeError;
import lexer.Token;
import lexer.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a parsed Decaf Relation.
 * Created by Christian Yarros on 3/11/2016.
 */
public class Relation extends Expression {

    private Addition leftOperand = null;
    private Addition rightOperand = null;

    /**
     * Relation -> Addition (LT|LEQ|GT|GEQ Addition)?
     * @param tokenizer To provide tokens
     */

    private Token operator;

    public Relation(Tokenizer tokenizer) {
        leftOperand = new Addition(tokenizer);
        if (tokenizer.hasNext(Token.Type.LT, Token.Type.LEQ, Token.Type.GT, Token.Type.GEQ)) {
            operator = tokenizer.next();
            rightOperand = new Addition(tokenizer);
        }
    }

    public Object evaluate(HashMap<String, Object> state) {

        // Evaluate the left operand. If it's the only operand, return its value.
        // Otherwise, if it's not a number value, throw a type error on it.
        Object leftValue = leftOperand.evaluate(state);
        if (rightOperand == null) {
            return leftValue;
        }
        Object rightValue = rightOperand.evaluate(state);
        if (!(leftValue instanceof Number)) {
            throw new TypeError(leftOperand.firstToken());
        }

        // Evaluate the right operand. If it's not a number value, throw a type error on it.
        if (!(rightValue instanceof Number)) {
            throw new TypeError(rightOperand.firstToken());
        }

        // Apply the appropriate operation and return the boolean result.
        if (operator.type == Token.Type.LT)
           return (((Number) leftValue).doubleValue() < ((Number) rightValue).doubleValue());
        else if (operator.type == Token.Type.LEQ)
            return (((Number) leftValue).doubleValue() <= ((Number) rightValue).doubleValue());
        else if (operator.type == Token.Type.GT)
            return (((Number) leftValue).doubleValue() > ((Number) rightValue).doubleValue());
        else
            return (((Number) leftValue).doubleValue() >= ((Number) rightValue).doubleValue());
    }

    @Override
    /**
     * @return The token that begins this expression.
     */
    public Token firstToken() {
        return leftOperand.firstToken();
    }
}