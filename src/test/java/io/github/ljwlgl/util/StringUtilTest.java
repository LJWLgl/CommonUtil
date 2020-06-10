package io.github.ljwlgl.util;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

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


        assertEquals("000000001", StringUtil.appendZero("1", 9));

    }

    @Test
    public void castDouble() {


    }

    @Test
    public void castInt() {

        assertEquals(2, StringUtil.castInt("", 2));


    }

    @Test
    public void reolaceString() {

        assertEquals("我的名字", StringUtil.replaceString("1名字", "1","我的"));
    }

    @Test
    public void getKey() {

        String key = StringUtil.getKey();
    }
}
