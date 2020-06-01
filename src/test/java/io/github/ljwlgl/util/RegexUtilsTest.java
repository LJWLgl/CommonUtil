package io.github.ljwlgl.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class RegexUtilsTest {

    @Test
    public void testIsNumeric() {
        assertTrue(RegexUtils.isNumeric("input"));
    }

    @Test
    public void testIsPositiveFloat() {
        assertTrue(RegexUtils.isPositiveFloat("input"));
    }

    @Test
    public void testIsLetter() {
        assertTrue(RegexUtils.isLetter("input"));
    }

    @Test
    public void testIsLetterAndNum() {
        assertTrue(RegexUtils.isLetterAndNum("input"));
    }

    @Test
    public void testIsMobileSimple() {
        assertTrue(RegexUtils.isMobileSimple("input"));
    }

    @Test
    public void testIsMobileExact() {
        assertTrue(RegexUtils.isMobileExact("input"));
    }

    @Test
    public void testIsTel() {
        assertTrue(RegexUtils.isTel("input"));
    }

    @Test
    public void testIsIDCard15() {
        assertTrue(RegexUtils.isIDCard15("input"));
    }

    @Test
    public void testIsIDCard18() {
        assertTrue(RegexUtils.isIDCard18("input"));
    }

    @Test
    public void testIsEmail() {
        assertTrue(RegexUtils.isEmail("input"));
    }

    @Test
    public void testIsURL() {
        assertTrue(RegexUtils.isURL("input"));
    }

    @Test
    public void testIsZh() {
        assertTrue(RegexUtils.isZh("input"));
    }

    @Test
    public void testIsUsername() {
        assertTrue(RegexUtils.isUsername("input"));
    }

    @Test
    public void testIsDate() {
        assertTrue(RegexUtils.isDate("input"));
    }

    @Test
    public void testIsIP() {
        assertTrue(RegexUtils.isIP("input"));
    }

    @Test
    public void testIsMatch() {
        assertTrue(RegexUtils.isMatch("regex", "input"));
    }

    @Test
    public void testGetMatches() {
        // Setup
        final List<String> expectedResult = Arrays.asList("value");

        // Run the test
        final List<String> result = RegexUtils.getMatches("regex", "input");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetSplits() {
        assertArrayEquals(new String[]{"value"}, RegexUtils.getSplits("input", "regex"));
    }

    @Test
    public void testGetReplaceFirst() {
        assertEquals("result", RegexUtils.getReplaceFirst("input", "regex", "replacement"));
    }

    @Test
    public void testGetReplaceAll() {
        assertEquals("result", RegexUtils.getReplaceAll("input", "regex", "replacement"));
    }
}
