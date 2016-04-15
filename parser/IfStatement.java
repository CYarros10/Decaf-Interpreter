package parser;

import interpreter.TypeError;
import lexer.Token;
import lexer.Tokenizer;

import java.util.HashMap;

/**
 * Represents a parsed Decaf if statement.
 * Created by Christian Yarros on 3/11/2016.
 */
public class IfStatement extends Statement {

    private Expression expression;
    private Statement ifstatement;
    private Statement elsestatement;

    /**
     * IfStatement -> IF LPAREN Expression RPAREN Statement (ELSE Statement)?
     * @param tokenizer To provide tokens
     */
    public IfStatement(Tokenizer tokenizer) {
        tokenizer.next(Token.Type.IF);
        tokenizer.next(Token.Type.LPAREN);
        expression = Expression.getExpression(tokenizer);
        tokenizer.next(Token.Type.RPAREN);
        ifstatement = Statement.getStatement(tokenizer);

        if (tokenizer.hasNext(Token.Type.ELSE)) {
            tokenizer.next(Token.Type.ELSE);
            elsestatement = Statement.getStatement(tokenizer);
        }
    }

    public void execute(HashMap<String,Object> state) {

        // Evaluate the expression; if it's not a boolean value, throw a type error on it.
        Object value = expression.evaluate(state);
        if (!(value instanceof Boolean)) {
            throw new TypeError(expression.firstToken());
        }

        // If the expression evaluates to true, execute the first statement.
        else if ((Boolean) value) {
            ifstatement.execute(state);
        }
        // Otherwise, if there is a second statement, execute that.
        else if (elsestatement != null) {
            elsestatement.execute(state);
        }
    }
}
