package io.github.ljwlgl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BigDecimalUtilTest {

    @Test
    public void testSubtract() {
        assertEquals(1.72, BigDecimalUtil.subtract(5.65, 3.93), 0.01);
    }

    @Test
    public void testSubtractUp() {
        assertEquals(2, BigDecimalUtil.subtractUp(5.65, 3.93), 1);
    }

    @Test
    public void testSubtractDown() {
        assertEquals(1, BigDecimalUtil.subtractDown(5.65, 3.93), 1);
    }

    @Test
    public void testAdd() {
        assertEquals(20.8, BigDecimalUtil.add(19.9, 0.9), 0.0001);
    }

    @Test
    public void testMultiply() {
        assertEquals(13.68, BigDecimalUtil.multiply(4.5, 3.04), 0.0001);
    }

    @Test
    public void testDivide() {
        assertEquals(3.04, BigDecimalUtil.divide(13.68, 4.5, 0), 0.0001);
    }

    @Test
    public void testRoundUp() {
        assertEquals(7, BigDecimalUtil.roundUp(6.3), 1);
    }

    @Test
    public void testRoundUp1() {
        assertEquals(1.33, BigDecimalUtil.roundUp(1.324, 2), 0.01);
    }

    @Test
    public void testRoundDown() {
        assertEquals(1, BigDecimalUtil.roundDown(1.6), 1);
    }

    @Test
    public void testRoundDown1() {
        assertEquals(1.74, BigDecimalUtil.roundDown(1.746, 2), 0.01);
    }

    @Test
    public void testRoundHalfUp() {
        assertEquals(2, BigDecimalUtil.roundHalfUp(1.746), 1);
    }

    @Test
    public void testRoundHalfUp1() {
        assertEquals(1.75, BigDecimalUtil.roundHalfUp(1.746, 2), 0.01);
    }
}
