package parser;

import interpreter.TypeError;
import lexer.Token;
import lexer.Tokenizer;

import java.util.HashMap;

/**
 * Represents a parsed Decaf print statement.
 * Created by Christian Yarros on 3/9/2016.
 */
public class PrintStatement extends Statement {

    private Expression expression;

    /**
     * PrintStatement -> PRINT Expression SEMICOLON
     * @param tokenizer To provide tokens
     */

    public PrintStatement(Tokenizer tokenizer) {
        tokenizer.next(Token.Type.PRINT);
        expression = Expression.getExpression(tokenizer);
        tokenizer.next(Token.Type.SEMICOLON);
    }

    public void execute(HashMap<String,Object> state) {
        //Evaluate the expression.
        Object e = expression.evaluate(state);

        // Print it to the console.
        System.out.println(e.toString());
    }

}
