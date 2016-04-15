package parser;

import interpreter.TypeError;
import lexer.Token;
import lexer.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a parsed Decaf disjunction.
 * Created by Christian Yarros on 3/11/2016.
 */
public class Disjunction extends Expression {

    private Conjunction leftOperand;
    private ArrayList<Conjunction> rightOperands = new ArrayList();
    private ArrayList<Token> operators = new ArrayList<>();
    private Token o;

    /**
     * Disjunction -> Conjunction (OR Conjunction)*
     * @param tokenizer To provide tokens
     */
    public Disjunction(Tokenizer tokenizer) {
        leftOperand = new Conjunction(tokenizer);
        while (tokenizer.hasNext(Token.Type.OR)) {
            operators.add(tokenizer.next());
            rightOperands.add(new Conjunction(tokenizer));
        }
    }

    /**
     * Evaluate the Decaf expression represented by this object.
     * @param state The current program state
     */
    public Object evaluate(HashMap<String, Object> state) {

        Object value = leftOperand.evaluate(state);

        // Evaluate the left operand. If it's the only operand, return its value.
        // Otherwise, if it's not a boolean value, throw a type error on it.
        if (rightOperands.size() == 0) {
            return value;
        }
        else if (!(value instanceof Boolean)) {
            throw new TypeError(leftOperand.firstToken());
        }
        // this ignores future numbers in the expression (but logical short circuits)
        else if ((Boolean) value) {
            return true;
        }

        else {
            // Evaluate each other operand one by one. If it's not a boolean value,
            // throw a type error on it. If it evaluates to true, perform logical
            // short-circuiting: return true without evaluating the rest.

            for (int i = 0; i < rightOperands.size(); i ++) {

                Conjunction c = rightOperands.get(i);
                value = c.evaluate(state);
                o = operators.get(i);

                if (!(value instanceof Boolean)) {
                    throw new TypeError(c.firstToken());
                }
                else if ((Boolean) value) {
                    return true;
                }
            }
        }

        // If all of the operands evaluated to false, return false.
        return false;
    }

    /**
     * @return The token that begins this expression.
     */
    @Override
    public Token firstToken() {
        return leftOperand.firstToken();
    }

}
