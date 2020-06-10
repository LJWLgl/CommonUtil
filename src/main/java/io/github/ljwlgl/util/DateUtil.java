package io.github.ljwlgl.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author zqgan
 * @since 2018/9/1
 * 日期时间处理相关类
 **/

public class DateUtil {

    public static final String YYYYMMDD = "yyyy-MM-dd";
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSSSSS = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String YYYYMMDDHHMM_CHINESE = "yyyy年MM月dd日HH点mm分";
    public static final String YYYYMMDD_CHINESE = "yyyy年MM月dd日";
    public static final String MMDD_CHINESE = "MM月dd日";

    public static final long MILLISECONDS_FOR_ONE_MINUTE = 60 * 1000;
    public static final long MILLISECONDS_FOR_ONE_HOUR = 60 * MILLISECONDS_FOR_ONE_MINUTE;
    public static final long MILLISECONDS_FOR_ONE_DAY = 24 * MILLISECONDS_FOR_ONE_HOUR;

    /**
     * 获取当前日期，只包年月日
     */
    public static Date getCurrentDate() {
        Calendar c = Calendar.getInstance();
        return stringToDate(dateToShortDateString(c.getTime()));
    }

    /**
     * 比较两个时间是否是相同的天数
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (calcIntervalDays(date1, date2) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将Date转成Calendar
     */
    public static Calendar toCalendar(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    /**
     * 计算两个时间间隔的天数
     */
    public static int calcIntervalDays(String dateStr1, String dateStr2) {
        return calcIntervalDays(stringToDate(dateStr1), stringToDate(dateStr2));
    }

    /**
     * 计算两个时间的间隔小时，只会整除
     */
    public static int calcIntervalOurs(Date date1, Date date2) {
        if (date2.after(date1)) {
            return Long.valueOf((date2.getTime() - date1.getTime()) / (1000 * 60 * 60)).intValue();
        } else if (date2.before(date1)) {
            return Long.valueOf((date1.getTime() - date2.getTime()) / (1000 * 60 * 60)).intValue();
        } else {
            return 0;
        }
    }

    /**
     * 计算两个时间的间隔小时，只会整除
     */
    public static int calcIntervalMinutes(Date date1, Date date2) {
        if (date2.after(date1)) {
            return Long.valueOf((date2.getTime() - date1.getTime()) / (1000 * 60)).intValue();
        } else if (date2.before(date1)) {
            return Long.valueOf((date1.getTime() - date2.getTime()) / (1000 * 60)).intValue();
        } else {
            return 0;
        }
    }

    /**
     * 计算两个时间的间隔天数
     */
    public static int calcIntervalDays(Date date1, Date date2) {
        if (date2.after(date1)) {
            return Long.valueOf((date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24)).intValue();
        } else if (date2.before(date1)) {
            return Long.valueOf((date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24)).intValue();
        } else {
            return 0;
        }
    }

    /**
     * 返回日期对应的是星期几
     */
    public static int dayOfWeek(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int dayofWeek;
        if (ca.get(Calendar.DAY_OF_WEEK) == 1) {
            dayofWeek = 7;
        } else {
            dayofWeek = ca.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayofWeek;
    }

    /**
     * 获取今天的分钟数，如今天18:05，则返回1805
     */
    public static int getTodayMinutes() {
        Calendar ca = Calendar.getInstance();
        int hours = ca.get(Calendar.HOUR_OF_DAY);
        int minutes = ca.get(Calendar.MINUTE);
        return hours * 60 + minutes;
    }

    /**
     * 获取指定间隔天数的日期
     */
    public static Date getIntervalDaysDate(Date time, int days) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(time);
        ca.add(Calendar.DATE, days);
        return stringToDate(dateToShortDateString(ca.getTime()));
    }


    /**
     * 获取间隔的几个小时，如需要获取之前的3小时，hours传-3
     */
    public static Date getIntervalHourDate(Date time, int hours) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(time);
        ca.add(Calendar.HOUR, hours);
        System.out.println(dateToString(ca.getTime(), YYYYMMDDHHMM_CHINESE));
        return ca.getTime();
    }

    public static String dateToShortDateString(Date date) {
        return dateToString(date, "yyyy-MM-dd");
    }

