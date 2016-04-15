package test;

import lexer.SyntaxError;
import lexer.Tokenizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import parser.Expression;
import parser.Program;
import parser.Statement;

import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Test suite for the syntax analysis stage.
 */
public class ParserTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Test
    public void disjunction() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("x&&y || y"));
        Expression.getExpression(tokenizer);
        assertFalse(tokenizer.hasNext());
    }
    @Test
    public void incompleteDisjunction() {
        exception.expect(SyntaxError.class);
        exception.expectMessage("Syntax error on line 1, column 6");
        Tokenizer tokenizer = new Tokenizer(new Scanner("x&&y | y"));
        Expression.getExpression(tokenizer);
    }
    @Test
    public void conjunction() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("(x&&y) || y"));
        Expression.getExpression(tokenizer);
        assertFalse(tokenizer.hasNext());
    }
    @Test
    public void incompleteConjunction() {
        exception.expect(SyntaxError.class);
        exception.expectMessage("Syntax error on line 1, column 2");
        Tokenizer tokenizer = new Tokenizer(new Scanner("(&&y) || y"));
        Expression.getExpression(tokenizer);
    }
    @Test
    public void equality() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("((x>2)&&y) || y"));
        Expression.getExpression(tokenizer);
        assertFalse(tokenizer.hasNext());
    }
    @Test
    public void incompleteEquality() {
        exception.expect(SyntaxError.class);
        exception.expectMessage("Syntax error on line 1, column 9");
        Tokenizer tokenizer = new Tokenizer(new Scanner("((x>2)&&) || y"));
        Expression.getExpression(tokenizer);
    }
    @Test
    public void relation() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("(((x+1)>2)&&y) || y"));
        Expression.getExpression(tokenizer);
        assertFalse(tokenizer.hasNext());
    }
    @Test
    public void incompleteRelation() {
        exception.expect(SyntaxError.class);
        exception.expectMessage("Syntax error on line 1, column 9");
        Tokenizer tokenizer = new Tokenizer(new Scanner("(((x+1)>)&&y) || y"));
        Expression.getExpression(tokenizer);
    }
    @Test
    public void addition() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("((((x*z)+1)>2)&&y) || y"));
        Expression.getExpression(tokenizer);
        assertFalse(tokenizer.hasNext());
    }
    @Test
    public void incompleteAddition() {
        exception.expect(SyntaxError.class);
        exception.expectMessage("Syntax error on line 1, column 10");
        Tokenizer tokenizer = new Tokenizer(new Scanner("((((x*z)+)>2)&&y) || y"));
        Expression.getExpression(tokenizer);
    }
    @Test
    public void multiplication() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("(((((-x)*z)+1)>2)&&y) || y"));
        Expression.getExpression(tokenizer);
        assertFalse(tokenizer.hasNext());
    }
    @Test
    public void incompleteMultiplication() {
        exception.expect(SyntaxError.class);
        exception.expectMessage("Syntax error on line 1, column 10");
        Tokenizer tokenizer = new Tokenizer(new Scanner("(((((-x)*)+1)>2)&&y) || y"));
        Expression.getExpression(tokenizer);
    }
    @Test
    public void negation() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("(((((-4)*z)+1)>2)&&y) || y"));
        Expression.getExpression(tokenizer);
        assertFalse(tokenizer.hasNext());
    }
    @Test
    public void incompleteNegation() {
        exception.expect(SyntaxError.class);
        exception.expectMessage("Syntax error on line 1, column 7");
        Tokenizer tokenizer = new Tokenizer(new Scanner("(((((-)*z)+1)>2)&&y) || y"));
        Expression.getExpression(tokenizer);
    }

    @Test
    public void printStatement() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("print x;"));
        Statement.getStatement(tokenizer);
        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void incompletePrintStatement() {
        exception.expect(SyntaxError.class);
        exception.expectMessage("Syntax error on line 1, column 8");
        Tokenizer tokenizer = new Tokenizer(new Scanner("print x"));
        Statement.getStatement(tokenizer);
    }

    @Test
    public void whileStatement() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("while (x > 1) {print x;}"));
        Statement.getStatement(tokenizer);
        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void ifStatement() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("if (x > 1) {print x;}"));
        Statement.getStatement(tokenizer);
        assertFalse(tokenizer.hasNext());
    }

    @Test // Doesn't work with multiple assignments
    public void assignment() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("x=5;"));
        Statement.getStatement(tokenizer);
        assertFalse(tokenizer.hasNext());
    }

    @Test
    public void declaration() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("int number, factor;\n" +
                "boolean prime;"));
        Program pro = new Program(tokenizer);
        assertFalse(tokenizer.hasNext());
    }
    @Test
    public void incorrectDeclaration() {
        exception.expect(SyntaxError.class);
        exception.expectMessage("Syntax error on line 2, column 14");
        Tokenizer tokenizer = new Tokenizer(new Scanner("int number, factor;\n" +
                "boolean prime"));
        Program pro = new Program(tokenizer);
        assertFalse(tokenizer.hasNext());
    }
}
