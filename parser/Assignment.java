package parser;

import interpreter.NameError;
import interpreter.TypeError;
import lexer.Token;
import lexer.Tokenizer;

import javax.swing.plaf.nimbus.State;
import java.util.HashMap;

/**
 * Represents a parsed Decaf assignment.
 * Created by Christian Yarros on 3/11/2016.
 */
public class Assignment extends Statement {

    private Expression expression;
    private Token identifier;

    /**
     * Assignment -> IDENTIFIER ASSIGN Expression SEMICOLON
     * @param tokenizer To provide tokens
     */
    public Assignment(Tokenizer tokenizer) {
        identifier = tokenizer.next(Token.Type.IDENTIFIER);
        tokenizer.next(Token.Type.ASSIGN);
        expression = Expression.getExpression(tokenizer);
        tokenizer.next(Token.Type.SEMICOLON);
    }

    /**
     * Add an identifier to the program state.
     * @param state The current program state
     */
    public void execute(HashMap<String,Object> state) {

        String name = identifier.text;

        // Evaluate the expression to get the new value for this binding.
        Object newValue = expression.evaluate(state);


        // If no binding exists for the identifier name, throw a name error on the identifier.
        if (!state.containsKey(name))
            throw new NameError(identifier);

        // If the new value has the same type as the value currently bound to this
        // identifier, update the binding.

        else if ((state.get(name) instanceof Boolean) && (newValue instanceof Boolean)) {
            Boolean b = ((Boolean) newValue).booleanValue();
            state.put(name, b);
        }

        else if ((state.get(name) instanceof Integer) && (newValue instanceof Integer)) {
            Integer i = ((Integer) newValue).intValue();
            state.put(name, i);
        }

        else if ((state.get(name) instanceof Float) && (newValue instanceof Float)) {
            Float f = ((Integer) newValue).floatValue();
            state.put(name, f);
        }


        // If the new value is an int and the old value is a float,
        // convert to the new value to a float and update the binding.
        else if ((state.get(name) instanceof Float) && (newValue instanceof Integer)) {
            Float f = ((Integer) newValue).floatValue();
            state.put(name, f);
        }

        // If there is any other type of value mismatch, throw a type error.
        else
            throw new TypeError(expression.firstToken());
    }



}

