package parser;

import interpreter.TypeError;
import lexer.Token;
import lexer.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a parsed Decaf equality.
 * Created by Christian Yarros on 3/11/2016.
 */
public class Equality extends Expression {

    private Relation leftOperand = null;
    private Relation rightOperand = null;

    /**
     * Equality -> Relation (EQ|NEQ Relation)?
     * @param tokenizer To provide tokens
     */

    private Token operator;

    public Equality(Tokenizer tokenizer) {
        leftOperand = new Relation(tokenizer);
        if (tokenizer.hasNext(Token.Type.EQ, Token.Type.NEQ)) {
            operator = tokenizer.next();
            rightOperand = new Relation(tokenizer);
        }
    }

    public Object evaluate(HashMap<String, Object> state) {


        // Evaluate the left operand. If it's the only operand, return its value.
        Object leftValue = leftOperand.evaluate(state);
        if (rightOperand == null) {
            return leftValue;
        }

        // Evaluate the right operand. If one value is a boolean and the other is a number,
        // or vice versa, throw a type error on the second one.
        Object rightValue = rightOperand.evaluate(state);
        if (((leftValue instanceof Number) && (rightValue instanceof Number)) ||
                ((leftValue instanceof Boolean) && (rightValue instanceof Boolean))){

            // Apply the appropriate operation and return the boolean result.
            if (operator.type == Token.Type.EQ){
                return leftValue.equals(rightValue);
            }

            else if (operator.type == Token.Type.NEQ) {
                return !(leftValue.equals(rightValue));
            }
        }
         throw new TypeError(rightOperand.firstToken());
    }


    /**
     * @return The token that begins this expression.
     */

    @Override
    public Token firstToken() {
        return leftOperand.firstToken();
    }

}