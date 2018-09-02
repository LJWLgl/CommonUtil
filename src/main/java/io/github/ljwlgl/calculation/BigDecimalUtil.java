package io.github.ljwlgl.calculation;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zqgan
 * @since 2018/9/1
 * BigDecimal的相关计算类
 **/

public class BigDecimalUtil {

    public static double subtract(double x, double y) {
        BigDecimal d1 = new BigDecimal(x);
        BigDecimal d2 = new BigDecimal(y);
        return d1.subtract(d2).doubleValue();
    }

    public static double add(double x, double y) {
        BigDecimal d1 = new BigDecimal(x);
        BigDecimal d2 = new BigDecimal(y);
        return d1.add(d2).doubleValue();
    }

    public static double multiply(double x, double y) {
        BigDecimal d1 = new BigDecimal(x);
        BigDecimal d2 = new BigDecimal(y);
        return d1.multiply(d2).doubleValue();
    }

    public static double divide(double x, double y, int scale) {
        BigDecimal d1 = new BigDecimal(x);
        BigDecimal d2 = new BigDecimal(y);
        return d1.divide(d2, scale).doubleValue();
    }
    
    public static double roundUp(double val) {
        return roundUp(val, 0);
    }

    public static double roundUp(double val, int scale) {
        BigDecimal dec = new BigDecimal(val);
        return dec.setScale(scale, RoundingMode.UP).doubleValue();
    }
    
    public static double roundDown(double val) {
        return roundDown(val, 0);
    }
    
    public static double roundDown(double val, int scale) {
        BigDecimal dec = new BigDecimal(val);
        return dec.setScale(scale, RoundingMode.DOWN).doubleValue();
    }
}
