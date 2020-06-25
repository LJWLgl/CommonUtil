package io.github.ljwlgl.numberconvert;

/**
 * @author zq_gan
 * @since 2020/6/10
 **/

public abstract class LangArabicNumberConvert {

    abstract boolean isDecimalNum(String word);

    abstract String toArabicNumber(String word);

    abstract String toLangNumber(String word);

    abstract String toNoDecimalLangNumber(String word);

}
