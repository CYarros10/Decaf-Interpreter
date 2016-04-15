package parser;

import interpreter.TypeError;
import lexer.Token;
import lexer.Tokenizer;

import java.util.HashMap;

/**
 * Represents a parsed Decaf while statement.
 * Created by Christian Yarros on 3/11/2016.
 */
public class WhileStatement extends Statement {

    private Expression expression;
    private Statement statement;

    /**
     * WhileStatement -> WHILE LPAREN Expression RPAREN Statement
     * @param tokenizer To provide tokens
     */
    public WhileStatement(Tokenizer tokenizer) {
        tokenizer.next(Token.Type.WHILE);
        tokenizer.next(Token.Type.LPAREN);
        expression = Expression.getExpression(tokenizer);
        tokenizer.next(Token.Type.RPAREN);
        statement = Statement.getStatement(tokenizer);
    }

    /**
     *
     *      * @param state The current program state
     */
    public void execute(HashMap<String,Object> state) {

       // Evaluate the expression; if it's not a boolean value, throw a type error on it.
        Object value = expression.evaluate(state);
       if (!(value instanceof Boolean)) {
           throw new TypeError(expression.firstToken());
       }

        else {
           // While the expression evaluates to true, execute the statement
           // and re-evaluate the expression.
           while ((Boolean) value) {
               statement.execute(state);
               value = expression.evaluate(state);
           }
       }
    }
}

