package org.main;

import org.junit.Assert;
import org.junit.Test;


public class ExpressionTest {

    @Test
    public void compute() {
    }

    @Test
    public void simplify() {
        Expression test = new Expression("1 + (-1) + (25) + (36)");
        String actual = test.simplify();
        Assert.assertEquals("1 + [-1] + 25 + 36", actual);
    }
    @Test
    public void isCorrect1() {
        Expression test = new Expression("(1 + 1) * (2 + 2)");
        Boolean actual = test.isCorrect();
        Assert.assertEquals(true, actual);
    }
    @Test
    public void isCorrect2() {
        Expression test = new Expression("(1 + 1 * (2 + 2)");
        Boolean actual = test.isCorrect();
        Assert.assertEquals(false, actual);
    }
    @Test
    public void isCorrect3() {
        Expression test = new Expression("(((((1 + 1)))))");
        Boolean actual = test.isCorrect();
        Assert.assertEquals(true, actual);
    }
    @Test
    public void isCorrect4() {
        Expression test = new Expression("((1 + 1) * 2 ) + (1 + 1)");
        Boolean actual = test.isCorrect();
        Assert.assertEquals(true, actual);
    }
    @Test
    public void isCorrect5() {
        Expression test = new Expression("2 + 2");
        Boolean actual = test.isCorrect();
        Assert.assertEquals(true, actual);
    }
}