    public static String dateToString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将String转成Date，默认时区东八区，TimeZone.getTimeZone("Asia/Shanghai")
     *
     * @param dateStr 含格式的时间字符串串
     * @return Date
     */
    public static Date stringToDate(String dateStr) {
        SimpleDateFormat format = null;
        if (dateStr.contains("/")) {
            if (dateStr.contains(":") && dateStr.contains(" ")) {
                format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            } else {
                format = new SimpleDateFormat("yyyy/MM/dd");
            }
        } else if (dateStr.contains("-")) {
            if (dateStr.contains(":") && dateStr.contains(" ")) {
                format = new SimpleDateFormat(YYYYMMDDHHMMSS);
            } else {
                format = new SimpleDateFormat(YYYYMMDD);
            }
        } else if (dateStr.contains("年") && dateStr.contains("月") && dateStr.contains("日")) {
            format = new SimpleDateFormat(YYYYMMDD_CHINESE);
        } else if (!dateStr.contains("年") && dateStr.contains("月") && dateStr.contains("日")) {
            format = new SimpleDateFormat(MMDD_CHINESE);
        }
        if (format == null) {
            return null;
        }
        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 全站时间展示规范
     * 1分钟内：刚刚
     * 超过1分钟并在1小时内：某分钟前 （1分钟前）
     * 超过1小时并在当日内：某小时前（1小时前）
     * 昨天：昨天 + 小时分钟（昨天 08:30）
     * 昨天之前并在当年内：某月某日 + 小时分钟（1月1日 08:30）
     * 隔年：某年某月某日 + 小时分钟（2015年1月1日 08:30）
     */
    public static String dateToVoString(Date date) {
        Date now = new Date();
        long deltaMilliSeconds = now.getTime() - date.getTime();
        Calendar dateCalendar = toCalendar(date);
        Calendar nowCalendar = toCalendar(now);

        if (nowCalendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR)) {
            if (isSameDay(date, now)) {
                if (deltaMilliSeconds < MILLISECONDS_FOR_ONE_MINUTE) {
                    return "刚刚";
                } else if (deltaMilliSeconds < MILLISECONDS_FOR_ONE_HOUR) {
                    return String.format("%d分钟前", deltaMilliSeconds / MILLISECONDS_FOR_ONE_MINUTE);
                } else if (deltaMilliSeconds < MILLISECONDS_FOR_ONE_DAY) {
                    return String.format("%d小时前", deltaMilliSeconds / MILLISECONDS_FOR_ONE_HOUR);
                }
            }

            if (isSameDay(date, getIntervalDaysDate(now, -1))) {
                return String.format("昨天 %d:%02d", dateCalendar.get(Calendar.HOUR_OF_DAY),
                        dateCalendar.get(Calendar.MINUTE));
            } else {
                return String.format("%d月%d日 %d:%02d", dateCalendar.get(Calendar.MONTH) + 1,
                        dateCalendar.get(Calendar.DAY_OF_MONTH),
                        dateCalendar.get(Calendar.HOUR_OF_DAY), dateCalendar.get(Calendar.MINUTE));
            }
        } else {
            return String.format("%d年%d月%d日 %d:%02d", dateCalendar.get(Calendar.YEAR),
                    dateCalendar.get(Calendar.MONTH) + 1,
                    dateCalendar.get(Calendar.DAY_OF_MONTH), dateCalendar.get(Calendar.HOUR_OF_DAY),
                    dateCalendar.get(Calendar.MINUTE));
        }
    }

    /**
     * 获取指定日期百分百
     *
     * @param date
     * @return
     */
    public static Float getPercentage(Date date) {
        if (date == null) date = new Date();
        Integer day = getDay(date);
        Integer month = getMonth(date);
        Integer year = getYear(date);
        Integer monthDays = getMonthLastDay(year, month);
        return (float) day / (float) monthDays;
    }

    /**
     * 获取年份
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        if (date == null) date = new Date();
        String year = new SimpleDateFormat("yyyy").format(date);
        return Integer.parseInt(year);
    }

    /**
     * 获取月份
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        String month = new SimpleDateFormat("MM").format(date);
        return Integer.parseInt(month);
    }

    /**
     * 获取日期
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        String day = new SimpleDateFormat("dd").format(date);
        return Integer.parseInt(day);
    }

    /**
     * 获取某月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 获取某月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthFirstDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month);
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 判断是否是闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeap(int year) {
        if (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0))
            return true;
        else
            return false;
    }

    /**
     * 获取传入当日0点
     *
     * @param time
     * @return
     */
    public static Date startTime(Date time) {
        if (time == null) time = new Date();
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(time);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取传入当日最后一刻
     *
     * @param time
     * @return
     */
    public static Date endTime(Date time) {
        if (time == null) time = new Date();
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(time);
        todayStart.set(Calendar.HOUR_OF_DAY, 23);
        todayStart.set(Calendar.MINUTE, 59);
        todayStart.set(Calendar.SECOND, 59);
        todayStart.set(Calendar.MILLISECOND, 999);
        return todayStart.getTime();
    }
}
