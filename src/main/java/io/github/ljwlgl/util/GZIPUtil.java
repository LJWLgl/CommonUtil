package io.github.ljwlgl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author zqgan
 * @since 2019/4/8
 **/

public class GZIPUtil {

    private static Logger logger = LoggerFactory.getLogger(GZIPUtil.class);
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";

    /**
     * 字符串压缩为GZIP字节数组
     *
     * @param str
     * @return
     */
    public static byte[] compress(String str) {
        return compress(str, GZIP_ENCODE_UTF_8);
    }

    /**
     * 字符串压缩为GZIP字节数组
     *
     * @param str
     * @param encoding
     * @return
     */
    public static byte[] compress(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        byte[] bytes = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            GZIPOutputStream gzip;
            try {
                gzip = new GZIPOutputStream(out);
                gzip.write(str.getBytes(encoding));
                gzip.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            bytes = out.toByteArray();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        return bytes;
    }

    public static byte[] compress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        byte[] byteArr = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            GZIPOutputStream gzip = null;
            try {
                gzip = new GZIPOutputStream(out);
                gzip.write(bytes);

            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }finally {
            	if(gzip!=null){
            		 gzip.close();
            	}
    		}
            byteArr = out.toByteArray();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return byteArr;
    }

    /**
     * GZIP解压缩
     *
     * @param bytes
     * @return
     */
    public static byte[] uncompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        byte[] byteArr = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            GZIPInputStream ungzip = new GZIPInputStream(in);
            try {
                byte[] buffer = new byte[256];
                int n;
                while ((n = ungzip.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
                in.close();

            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }finally {
            	  ungzip.close();
			}
            byteArr = out.toByteArray();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return byteArr;
    }

    /**
     * @param bytes
     * @return
     */
    public static String uncompress2Str(byte[] bytes) {
        return uncompress2Str(bytes, GZIP_ENCODE_UTF_8);
    }

    /**
     * @param bytes
     * @param encoding
     * @return
     */
    public static String uncompress2Str(byte[] bytes, String encoding) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        String str = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            try {
                GZIPInputStream ungzip = new GZIPInputStream(in);
                byte[] buffer = new byte[256];
                int n;
                while ((n = ungzip.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
                in.close();
                ungzip.close();
                str = out.toString(encoding);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return str;
    }
}
