package parser;

import lexer.Token;
import lexer.Tokenizer;

/**
 * Represents a parsed Decaf Operand.
 * Created by Christian Yarros on 3/9/2016.
 */
public abstract class Operand extends Expression {
    /**
     * Operand -> Literal | Identifier | Parenthesis
     * @param tokenizer To provide tokens
     */
    public static Operand getOperand(Tokenizer tokenizer) {
        if (tokenizer.hasNext(Token.Type.IDENTIFIER))
            return new Identifier(tokenizer);
        else if (tokenizer.hasNext(Token.Type.INT_LITERAL, Token.Type.FLOAT_LITERAL, Token.Type.TRUE, Token.Type.FALSE))
            return new Literal(tokenizer);
        else
            return new Parenthesis(tokenizer);
    }
}