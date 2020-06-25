package io.github.ljwlgl.numberconvert;

import io.github.ljwlgl.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zq_gan
 * @since 2020/6/10
 * 本工具类部分代码参考：https://blog.csdn.net/u012631267/article/details/19823253
 **/

public class EnLangArabicDecimalNumberConvert extends LangArabicNumberConvert {


    private NumberWordConvert numberWordConvert = new NumberWordConvert();

    @Override
    boolean isDecimalNum(String word) {
        return Arrays.stream(StringUtils.split(word, " -")).anyMatch(DataTool.weightNames::contains);
    }

    @Override
    String toArabicNumber(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
        String newWord = beforeProcess(word);
        Set<String> numbers = separateNumbers(newWord);
        if (CollectionUtils.isEmpty(numbers)) {
            return newWord;
        }
        Map<String, String> afterNumMap = numbers.stream().collect(Collectors.toMap(i -> i, this::numberAfterProcess));
        Map<String, String> replaceMap = new HashMap<>();
        for (Map.Entry<String, String> entry : afterNumMap.entrySet()) {
            if (isDecimalNum(entry.getValue())) {
                replaceMap.put(entry.getKey(), String.valueOf(numberWordConvert.parse(entry.getValue())));
            } else {
                replaceMap.put(entry.getKey(), String.valueOf(numberWordConvert.noDecimalParse(entry.getValue())));
            }
        }
        return StringUtil.replaceString(newWord, replaceMap);
    }

    @Override
    String toLangNumber(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
        Set<String> numbers = separateArabicNumbers(word);
        if (CollectionUtils.isEmpty(numbers)) {
            return word;
        }
        Map<String, String> replaceMap = new HashMap<>();
        for (String number : numbers) {
            replaceMap.put(number, String.valueOf(numberWordConvert.format(Integer.parseInt(number))));
        }
        return StringUtil.replaceString(word, replaceMap);
    }

    @Override
    String toNoDecimalLangNumber(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
        return numberWordConvert.noDecimalFormat(word);
    }

    /**
     * 从文本中分离数字字符
     * @param str 输入文本
     * @return 数字文本集合
     */
    public Set<String> separateNumbers(String str) {
        Set<String> numbers = new HashSet<>();
        String[] strArr = StringUtils.split(str, " ");
        for (int i = 0; i < strArr.length; i++) {
            int maxLength = 0;
            String tempStr = "";
            for (int j = i + 1; j <= strArr.length; j++) {
                if (isAllEngListNumber(i, j, strArr) && j - i > maxLength) {
                    maxLength = j - i;
                    tempStr = StringUtil.subArr2String(i, j, strArr, " ");
                }
            }
            if (maxLength > 0) {
                numbers.add(tempStr);
                i += maxLength;
            }
        }
        return numbers;
    }

