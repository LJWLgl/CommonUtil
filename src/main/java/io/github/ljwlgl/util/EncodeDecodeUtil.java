package io.github.ljwlgl.util;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * @author zqgan
 * @since 2019/4/28
 **/

public class EncodeDecodeUtil {

    private static final char[] HEX = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final String DEFAULT_CHARSET = "utf-8";


    /**
     * 对字符串进行MD5加密
     * @param str 待加密字符串
     * @return 加密后的字符串
     */
    public static String encodeWithMD5(String str) {
        return encode("MD5", str);
    }

    /**
     * 对字符串进行SHA1加密
     * @param str 待加密字符串
     * @return 加密后的字符串
     */
    public static String encodeWithSHA1(String str) {
        return encode("SHA1", str);
    }

    /**
     * 对字符串进行SHA-256加密
     * @param str 待加密字符串
     * @return 加密后的字符串
     */
    public static String encodeWithSHA256(String str) {
        return encode("SHA-256", str);
    }

    /**
     * 通过加密算法加密字符串
     */
    public static String encode(String algorithm, String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static String encodeBase64(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        try {
            return Base64.getEncoder().encodeToString(str.getBytes(DEFAULT_CHARSET));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decodeBase64(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        try {
            byte[] bytes = Base64.getDecoder().decode(str);
            return new String(bytes, DEFAULT_CHARSET);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decodeUrl(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        try {
            return java.net.URLDecoder.decode(str, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String encodeUrl(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        try {
            return java.net.URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

}
