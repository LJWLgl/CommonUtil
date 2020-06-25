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
        assertEquals("北京三里墩五星小区第六十四栋六百零二室", LangArabicNumConvertUtil.arabic2LangNumber("北京3里墩5星小区第64栋602室", "zh"));
        assertEquals("six hundred sixty six beijing litun two hundred  and sixties-five hotel seven thousand eight hundred ninety four", LangArabicNumConvertUtil.arabic2LangNumber("666 beijing litun 200 and sixties-five hotel 7894", "en"));
    }

    @Test
    public void testArabic2NoDecimalLangNumber() {
        assertEquals("北京三里墩五星小区第六四栋六零二室", LangArabicNumConvertUtil.arabic2NoDecimalLangNumber("北京3里墩5星小区第64栋602室", "zh"));
        assertEquals("six six six beijing abnormalities mottoes litun two zero zero and sixties-five hotel seven eight nine four", LangArabicNumConvertUtil.arabic2NoDecimalLangNumber("666 beijing abnormalities mottoes litun 200 and sixties-five hotel 7894", "en"));
    }
}
