package io.github.ljwlgl.util;


import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * @author ：lzz
 * @BelongsProject: io.github.ljwlgl.util
 * @date ：Created in 2020/6/9 15:59
 * @description ：
 * @modified By：
 */
public class StringUtilTest {

    @Test
    public void isEmptyOrNull() {
        boolean rs = StringUtil.isEmptyOrNull("");
        boolean rs1 = StringUtil.isEmptyOrNull(null);
        boolean rs2 = StringUtil.isEmptyOrNull("22");
    }

    @Test
    public void appendZero() {
        String rs = StringUtil.appendZero("1", 9);

    }

    @Test
    public void castDouble() {

        double rs = StringUtil.castDouble("234", 2);
    }

    @Test
    public void castInt() {

        int rs = StringUtil.castInt("", 2);

    }

    @Test
    public void reolaceString() {

        String rs = StringUtil.replaceString("12345677", "1", "我的");
    }

    @Test
    public void getKey() {

        String key=StringUtil.getKey();
    }
}
