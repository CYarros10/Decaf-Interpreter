package parser;

import interpreter.TypeError;
import lexer.Token;
import lexer.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a parsed Decaf conjunction.
 * Created by Christian Yarros on 3/11/2016.
 */
public class Conjunction extends Expression {

    private Equality leftOperand;
    private ArrayList<Equality> rightOperands = new ArrayList();
    private ArrayList<Token> operators = new ArrayList<>();
    private Token o;

    /**
     * Conjunction -> Equality (AND Equality)*
     * @param tokenizer To provide tokens
     */
    public Conjunction(Tokenizer tokenizer) {
        leftOperand = new Equality(tokenizer);
        while (tokenizer.hasNext(Token.Type.AND)) {
            o = tokenizer.next();
            operators.add(o);
            rightOperands.add(new Equality(tokenizer));
        }
    }

    public Object evaluate(HashMap<String, Object> state) {


        // Evaluate the left operand. If it's the only operand, return its value.
        // Otherwise, if it's not a boolean value, throw a type error on it.
        Object value = leftOperand.evaluate(state);
        if (rightOperands.size() == 0) {
            return value;
        }
        else if (!(value instanceof Boolean)) {
            throw new TypeError(leftOperand.firstToken());
        }


        // this ignores future numbers in the expression (but logical short circuits)
        else if (!(Boolean) value) {
            return false;
        }

        else {

            // Evaluate each other operand one by one. If it's not a boolean value,
            // throw a type error on it. If it evaluates to false, perform logical
            // short-circuiting: return false without evaluating the rest.

            for (int i = 0; i < rightOperands.size(); i++) {

                Equality e = rightOperands.get(i);
                value = e.evaluate(state);
                o = operators.get(i);

                if (!(value instanceof Boolean)) {
                    throw new TypeError(e.firstToken());
                }
                else if (!(Boolean) value) {
                    return false;
                }
            }
        }

        // If all of the operands evaluated to true, return true
        return true;
    }

    @Override
    /**
     * @return The token that begins this expression.
     */
    public Token firstToken() {
        return leftOperand.firstToken();
    }

}