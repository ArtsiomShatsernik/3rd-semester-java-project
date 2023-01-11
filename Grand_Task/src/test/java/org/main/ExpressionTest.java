package org.main;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressionTest {

    @Test
    public void compute() {
    }

    @Test
    public void simplify() {
        Expression test = new Expression("1 + (-1) + (25) + (36)");
        test.simplify();
        Assert.assertEquals("1 + [-1] + 25 + 36", test.getExp());
    }

    @Test
    public void isCorrect() {
    }
}