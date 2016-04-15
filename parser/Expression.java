package parser;

import lexer.Token;
import lexer.Tokenizer;

import java.util.HashMap;

/**
 * Represents a parsed Decaf expression.
 * Created by ltorrey on 3/11/2016.
 */
public abstract class Expression {

    /**
     * Expression -> Disjunction
     * @param tokenizer To provide tokens
     * @return A subclass object
     */
    public static Expression getExpression(Tokenizer tokenizer) {
        return new Disjunction(tokenizer);
    }

    /**
     * Evaluate the Decaf expression represented by this object.
     * @param state The current program state
     */
    public abstract Object evaluate(HashMap<String, Object> state);

    /**
     * @return The token that begins this expression.
     */
    public abstract Token firstToken();
}