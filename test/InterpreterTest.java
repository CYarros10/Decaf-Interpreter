package test;

import interpreter.DivisionError;
import interpreter.NameError;
import interpreter.TypeError;
import lexer.SyntaxError;
import lexer.Tokenizer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import parser.Declaration;
import parser.Expression;
import parser.Program;

import java.util.HashMap;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Test suite for the semantic analysis stage.
 */
public class InterpreterTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /*
    * ==============
    * Declarations
    * ==============
     */

    @Test
    public void program() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("// Print all prime numbers between 1 and 100\n" +
                "\n" +
                "int number, factor;\n" +
                "boolean prime;\n" +
                "\n" +
                "number = 2;\n" +
                "while (number < 100) {\n" +
                "    prime = true;\n" +
                "    \n" +
                "    factor = 2;\n" +
                "    while (factor < number) {\n" +
                "        if (number % factor == 0)\n" +
                "            prime = false; \n" +
                "        factor = factor + 1;\n" +
                "    }\n" +
                "    \n" +
                "    if (prime) {\n" +
                "        print number;\n" +
                "    }\n" +

                "    \n" +
                "    number = number + 1;\n" +
                "}"
                ));
        Program program = new Program(tokenizer);
        program.run();
    }

    @Test
    public void declarationNameError() {
        exception.expect(NameError.class);
        exception.expectMessage("Name error on line 1, column 8");
        Tokenizer tokenizer = new Tokenizer(new Scanner("int x, x;"));
        Declaration declaration = new Declaration(tokenizer);
        declaration.execute(new HashMap());
    }

    /*
    * ============
    * Expressions
    * ============
     */
    @Test
    public void disjunction() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("true || false"));
        Expression expression = Expression.getExpression(tokenizer);
        assertEquals(true, expression.evaluate(new HashMap()));
    }

    @Test
    public void conjunction() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("true && false"));
        Expression expression = Expression.getExpression(tokenizer);
        assertEquals(false, expression.evaluate(new HashMap()));
    }

    @Test
    public void equality() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("8 == 8"));
        Expression expression = Expression.getExpression(tokenizer);
        assertEquals(true, expression.evaluate(new HashMap()));
    }

    @Test
    public void relation() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("8 > 4"));
        Expression expression = Expression.getExpression(tokenizer);
        assertEquals(true, expression.evaluate(new HashMap()));
    }

    @Test
    public void addition() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("10+4"));
        Expression expression = Expression.getExpression(tokenizer);
        assertEquals(14, expression.evaluate(new HashMap()));
    }

    @Test
    public void multiplication() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("5*5*5"));
        Expression expression = Expression.getExpression(tokenizer);
        assertEquals(125, expression.evaluate(new HashMap()));
    }

    @Test
    public void negation() {
        Tokenizer tokenizer = new Tokenizer(new Scanner("!true"));
        Expression expression = Expression.getExpression(tokenizer);
        assertEquals(false, expression.evaluate(new HashMap()));
    }

    /*
    * =================
    * Type Errors
    * =================
     */
    @Test
    public void negationTypeError1() {
        exception.expect(TypeError.class);
        exception.expectMessage("Type error on line 1, column 2");
        Tokenizer tokenizer = new Tokenizer(new Scanner("!5"));
        Expression expression = Expression.getExpression(tokenizer);
        expression.evaluate(new HashMap());
    }

    @Test
    public void negationTypeError2() {
        exception.expect(TypeError.class);
        exception.expectMessage("Type error on line 1, column 2");
        Tokenizer tokenizer = new Tokenizer(new Scanner("-true"));
        Expression expression = Expression.getExpression(tokenizer);
        expression.evaluate(new HashMap());
    }

    @Test
    public void multiplicationDivideError() {
        exception.expect(DivisionError.class);
        exception.expectMessage("Division by zero on line 1, column 3");
        Tokenizer tokenizer = new Tokenizer(new Scanner("5/0"));
        Expression expression = Expression.getExpression(tokenizer);
        expression.evaluate(new HashMap());
    }

    @Test
    public void disjunctionTypeError1() {
        exception.expect(TypeError.class);
        exception.expectMessage("Type error on line 1, column 1");
        Tokenizer tokenizer = new Tokenizer(new Scanner("5 || false"));
        Expression expression = Expression.getExpression(tokenizer);
        expression.evaluate(new HashMap());
    }

    @Test
    public void disjunctionTypeError2() {
        exception.expect(TypeError.class);
        exception.expectMessage("Type error on line 1, column 9");
        Tokenizer tokenizer = new Tokenizer(new Scanner("true || 7"));
        Expression expression = Expression.getExpression(tokenizer);
        expression.evaluate(new HashMap());
    }

    @Test
    public void conjunctionTypeError1() {
        exception.expect(TypeError.class);
        exception.expectMessage("Type error on line 1, column 1");
        Tokenizer tokenizer = new Tokenizer(new Scanner("5 && false"));
        Expression expression = Expression.getExpression(tokenizer);
        expression.evaluate(new HashMap());
    }

    @Test
    public void conjunctionTypeError2() {
        exception.expect(TypeError.class);
        exception.expectMessage("Type error on line 1, column 10");
        Tokenizer tokenizer = new Tokenizer(new Scanner("false && 7"));
        Expression expression = Expression.getExpression(tokenizer);
        expression.evaluate(new HashMap());
    }

    @Test
    public void equalityTypeError1() {
        exception.expect(TypeError.class);
        exception.expectMessage("Type error on line 1, column 6");
        Tokenizer tokenizer = new Tokenizer(new Scanner("5 == true"));
        Expression expression = Expression.getExpression(tokenizer);
        expression.evaluate(new HashMap());
    }

    @Test
    public void equalityTypeError2() {
        exception.expect(TypeError.class);
        exception.expectMessage("Type error on line 1, column 10");
        Tokenizer tokenizer = new Tokenizer(new Scanner("false == 7"));
        Expression expression = Expression.getExpression(tokenizer);
        expression.evaluate(new HashMap());
    }

    @Test
    public void relationTypeError1() {
        exception.expect(TypeError.class);
        exception.expectMessage("Type error on line 1, column 5");
        Tokenizer tokenizer = new Tokenizer(new Scanner("5 > true"));
        Expression expression = Expression.getExpression(tokenizer);
        expression.evaluate(new HashMap());
    }

    @Test
    public void additionTypeError1() {
        exception.expect(TypeError.class);
        exception.expectMessage("Type error on line 1, column 1");
        Tokenizer tokenizer = new Tokenizer(new Scanner("false + 7"));
        Expression expression = Expression.getExpression(tokenizer);
        expression.evaluate(new HashMap());
    }

    @Test
    public void additionTypeError2() {
        exception.expect(TypeError.class);
        exception.expectMessage("Type error on line 1, column 5");
        Tokenizer tokenizer = new Tokenizer(new Scanner("7 + true"));
        Expression expression = Expression.getExpression(tokenizer);
        expression.evaluate(new HashMap());
    }

    @Test
    public void multiplicationTypeError1() {
        exception.expect(TypeError.class);
        exception.expectMessage("Type error on line 1, column 1");
        Tokenizer tokenizer = new Tokenizer(new Scanner("false * 7"));
        Expression expression = Expression.getExpression(tokenizer);
        expression.evaluate(new HashMap());
    }

    @Test
    public void multiplicationTypeError2() {
        exception.expect(TypeError.class);
        exception.expectMessage("Type error on line 1, column 5");
        Tokenizer tokenizer = new Tokenizer(new Scanner("7 * true"));
        Expression expression = Expression.getExpression(tokenizer);
        expression.evaluate(new HashMap());
    }

}
