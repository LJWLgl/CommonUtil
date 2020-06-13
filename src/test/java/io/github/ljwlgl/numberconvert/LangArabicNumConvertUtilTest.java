package io.github.ljwlgl.numberconvert;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LangArabicNumConvertUtilTest {

    @Test
    public void testLang2ArabicNumber() {
        assertEquals("666 beijing abnormalities mottoes litun 200 and sixties-five hotel 7894", LangArabicNumConvertUtil.lang2ArabicNumber("six six six Beijing abnormalities mottoes Litun two hundred and sixties-five Hotel seven thousand eight hundred and ninety-four", "en"));
        assertEquals("北京3里墩5星小区第64栋602室", LangArabicNumConvertUtil.lang2ArabicNumber("北京三里墩五星小区第陆拾肆栋六零二室", "zh"));
    }

    @Test
    public void testArabic2LangNumber() {
        assertEquals("result", LangArabicNumConvertUtil.arabic2LangNumber("word", "majorLocale"));
    }
}
