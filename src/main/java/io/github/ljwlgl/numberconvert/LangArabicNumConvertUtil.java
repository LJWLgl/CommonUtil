package io.github.ljwlgl.numberconvert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zq_gan
 * @since 2020/4/21
 * 语言数字与阿拉伯数字转换工具类
 **/

public class LangArabicNumConvertUtil {

    private static Map<String, LangArabicNumberConvert> convertMap = initCovertMap();

    public static String lang2ArabicNumber(String word, String majorLocale) {
        if (convertMap.get(majorLocale.toLowerCase()) == null) {
            return word;
        }
        return convertMap.get(majorLocale).toArabicNumber(word);
    }

    public static String arabic2LangNumber(String word, String majorLocale) {
        if (convertMap.get(majorLocale) == null) {
            return word;
        }
        return convertMap.get(majorLocale).toLangNumber(word);
    }

    public static String arabic2NoDecimalLangNumber(String word, String majorLocale) {
        if (convertMap.get(majorLocale) == null) {
            return word;
        }
        return convertMap.get(majorLocale).toNoDecimalLangNumber(word);
    }

    private static Map<String, LangArabicNumberConvert> initCovertMap() {
        convertMap = new HashMap<>();
        convertMap.put("en", new EnLangArabicDecimalNumberConvert());
        convertMap.put("zh", new ZhLangArabicDecimalNumberConvert());
        return convertMap;
    }

}
