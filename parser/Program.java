package parser;

import lexer.Token;
import lexer.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a parsed Decaf program.
 * Created by ltorrey on 3/9/2016.
 */
    public class Program {

        private ArrayList<Declaration> declarations = new ArrayList();
        private ArrayList<Statement> statements = new ArrayList();

        /**
         * Program -> Declaration* Statement*
         * @param tokenizer To provide tokens
         */
        public Program(Tokenizer tokenizer) {

            while (tokenizer.hasNext(Token.Type.INT, Token.Type.FLOAT, Token.Type.BOOLEAN))
                declarations.add(new Declaration(tokenizer));

            while (tokenizer.hasNext())
                statements.add(Statement.getStatement(tokenizer));

        }

        /**
         * Run the Decaf program represented by this object.
         * @return The final program state
         */
        public HashMap<String,Object> run() {

            HashMap<String,Object> state = new HashMap();

            for (Declaration declaration: declarations)
                declaration.execute(state);

            for (Statement statement : statements)
                statement.execute(state);

            return state;
        }
    }