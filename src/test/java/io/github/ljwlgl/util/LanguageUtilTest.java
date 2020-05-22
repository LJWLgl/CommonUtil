package io.github.ljwlgl.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class LanguageUtilTest {

    @Test
    public void testIsLetterAndNumber() {
        Assert.assertTrue(LanguageUtil.isLetterAndNumber("dsADc 323"));
    }
}