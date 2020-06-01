package io.github.ljwlgl.fileutil;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.google.common.base.Charsets;
import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zq_gan
 * @since 2020/6/1
 * @version 2.1.0
 * 本工具类部分代码参考：https://blog.csdn.net/expect521/article/details/104054891
 **/

public class CsvUtil {

    /**
     * 读取csv文件内容
     * @param filePath csv文件的路径
     * @return 返回csv文件中的数据
     * @throws Exception exception
     */
    public static List<String[]> read(String filePath) throws Exception {
        return read(new FileInputStream(filePath), Charsets.UTF_8, false, null);
    }


    /**
     * 读取csv文件内容
     * @param filePath csv文件的路径
     * @param columns 指定读取csv文件的哪几列，如果为null，则读取全部列
     * @return 返回csv文件中的数据
     * @throws Exception exception
     */
    public static List<String[]> read(String filePath, int[] columns) throws Exception {
        return read(new FileInputStream(filePath), Charsets.UTF_8, false, columns);
    }

    /**
     * 读取csv文件内容
     * @param filePath csv文件的路径
     * @param needHeader 是否需要列标题（假设第一行是有标题的，如果没有该参数请传true）
     * @return 返回csv文件中的数据
     * @throws Exception exception
     */
    public static List<String[]> read(String filePath, boolean needHeader) throws Exception {
        return read(new FileInputStream(filePath), Charsets.UTF_8, needHeader, null);
    }

    /**
     * 读取csv文件内容
     * @param filePath csv文件的路径
     * @param needHeader 是否需要列标题（假设第一行是有标题的，如果没有该参数请传true）
     * @param columns 指定读取csv文件的哪几列，如果为null，则读取全部列
     * @return 返回csv文件中的数据
     * @throws Exception exception
     */
    public static List<String[]> read(String filePath, boolean needHeader, int[] columns) throws Exception {
        return read(new FileInputStream(filePath), Charsets.UTF_8, needHeader, columns);
    }

    /**
     * 读取csv文件内容
     * @param filePath csv文件的路径
     * @param code csv文件的编码, 如utf8, gbk等
     * @param needHeader 是否需要列标题（假设第一行是有标题的，如果没有该参数请传true）
     * @return 返回csv文件中的数据
     * @throws Exception exception
     */
    public static List<String[]> read(String filePath, String code, boolean needHeader) throws Exception {
        return read(new FileInputStream(filePath), Charset.forName(code), needHeader, null);
    }

    /**
     * 读取csv文件内容
     * @param inputStream stream
     * @return 返回csv文件中的数据
     * @throws Exception exception
     */
    public static List<String[]> read(InputStream inputStream) throws Exception {
        return read(inputStream, Charsets.UTF_8, false, null);
    }

    /**
     * 读取csv文件内容
     * @param inputStream stream
     * @param needHeader 是否需要列标题（假设第一行是有标题的，如果没有该参数请传true）
     * @return 返回csv文件中的数据
     * @throws Exception exception
     */
    public static List<String[]> read(InputStream inputStream, boolean needHeader) throws Exception {
        return read(inputStream, Charsets.UTF_8, needHeader, null);
    }

    /**
     * 读取csv文件内容
     * @param inputStream stream
     * @param columns 指定读取csv文件的哪几列，如果为null，则读取全部列
     * @return 返回csv文件中的数据
     * @throws Exception exception
     */
    public static List<String[]> read(InputStream inputStream, int[] columns) throws Exception {
        return read(inputStream, Charsets.UTF_8, false, columns);
    }

    /**
     * 读取csv文件内容
     * @param inputStream stream
     * @param columns 指定读取csv文件的哪几列，如果为null，则读取全部列
     * @param needHeader 是否需要列标题（假设第一行是有标题的，如果没有该参数请传true）
     * @return 返回csv文件中的数据
     * @throws Exception exception
     */
    public static List<String[]> read(InputStream inputStream, boolean needHeader, int[] columns) throws Exception {
        return read(inputStream, Charsets.UTF_8, needHeader, columns);
    }

    /**
     * 读取csv文件内容
     * @param inputStream stream
     * @param charset csv文件的编码
     * @param needHeader 是否需要列标题（假设第一行是有标题的，如果没有该参数请传true）
     * @param columns 指定读取csv文件的哪几列，如果为null，则读取全部列
     * @return 返回csv文件中的数据
     * @throws Exception exception
     */
    public static List<String[]> read(InputStream inputStream, Charset charset, boolean needHeader, int[] columns) throws Exception {
        List<String[]> csvList = new ArrayList<>();
        CsvReader reader = new CsvReader(inputStream, ',', charset);
        if (! needHeader) {
            reader.readHeaders();
        }
        while(reader.readRecord()) {
            if (ArrayUtils.isEmpty(columns)) {
                csvList.add(reader.getValues());
            } else {
                String[] lineRes = new String[columns.length];
                if (! checkColumnSize(columns, reader.getColumnCount())) {
                    throw new IllegalArgumentException("columns size must less file's columns size");
                }
                for (int i = 0; i < columns.length; i++) {
                    lineRes[i] = reader.getValues()[columns[i]];
                }
                csvList.add(lineRes);
            }
        }
        reader.close();
        return csvList;
    }

    /**
     * 数据写入csv文件
     * @param list UTF-8编码写入csv文件的内容
     * @param filePath 写入的csv文件的指定路径
     * @param append 是否以追加的方式写入（追加：append为true, 覆盖：append为false）
     * @throws Exception exception
     */
    public static void write(List<String[]> list, String filePath, boolean append) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath), append));
        CsvWriter wr = new CsvWriter(writer, ',');
        for (String[] strings : list) {
            wr.writeRecord(strings);
        }
        wr.close();
    }

    private static boolean checkColumnSize(int[] columns, int size) {
        return Arrays.stream(columns).allMatch(i -> i < size);
    }

}
