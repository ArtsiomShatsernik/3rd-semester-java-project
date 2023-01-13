package org.main;

import org.junit.Assert;
import org.junit.Test;


public class ExpressionTest {
    @Test
    public void compute1() {
        Expression test = new Expression("(1 + 2) * (3 + 4)");
        String actual = test.compute();
        Assert.assertEquals("21.0", actual);
    }
    @Test
    public void compute2() {
        Expression test = new Expression("(1 + (2 * (56 / 7)) - 1)");
        String actual = test.compute();
        Assert.assertEquals("16.0", actual);
    }
    @Test(expected = RuntimeException.class)
    public void compute3() {
        Expression test = new Expression("2 / 0");
        test.compute();
    }
    @Test(expected = RuntimeException.class)
    public void computeWithExtension3() {
        Expression test = new Expression("2 / 0");
        test.computeWithExtension();
    }
    @Test
    public void compute4() {
        Expression test = new Expression("1 + (-2) * (-1)");
        String actual = test.compute();
        Assert.assertEquals("3.0", actual);
    }
    @Test
    public void computeWithExtension4() {
        Expression test = new Expression("1 + (-2) * (-1)");
        String actual = test.computeWithExtension();
        Assert.assertEquals("3.0", actual);
    }

    @Test
    public void simplify() {
        Expression test = new Expression("1 + (-1) + (25) + (36)");
        String actual = test.simplify(test.getExp());
        Assert.assertEquals("1 + -1 + 25 + 36", actual);
    }

    @Test
    public void toRPN() {
        Expression test = new Expression("1 + 1");
        StringBuilder builder = new StringBuilder("3 + 4 * 10 / 2");
        StringBuilder res = test.toRPN(builder);
        Assert.assertEquals("3 4 10 * 2 / + ", res.toString());
    }

    @Test
    public void computeRPN() {
        Expression test = new Expression("1 + 1");
        String res = test.computeRPN("1 * 4 + 2 * 4 + 3");
        Assert.assertEquals("15.0", res);
    }
}