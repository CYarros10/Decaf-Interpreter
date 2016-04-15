package parser;

import interpreter.DivisionError;
import interpreter.TypeError;
import lexer.Token;
import lexer.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a parsed Decaf Multiplication / Division.
 * Created by Christian Yarros on 3/11/2016.
 */
public class Multiplication extends Expression {

    private Negation leftOperand;
    private ArrayList<Negation> rightOperands = new ArrayList();
    private ArrayList<Token> operators = new ArrayList();
    private Token o;

    /**
     * Multiplication -> Negation (MULT|DIV|MOD Negation)*
     * @param tokenizer To provide tokens
     */
    public Multiplication(Tokenizer tokenizer) {
        leftOperand = new Negation(tokenizer);
        while (tokenizer.hasNext(Token.Type.MULT, Token.Type.DIV, Token.Type.MOD)) {
            o = tokenizer.next();
            operators.add(o);
            rightOperands.add(new Negation(tokenizer));
        }
    }

    public Object evaluate(HashMap<String, Object> state) {

        // Evaluate the left operand. If it's the only operand, return its value.
        // Otherwise, if it's not a number value, throw a type error on it.
        Object value = leftOperand.evaluate(state);
        if (rightOperands.size() == 0) {
            return value;
        } else if (!(value instanceof Number)) {
            throw new TypeError(leftOperand.firstToken());
        }

        // Evaluate each other operand one by one. If it's not a number value,
        // throw a type error on it; otherwise apply the appropriate operation.
        // Operations involving only integers produce integer results.
        // Operations involving one or two floats produce float results. If there
        // is a divide or mod by zero, throw a division error on the zero operand.

        for (int i = 0; i < rightOperands.size(); i ++) {

            Negation n = rightOperands.get(i);

            Object temp = n.evaluate(state);
            if (!(temp instanceof Number)) {
                throw new TypeError(n.firstToken());
            }

            o = operators.get(i);

                if (o.type == Token.Type.MULT) {
                    if (floats(value, temp))
                        value = ((Number) value).doubleValue() * ((Number) temp).doubleValue();
                    else
                        value = (Integer) value * (Integer) temp;
                }

                else if (o.type == Token.Type.DIV) {

                    if (temp.equals(0))
                        throw new DivisionError(n.firstToken());
                    else if (floats(value, temp))
                        value = ((Number) value).doubleValue() / ((Number) temp).doubleValue();
                    else
                        value = (Integer) value / (Integer) temp;
                }

                else if (o.type == Token.Type.MOD) {
                    if (temp.equals(0))
                        throw new DivisionError(n.firstToken());
                    else if (floats(value, temp))
                        value = ((Number) value).doubleValue() % ((Number) temp).doubleValue();
                    else
                        value = (Integer) value % (Integer) temp;
                }
        }

        // Return the final result after all the operations have been applied from left to right.
        return value;
    }

    /**
     * @return The token that begins this expression.
     */

    @Override
    public Token firstToken() {
            return leftOperand.firstToken();
    }

    /**
     * Check if two objects are instances of Float
     * @param x,y objects
     */
    private boolean floats(Object x, Object y) {
        return (x instanceof Float || y instanceof Float);
    }
}