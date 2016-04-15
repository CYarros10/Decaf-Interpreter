package parser;

import interpreter.NameError;
import lexer.Token;
import lexer.Tokenizer;

import java.util.HashMap;

/**
 * Represents a parsed Decaf identifier.
 * Created by Christian Yarros on 3/11/2016.
 */
public class Identifier extends Operand {

    /**
     * Identifier -> IDENTIFIER
     * @param tokenizer To provide tokens
     */

    private Token identifier;

    public Identifier(Tokenizer tokenizer) {
        identifier = tokenizer.next(Token.Type.IDENTIFIER);
    }


    public Object evaluate(HashMap<String, Object> state) {

        // If no binding exists for the identifier name, throw a name error on the identifier.
        String name = identifier.text;
        if (!state.containsKey(name))
            throw new NameError(identifier);

        // Otherwise, return the value that is currently bound to the identifier name.
        else
            return (state.get(name));
    }


    /**
     * @return The token that begins this expression.
     */

    @Override
    public Token firstToken() {
        return identifier;
    }


}