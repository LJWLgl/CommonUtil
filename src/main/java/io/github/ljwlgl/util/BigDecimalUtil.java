package io.github.ljwlgl.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zqgan
 * @since 2018/9/1
 * BigDecimal的相关计算类
 **/

public class BigDecimalUtil {

    /**
     * x, y转成BigDecimal后相减
     * @param x x值（double类型）
     * @param y y值 （double类型）
     * @return result
     */
    public static double subtract(double x, double y) {
        BigDecimal d1 = BigDecimal.valueOf(x);
        BigDecimal d2 = BigDecimal.valueOf(y);
        return d1.subtract(d2).doubleValue();
    }

    /**
     * x, y转成BigDecimal后相减，获得的结果再向上取整
     * @param x x值（double类型）
     * @param y y值 （double类型）
     * @return result
     */
    public static double subtractUp(double x, double y) {
        double value = subtract(x, y);
        return roundUp(value);
    }

    /**
     * x, y转成BigDecimal后相减，获得的结果再向下取整
     * @param x x值（double类型）
     * @param y y值 （double类型）
     * @return result
     */
    public static double subtractDown(double x, double y) {
        double value = subtract(x, y);
        return roundDown(value);
    }

    /**
     * x, y转成BigDecimal后相减
     * @param x x值（double类型）
     * @param y y值 （double类型）
     * @return result
     */
    public static double add(double x, double y) {
        BigDecimal d1 = BigDecimal.valueOf(x);
        BigDecimal d2 = BigDecimal.valueOf(y);
        return d1.add(d2).doubleValue();
    }

    /**
     * x, y转成BigDecimal后相乘
     * @param x x值（double类型）
     * @param y y值 （double类型）
     * @return result
     */
    public static double multiply(double x, double y) {
        BigDecimal d1 = BigDecimal.valueOf(x);
        BigDecimal d2 = BigDecimal.valueOf(y);
        return d1.multiply(d2).doubleValue();
    }

    /**
     * x, y转成BigDecimal后相除
     * @param x x值（double类型）
     * @param y y值 （double类型）
     * @return result
     */
    public static double divide(double x, double y, int scale) {
        BigDecimal d1 = BigDecimal.valueOf(x);
        BigDecimal d2 = BigDecimal.valueOf(y);
        return d1.divide(d2, scale).doubleValue();
    }

    /**
     * 向上取整，整数向上取整
     * @param val val
     * @return result
     */
    public static double roundUp(double val) {
        return roundUp(val, 0);
    }


    /**
     * 向上取整，可设置精度
     * @param val val
     * @param scale 精度
     * @return result
     */
    public static double roundUp(double val, int scale) {
        BigDecimal dec = BigDecimal.valueOf(val);
        return dec.setScale(scale, RoundingMode.UP).doubleValue();
    }

    /**
     * 向下取整，可设置精度
     * @param val val
     * @return result
     */
    public static double roundDown(double val) {
        return roundDown(val, 0);
    }

    /**
     * 向下取整，可设置精度
     * @param val val
     * @param scale 精度
     * @return result
     */
    public static double roundDown(double val, int scale) {
        BigDecimal dec = BigDecimal.valueOf(val);
        return dec.setScale(scale, RoundingMode.DOWN).doubleValue();
    }

    /**
     * 四舍五入
     * @param val val
     * @return result
     */
    public static double roundHalfUp(double val) {
        return roundHalfUp(val, 0);
    }

    /**
     * 四舍五入，可设置精度
     * @param val
     * @param scale
     * @return
     */
    public static double roundHalfUp(double val, int scale) {
        BigDecimal dec = BigDecimal.valueOf(val);
        return dec.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
