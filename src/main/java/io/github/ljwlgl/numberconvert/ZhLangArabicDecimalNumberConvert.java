package io.github.ljwlgl.numberconvert;

import com.google.common.collect.Lists;
import io.github.ljwlgl.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author zq_gan
 * @since 2020/6/10
 * 本工具类部门代码参考：https://blog.csdn.net/jjfly999/article/details/51052492
 **/

public class ZhLangArabicDecimalNumberConvert extends LangArabicNumberConvert {

    private ArabicNumberToChineseNum arabicNumberToChineseNum = new ArabicNumberToChineseNum();
    private ArabicNumberToNoDecimalChineseNum arabicNumberToNoDecimalChineseNum = new ArabicNumberToNoDecimalChineseNum();
    private ChineseChangeToNumber chineseChangeToNumber = new ChineseChangeToNumber();
    private ChineseConvertNoDecimalNumber chineseConvertNoDecimalNumber = new ChineseConvertNoDecimalNumber();

    @Override
    String toArabicNumber(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
        Set<String> numbers = separateNumbers(word, this::isAllChineseNumber);
        if (CollectionUtils.isEmpty(numbers)) {
            return word;
        }
        Map<String, String> replaceMap = new HashMap<>();
        for (String number : numbers) {
            if (isDecimalNum(number)) {
                replaceMap.put(number, String.valueOf(chineseChangeToNumber.chineseToNumber(number)));
            } else {
                replaceMap.put(number, chineseConvertNoDecimalNumber.chineseToNumber(number));
            }
        }
        return StringUtil.replaceString(word, replaceMap);
    }

    @Override
    String toLangNumber(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
        Set<String> numbers = separateNumbers(word, this::isAllArabicNumber);
        if (CollectionUtils.isEmpty(numbers)) {
            return word;
        }
        Map<String, String> replaceMap = new HashMap<>();
        for (String number : numbers) {
            replaceMap.put(number, arabicNumberToChineseNum.numberToChinese(Integer.parseInt(number)));
        }
        return StringUtil.replaceString(word, replaceMap);
    }

    @Override
    String toNoDecimalLangNumber(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
        return arabicNumberToNoDecimalChineseNum.numberToNoDecimalChinese(word);
    }

    @Override
    public boolean isDecimalNum(String str) {
        if ((str.length() == 2 || str.length() == 1)   && (str.startsWith("十") || str.startsWith("拾"))) {
            return true;
        }
        if (DataTool.chnUnitNameList.stream().noneMatch(str::contains)) {
            return false;
        }
        int firstWeightIndex = -1;
        for (int i = 0; i < str.length(); i++) {
            if (DataTool.chnUnitNameList.contains(String.valueOf(str.charAt(i)))) {
                firstWeightIndex = i;
                break;
            }
        }
        if (firstWeightIndex >= 1
                && (ArrayUtils.indexOf(DataTool.bigChnNumChinese, str.charAt(firstWeightIndex - 1)) != -1
                    ||  ArrayUtils.indexOf(DataTool.chnNumChinese, str.charAt(firstWeightIndex - 1)) != -1 )) {
            return true;
        }
        return false;
    }

    /**
     * 从文本中分离出对应字符
     * @param str 输入文本
     * @return 数字文本集合
     */
    public Set<String> separateNumbers(String str, Apply<Boolean> apply) {
        Set<String> numbers = new HashSet<>();
        char[] chrArr = str.toCharArray();
        // 正向最大匹配
        for (int i = 0; i < chrArr.length; i++) {
            String maxNumber = "";
            int maxLength = 0;
            for (int j = i + 1; j <= chrArr.length; j++) {
                if (apply.apply(i, j, chrArr)) {
                    if (j - i > maxLength) {
                        maxNumber = StringUtil.subArr2String(i, j, chrArr);
                        maxLength = j -i;
                    }
                }
            }
            if (maxLength > 0) {
                numbers.add(maxNumber);
                i += maxLength;
            }
        }
        return numbers;
    }

    private boolean isAllChineseNumber(int i, int j, char[] arr) {
        for (int k = i; k < j; k++) {
            if (! DataTool.valueMap.containsKey(arr[k]) && ! DataTool.chnUnitNameList.contains(String.valueOf(arr[k]))) {
                return false;
            }
        }
        return true;
    }

    private Boolean isAllArabicNumber(int i, int j, char[] arr) {
        for (int k = i; k < j; k++) {
            if (! DataTool.numberNameMap.containsKey(arr[k])) {
                return false;
            }
        }
        return true;
    }

    private interface Apply<T> {
        T apply(int i, int j, char[] arr);
    }

    private static class ArabicNumberToNoDecimalChineseNum {
        public String numberToNoDecimalChinese(String str) {
            StringBuilder builder = new StringBuilder();
            char[] charArr = str.toCharArray();
            for (char chr : charArr) {
                if (DataTool.numberNameMap.get(chr) != null) {
                    builder.append(DataTool.numberNameMap.get(chr));
                } else {
                    builder.append(chr);
                }
            }
            return builder.toString();
        }
    }

    private static class ArabicNumberToChineseNum {

