package parser;

import interpreter.TypeError;
import lexer.Token;
import lexer.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a parsed Decaf Addition / Subtraction.
 * Created by Christian Yarros on 3/11/2016.
 */
public class Addition extends Expression {

    private Multiplication leftOperand;
    private ArrayList<Multiplication> rightOperands = new ArrayList<>();
    private ArrayList<Token> operators = new ArrayList<>();
    private Token o;

    /**
     * Addition -> Multiplication (PLUS|MINUS Multiplication)*
     * @param tokenizer To provide tokens
     */
    public Addition(Tokenizer tokenizer) {
        leftOperand = new Multiplication(tokenizer);
        while (tokenizer.hasNext(Token.Type.PLUS, Token.Type.MINUS)) {
            operators.add(tokenizer.next());
            rightOperands.add(new Multiplication(tokenizer));
        }
    }

    /**
     * Evaluate the Decaf expression represented by this object.
     * @param state The current program state
     */
    public Object evaluate(HashMap<String, Object> state) {

        // Evaluate the left operand. If it's the only operand,
        // return its value. Otherwise, if it's not a number value,
        // throw a type error on it.

        Object value = leftOperand.evaluate(state);
        if (rightOperands.size() == 0) {
            return value;
        } else if (!(value instanceof Number)) {
            throw new TypeError(leftOperand.firstToken());
        }

        // Evaluate each other operand one by one. If it's not a number value,
        // throw a type error on it; otherwise apply the appropriate operation.
        // Operations involving only integers produce integer results. Operations
        // involving one or two floats produce float results.

        for (int i = 0; i < rightOperands.size(); i ++) {
            Multiplication m = rightOperands.get(i);
            Object temp = m.evaluate(state);
            if (!(temp instanceof Number)) {
                throw new TypeError(m.firstToken());
            }

            o = operators.get(i);

            if (o.type == Token.Type.PLUS) {
                if (floats(value, temp))
                    value = ((Number) value).doubleValue() + ((Number) temp).doubleValue();
                else
                    value = (Integer) value + (Integer) temp;
            }

            else if (o.type == Token.Type.MINUS) {
                if (floats(value, temp))
                    value = ((Number) value).doubleValue() - ((Number) temp).doubleValue();
                else
                    value = (Integer) value - (Integer) temp;
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