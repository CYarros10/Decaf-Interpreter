package parser;

import interpreter.NameError;
import lexer.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ltorrey on 3/18/2016.
 */
public class Declaration {

    private Token identifier, dataType;
    private ArrayList<Token> moreIdentifiers = new ArrayList<>();

    /**
     * Declaration -> (INT|FLOAT|BOOLEAN) IDENTIFIER (COMMA IDENTIFIER)* SEMICOLON
     */
    public Declaration(Tokenizer tokenizer) {

        dataType = tokenizer.next(Token.Type.INT, Token.Type.FLOAT, Token.Type.BOOLEAN);
        identifier = tokenizer.next(Token.Type.IDENTIFIER);

        while(tokenizer.hasNext(Token.Type.COMMA)) {
            tokenizer.next();
            moreIdentifiers.add(tokenizer.next(Token.Type.IDENTIFIER));
        }
        tokenizer.next(Token.Type.SEMICOLON);
    }

    /**
     * Execute the Decaf declaration represented by this object.
     * @param state The current program state
     */
    public void execute(HashMap<String,Object> state) {
        declare(identifier, state);
        for (Token id: moreIdentifiers)
            declare(id, state);
    }

    /**
     * Add an identifier to the program state.
     * @param id The identifier token
     * @param state The current program state
     */
    private void declare(Token id, HashMap<String,Object> state) {

        // Error on duplicate name declarations
        String name = id.text;
        if (state.containsKey(name))
            throw new NameError(id);
        // Default values depend on data type
        if (dataType.type == Token.Type.INT ) {
            state.put(name, 0);
        }
        else if (dataType.type == Token.Type.FLOAT)
            state.put(name, 0.0);
        else
            state.put(name, false);
    }
}
