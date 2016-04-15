package test;

import lexer.SyntaxError;
import lexer.Token;
import lexer.Tokenizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Scanner;
import static org.junit.Assert.*;


/**
 * Test suite for the lexical analysis stage.
 */
public class LexerTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void commentToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("// Comment \n while"));
        assertTrue(tokenizer.hasNext());
    }

    @Test
    public void whileToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("while"));
        Token token = tokenizer.next(Token.Type.WHILE);
        assertEquals("WHILE[while]@1,1", token.toString());
    }

    @Test
    public void lineToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner(
                "print ;"));
        Token token = tokenizer.next(Token.Type.PRINT);
        assertEquals("PRINT[print]@1,1", token.toString());
        Token token2 = tokenizer.next(Token.Type.SEMICOLON);
        assertEquals("SEMICOLON[;]@1,7", token2.toString());
    }
    @Test
    public void line2Token(){
        Tokenizer tokenizer = new Tokenizer(new Scanner(
                "print ;\n&&"));
        Token token = tokenizer.next(Token.Type.PRINT);
        assertEquals("PRINT[print]@1,1", token.toString());
        Token token2 = tokenizer.next(Token.Type.SEMICOLON);
        assertEquals("SEMICOLON[;]@1,7", token2.toString());
        Token token3 = tokenizer.next(Token.Type.AND);
        assertEquals("AND[&&]@2,1", token3.toString());
    }


    @Test
    public void hasnextToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("// Comment \n&&"));
        assertTrue(tokenizer.hasNext());
    }

    @Test
    public void badToken() {
        exception.expect(SyntaxError.class);
        exception.expectMessage("Syntax error on line 1, column 1");
        new Tokenizer(new Scanner("?"));
    }

    @Test
    public void semicolonToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner(";"));
        Token token = tokenizer.next();
        assertEquals("SEMICOLON[;]@1,1", token.toString());
    }
    @Test
    public void commaToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner(","));
        Token token = tokenizer.next();
        assertEquals("COMMA[,]@1,1", token.toString());
    }
    @Test
    public void lbraceToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("{"));
        Token token = tokenizer.next();
        assertEquals("LBRACE[{]@1,1", token.toString());
    }
    @Test
    public void rbraceToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("}"));
        Token token = tokenizer.next();
        assertEquals("RBRACE[}]@1,1", token.toString());
    }
    @Test
    public void lparenToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("("));
        Token token = tokenizer.next();
        assertEquals("LPAREN[(]@1,1", token.toString());
    }
    @Test
    public void rparenToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner(")"));
        Token token = tokenizer.next();
        assertEquals("RPAREN[)]@1,1", token.toString());
    }
    @Test
    public void ifToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("if"));
        Token token = tokenizer.next();
        assertEquals("IF[if]@1,1", token.toString());
    }
    @Test
    public void elseToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("else"));
        Token token = tokenizer.next();
        assertEquals("ELSE[else]@1,1", token.toString());
    }
    @Test
    public void printToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("print"));
        Token token = tokenizer.next();
        assertEquals("PRINT[print]@1,1", token.toString());
    }
    @Test
    public void intToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("int"));
        Token token = tokenizer.next();
        assertEquals("INT[int]@1,1", token.toString());
    }
    @Test
    public void floatToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("float"));
        Token token = tokenizer.next();
        assertEquals("FLOAT[float]@1,1", token.toString());
    }
    @Test
    public void booleanToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("boolean"));
        Token token = tokenizer.next();
        assertEquals("BOOLEAN[boolean]@1,1", token.toString());
    }
    @Test
    public void trueToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("true"));
        Token token = tokenizer.next();
        assertEquals("TRUE[true]@1,1", token.toString());
    }
    @Test
    public void falseToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("false"));
        Token token = tokenizer.next();
        assertEquals("FALSE[false]@1,1", token.toString());
    }
    @Test
    public void int_literalToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("1345234"));
        Token token = tokenizer.next();
        assertEquals("INT_LITERAL[1345234]@1,1", token.toString());
    }
    @Test
    public void float_literalToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("443.1234"));
        Token token = tokenizer.next();
        assertEquals("FLOAT_LITERAL[443.1234]@1,1", token.toString());
    }
    @Test
    public void identifierToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("w_99phillip"));
        Token token = tokenizer.next();
        assertEquals("IDENTIFIER[w_99phillip]@1,1", token.toString());
    }
    @Test
    public void assignToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("="));
        Token token = tokenizer.next();
        assertEquals("ASSIGN[=]@1,1", token.toString());
    }
    @Test
    public void plusToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("+"));
        Token token = tokenizer.next();
        assertEquals("PLUS[+]@1,1", token.toString());
    }
    @Test
    public void minusToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("-"));
        Token token = tokenizer.next();
        assertEquals("MINUS[-]@1,1", token.toString());
    }
    @Test
    public void multToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("*"));
        Token token = tokenizer.next();
        assertEquals("MULT[*]@1,1", token.toString());
    }
    @Test
    public void divToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("/"));
        Token token = tokenizer.next();
        assertEquals("DIV[/]@1,1", token.toString());
    }
    @Test
    public void modToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("%"));
        Token token = tokenizer.next();
        assertEquals("MOD[%]@1,1", token.toString());
    }
    @Test
    public void eqToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("=="));
        Token token = tokenizer.next();
        assertEquals("EQ[==]@1,1", token.toString());
    }
    @Test
    public void neqToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("!="));
        Token token = tokenizer.next();
        assertEquals("NEQ[!=]@1,1", token.toString());
    }
    @Test
    public void ltToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("<"));
        Token token = tokenizer.next();
        assertEquals("LT[<]@1,1", token.toString());
    }
    @Test
    public void gtoken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner(">"));
        Token token = tokenizer.next();
        assertEquals("GT[>]@1,1", token.toString());
    }
    @Test
    public void leqToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("<="));
        Token token = tokenizer.next();
        assertEquals("LEQ[<=]@1,1", token.toString());
    }
    @Test
    public void GEQToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner(">="));
        Token token = tokenizer.next();
        assertEquals("GEQ[>=]@1,1", token.toString());
    }
    @Test
    public void andToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("&&"));
        Token token = tokenizer.next();
        assertEquals("AND[&&]@1,1", token.toString());
    }
    @Test
    public void orToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("||"));
        Token token = tokenizer.next();
        assertEquals("OR[||]@1,1", token.toString());
    }
    @Test
    public void notToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("!"));
        Token token = tokenizer.next();
        assertEquals("NOT[!]@1,1", token.toString());
    }
    @Test
    public void commentCheckToken() {
        exception.expect(SyntaxError.class);
        exception.expectMessage("Syntax error on line 1, column 1");
        Tokenizer tokenizer = new Tokenizer(new Scanner("?"));
        Token token = tokenizer.next();
    }

    @Test
    public void line3Token(){
        exception.expect(SyntaxError.class);
        exception.expectMessage("Syntax error on line 2, column 1");
        new Tokenizer(new Scanner("|| \n?"));
    }

    @Test
    public void whitespaceToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner(""));
        assertFalse(tokenizer.hasNext());
    }
    @Test
    public void commentWhitespaceToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("//   "));
        assertFalse(tokenizer.hasNext());
    }
    @Test
    public void togetherToken() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("          234+gamg2\n-;"));
        Token token = tokenizer.next(Token.Type.INT_LITERAL);
        assertEquals("INT_LITERAL[234]@1,11", token.toString());
        Token token2 = tokenizer.next(Token.Type.PLUS);
        assertEquals("PLUS[+]@1,14", token2.toString());
        Token token3 = tokenizer.next(Token.Type.IDENTIFIER);
        assertEquals("IDENTIFIER[gamg2]@1,15", token3.toString());
        Token token4 = tokenizer.next(Token.Type.MINUS);
        assertEquals("MINUS[-]@2,1", token4.toString());
        Token token5 = tokenizer.next(Token.Type.SEMICOLON);
        assertEquals("SEMICOLON[;]@2,2", token5.toString());
    }

}
