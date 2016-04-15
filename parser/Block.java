package parser;

import interpreter.TypeError;
import lexer.Token;
import lexer.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a parsed Decaf block.
 * Created by Christian Yarros on 3/11/2016.
 */
public class Block extends Statement {

    private ArrayList<Statement> statements = new ArrayList<Statement>();

    /**
     * Block -> LBRACE Statement* RBRACE
     * @param tokenizer To provide tokens
     */
    public Block(Tokenizer tokenizer) {
        tokenizer.next(Token.Type.LBRACE);
        while (!tokenizer.hasNext(Token.Type.RBRACE)) {
            statements.add(Statement.getStatement(tokenizer));
        }
        tokenizer.next(Token.Type.RBRACE);
    }

    /*
    * Execute the statements in the order they appear.
     */
    public void execute(HashMap<String,Object> state) {
        for (Statement s : statements) {
            s.execute(state);
        }
    }
}