    /**
     * 从文本中分离出对应字符
     * @param str 输入文本
     * @return 数字文本集合
     */
    public Set<String> separateArabicNumbers(String str) {
        Set<String> numbers = new HashSet<>();
        char[] chrArr = str.toCharArray();
        // 正向最大匹配
        for (int i = 0; i < chrArr.length; i++) {
            String maxNumber = "";
            int maxLength = 0;
            for (int j = i + 1; j <= chrArr.length; j++) {
                if (isAllArabicNumber(i, j, chrArr)) {
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

    public boolean isAllArabicNumber(int i, int j, char[] arr) {
        for (int k = i; k < j; k++) {
            if (! DataTool.arabicNumberMap.containsKey(arr[k])) {
                return false;
            }
        }
        return true;
    }

    public boolean isAllEngListNumber(int i, int j, String[] arr) {
        int andIndex = -1;
        for (int k = i; k < j; k++) {
            if (StringUtils.isBlank(arr[k])) {
                return false;
            }
            if (arr[k].contains("-")) {
                String[] tempArr = StringUtils.splitByWholeSeparator(arr[k], "-");
                if (tempArr.length == 2 && DataTool.numberNames.contains(tempArr[0]) && DataTool.numberNames.contains(tempArr[1])) {
                    continue;
                }
            }
            if (arr[k].equals("and")) {
                andIndex = k;
                continue;
            }
            if (! DataTool.numberNames.contains(arr[k])) {
                return false;
            }
        }
        // 处理and在末尾的case
        if (andIndex > 0 && andIndex == j - 1) {
            return false;
        }
        return true;
    }

    private String beforeProcess(String str) {
        return str.toLowerCase().trim();
    }

    private String numberAfterProcess(String str) {
        String newStr = str;
        if (newStr.contains("and")) {
            newStr = Arrays.stream(str.split(" ")).filter(i -> ! i.equals("and")).collect(Collectors.joining(" "));
        }
        if (newStr.contains("-")) {
            newStr = newStr.replace("-", " ");
        }
        return newStr;
    }

    private static class NumberWordConvert {
        public static final String ZERO = "zero";
        public static final String NEGATIVE = "negative";
        public static final String SPACE = " ";
        public static final String MILLION = "million";
        public static final String THOUSAND = "thousand";
        public static final String HUNDRED = "hundred";
        public static final String[] INDNUM = {"zero", "one", "two", "three", "four", "five", "six",
                "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen",
                "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
        public static final String[] DECNUM = {"twenty", "thirty", "forty", "fifty", "sixty",
                "seventy", "eighty", "ninety"};

        //数字转换英文
        public String format(int i) {
            StringBuilder sb = new StringBuilder();
            if (i == 0) {
                return ZERO;
            }
            if (i < 0) {
                sb.append(NEGATIVE).append(SPACE);
                i *= -1;
            }
            if (i >= 1000000) {
                sb.append(numFormat(i / 1000000)).append(SPACE).append(MILLION).append(SPACE);
                i %= 1000000;
            }
            if (i >= 1000) {
                sb.append(numFormat(i / 1000)).append(SPACE).append(THOUSAND).append(SPACE);
                i %= 1000;
            }
            if (i < 1000) {
                sb.append(numFormat(i));
            }
            return sb.toString();
        }

        // 3位数转英文
        public String numFormat(int i) {
            StringBuilder sb = new StringBuilder();
            if (i >= 100) {
                sb.append(INDNUM[i / 100]).append(SPACE).append(HUNDRED).append(SPACE);
            }
            i %= 100;
            if (i != 0) {
                if (i >= 20) {
                    sb.append(DECNUM[i / 10 - 2]).append(SPACE);
                    if (i % 10 != 0) {
                        sb.append(INDNUM[i % 10]);
                    }
                } else {
                    sb.append(INDNUM[i]);
                }
            }
            return sb.toString();
        }

        public String noDecimalFormat(String str) {
            StringBuilder builder = new StringBuilder();
            char[] charArr = str.toCharArray();
            for (int i = 0; i < charArr.length; i++) {
                if (DataTool.arabicNumberMap.containsKey(charArr[i])) {
                    builder.append(" ").append(DataTool.arabicNumberMap.get(charArr[i])).append(" ");
                } else {
                    builder.append(charArr[i]);
                }
            }
            return builder.toString().replaceAll("[ ]+", " ").trim();
        }

        //英文转数字
        public int parse(String str) {
            int i = 0;
            int b = 0;
            int c = 0;
            String[] k = str.split(" ");
            for (String string : k) {
                if ("hundred".equals(string)) {
                    i *= DataTool.enNameValMap.get("hundred");
                } else if ("thousand".equals(string)) {
                    b = i;
                    b *= DataTool.enNameValMap.get("thousand");
                    i = 0;
                } else if ("million".equals(string)) {
                    c = i;
                    c *= DataTool.enNameValMap.get("million");
                    i = 0;
                } else if ("negative".equals(string)) {
                    i = 0;
                } else {
                    i += DataTool.enNameValMap.get(string);
                }
            }
            i += c + b;
            for (String string2 : k) {
                if ("negative".equals(string2)) {
                    i = -i;
                }
            }
            return i;
        }

        public String noDecimalParse(String str) {
            return Arrays.stream(str.split(" "))
                    .map(String::trim)
                    .map(i -> DataTool.singleNames.contains(i) ? String.valueOf( DataTool.enNameValMap.get(i)) : i)
                    .collect(Collectors.joining(""));
        }
    }
    
    private static class DataTool {

        public static Map<String, Integer> enNameValMap = new HashMap<>();
        public static Set<String> numberNames = new HashSet<>();
        public static Set<String> weightNames = new HashSet<>();
        public static Set<String> singleNames = new HashSet<>();
        public static Map<Character, String> arabicNumberMap = new HashMap<>();

        static {
            enNameValMap.put("zero", 0);
            enNameValMap.put("one", 1);
            enNameValMap.put("two", 2);
            enNameValMap.put("three", 3);
            enNameValMap.put("four", 4);
            enNameValMap.put("five", 5);
            enNameValMap.put("six", 6);
            enNameValMap.put("seven", 7);
            enNameValMap.put("eight", 8);
            enNameValMap.put("nine", 9);
            enNameValMap.put("ten", 10);
            enNameValMap.put("eleven", 11);
            enNameValMap.put("twelve", 12);
            enNameValMap.put("thirteen", 13);
            enNameValMap.put("fourteen", 14);
            enNameValMap.put("fifteen", 15);
            enNameValMap.put("sixteen", 16);
            enNameValMap.put("seventeen", 17);
            enNameValMap.put("eighteen", 18);
            enNameValMap.put("nineteen", 19);
            enNameValMap.put("twenty", 20);
            enNameValMap.put("thirty", 30);
            enNameValMap.put("forty", 40);
            enNameValMap.put("fifty", 50);
            enNameValMap.put("sixty", 60);
            enNameValMap.put("seventy", 70);
            enNameValMap.put("eighty", 80);
            enNameValMap.put("ninety", 90);
            enNameValMap.put("hundred", 100);
            enNameValMap.put("thousand", 1000);
            enNameValMap.put("million", 1000000);

            weightNames.add("ten");
            weightNames.add("eleven");
            weightNames.add("twelve");
            weightNames.add("thirteen");
            weightNames.add("fourteen");
            weightNames.add("fifteen");
            weightNames.add("sixteen");
            weightNames.add("seventeen");
            weightNames.add("eighteen");
            weightNames.add("nineteen");
            weightNames.add("twenty");
            weightNames.add("thirty");
            weightNames.add("forty");
            weightNames.add("fifty");
            weightNames.add("sixty");
            weightNames.add("seventy");
            weightNames.add("eighty");
            weightNames.add("ninety");
            weightNames.add("hundred");
            weightNames.add("thousand");
            weightNames.add("million");

            singleNames.add("zero");
            singleNames.add("one");
            singleNames.add("two");
            singleNames.add("three");
            singleNames.add("four");
            singleNames.add("five");
            singleNames.add("six");
            singleNames.add("seven");
            singleNames.add("eight");
            singleNames.add("nine");

            arabicNumberMap.put('0', "zero");
            arabicNumberMap.put('1', "one");
            arabicNumberMap.put('2', "two");
            arabicNumberMap.put('3', "three");
            arabicNumberMap.put('4', "four");
            arabicNumberMap.put('5', "five");
            arabicNumberMap.put('6', "six");
            arabicNumberMap.put('7', "seven");
            arabicNumberMap.put('8', "eight");
            arabicNumberMap.put('9', "nine");

            numberNames.addAll(enNameValMap.keySet());
        }


    }

}