        public String numberToChinese(int num) {//转化一个阿拉伯数字为中文字符串
            if (num == 0) {
                return "零";
            }
            int unitPos = 0;//节权位标识
            String All = new String();
            String chineseNum = new String();//中文数字字符串
            boolean needZero = false;//下一小结是否需要补零
            String strIns = new String();
            while (num > 0) {
                int section = num % 10000;//取最后面的那一个小节
                if (needZero) {//判断上一小节千位是否为零，为零就要加上零
                    All = DataTool.chnNumChar[0] + All;
                }
                chineseNum = sectionTOChinese(section, chineseNum);//处理当前小节的数字,然后用chineseNum记录当前小节数字
                if (section != 0) {//此处用if else 选择语句来执行加节权位
                    strIns = DataTool.chnUnitSection[unitPos];//当小节不为0，就加上节权位
                    chineseNum = chineseNum + strIns;
                } else {
                    strIns = DataTool.chnUnitSection[0];//否则不用加
                    chineseNum = strIns + chineseNum;
                }
                All = chineseNum + All;
                chineseNum = "";
                needZero = (section < 1000) && (section > 0);
                num = num / 10000;
                unitPos++;
            }
            if (All.startsWith("一十")) {
                All = All.substring(1);
            }
            return All.trim();
        }

        private String sectionTOChinese(int section, String chineseNum) {
            String setionChinese = new String();//小节部分用独立函数操作
            int unitPos = 0;//小节内部的权值计数器
            boolean zero = true;//小节内部的制零判断，每个小节内只能出现一个零
            while (section > 0) {
                int v = section % 10;//取当前最末位的值
                if (v == 0) {
                    if (!zero) {
                        zero = true;//需要补零的操作，确保对连续多个零只是输出一个
                        chineseNum = DataTool.chnNumChar[0] + chineseNum;
                    }
                } else {
                    zero = false;//有非零的数字，就把制零开关打开
                    setionChinese = DataTool.chnNumChar[v];//对应中文数字位
                    setionChinese = setionChinese + DataTool.chnUnitChar[unitPos];//对应中文权位
                    chineseNum = setionChinese + chineseNum;
                }
                unitPos++;
                section = section / 10;
            }

            return chineseNum;
        }
    }


    public class ChineseChangeToNumber {

        public int chineseToNumber(String str) {
            if ((str.length() == 2 || str.length() == 1) && (str.startsWith("十") || str.startsWith("拾"))) {
                str = "一" + str;
            }
            String str1 = new String();
            String str2 = new String();
            String str3 = new String();
            int k = 0;
            boolean dealflag = true;
            for (int i = 0; i < str.length(); i++) {//先把字符串中的“零”除去
                if ('零' == (str.charAt(i))) {
                    str = str.substring(0, i) + str.substring(i + 1);
                }
            }
            String chineseNum = str;
            for (int i = 0; i < chineseNum.length(); i++) {
                if (chineseNum.charAt(i) == '亿' || chineseNum.charAt(i) == '億') {
                    str1 = chineseNum.substring(0, i);//截取亿前面的数字，逐个对照表格，然后转换
                    k = i + 1;
                    dealflag = false;//已经处理
                }
                if (chineseNum.charAt(i) == '万' || chineseNum.charAt(i) == '萬') {
                    str2 = chineseNum.substring(k, i);
                    str3 = str.substring(i + 1);
                    dealflag = false;//已经处理
                }
            }
            if (dealflag) {//如果没有处理
                str3 = chineseNum;
            }
            int result = sectionChinese(str1) * 100000000 +
                    sectionChinese(str2) * 10000 + sectionChinese(str3);
            return result;
        }


        int sectionChinese(String str) {
            int value = 0;
            int sectionNum = 0;
            for (int i = 0; i < str.length(); i++) {
                int v = DataTool.valueMap.get(str.charAt(i));
                if (v == 10 || v == 100 || v == 1000) {//如果数值是权位则相乘
                    sectionNum = v * sectionNum;
                    value = value + sectionNum;
                } else if (i == str.length() - 1) {
                    value = value + v;
                } else {
                    sectionNum = v;
                }
            }
            return value;
        }
    }

    public class ChineseConvertNoDecimalNumber {

        public String chineseToNumber(String str) {
            StringBuilder builder = new StringBuilder();
            char[] charArr = str.toCharArray();
            for (int i = 0; i < charArr.length; i++) {
                if (DataTool.singleValueMap.get(charArr[i]) != null) {
                    builder.append(DataTool.singleValueMap.get(charArr[i]));
                } else {
                    builder.append(charArr[i]);
                }
            }
            return builder.toString();
        }
    }


    private static class DataTool {
        //数字位
        public static String[] chnNumChar = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        public static char[] chnNumChinese = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};

        public static char[] bigChnNumChinese = {'壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};

        //节权位
        public static String[] chnUnitSection = {"", "万", "亿", "万亿"};
        //权位
        public static String[] chnUnitChar = {"", "十", "百", "千"};
        public static Map<Character, Integer> valueMap = new HashMap<>();
        public static Map<Character, Integer> singleValueMap = new HashMap<>();

        public static Map<Character, Character> numberNameMap = new HashMap<>();

        public static List<String> chnUnitNameList = Lists.newArrayList( "億", "亿", "萬", "万", "仟", "千", "佰", "百", "十", "拾");

         static {
             for (int i = 0; i < chnNumChinese.length; i++) {
                 valueMap.put(chnNumChinese[i], i);
                 singleValueMap.put(chnNumChinese[i], i);
                 numberNameMap.put((char)(i + 48), chnNumChinese[i]);
             }
             for (int i = 0; i < bigChnNumChinese.length; i++) {
                 valueMap.put(bigChnNumChinese[i], i + 1);
                 singleValueMap.put(bigChnNumChinese[i], i + 1);
             }
             // 繁体
             valueMap.put((char)36144, 2);
             valueMap.put('陸', 6);
             singleValueMap.put('貳', 2);
             singleValueMap.put('陸', 6);

             valueMap.put('十', 10);
             valueMap.put('拾', 10);
             valueMap.put('百', 100);
             valueMap.put('佰', 100);
             valueMap.put('千', 1000);
             valueMap.put('仟', 1000);
         }

    }

}
