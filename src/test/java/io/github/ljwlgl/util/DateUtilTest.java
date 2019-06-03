package io.github.ljwlgl.util;

import io.github.ljwlgl.util.DateUtil;
import org.junit.Test;

import java.util.Date;

public class DateUtilTest {

    @Test
    public void testStringToDate() {
//        Date date = DateUtil.stringToDate("2018-09-02 08:00:00");
//        Date date = DateUtil.stringToDate("2018/09/02 08:00:00");
        Date date = DateUtil.stringToDate("2018/09/02");
        System.out.println(date);
    }

    @Test
    public void test03() {
        Date oldTime = DateUtil.getIntervalHour(new Date(), -3);
        System.out.println(oldTime);
    }

    @Test
    public void testDateToVoString() {
        Date date = DateUtil.stringToDate("2018年5月2日");
        System.out.println(DateUtil.dateToVoString(date));

        date.setTime(new Date().getTime() - 12 * 60 * 60 * 1000);
        System.out.println(DateUtil.dateToVoString(date));

        date.setTime(new Date().getTime() - 6 * 1000);
        System.out.println(DateUtil.dateToVoString(date));
    }

    @Test
    public void test09() {
        Date date1 = new Date();
        date1.setTime(new Date().getTime() - 1);
        Date date2 = new Date();
        date2.setTime(new Date().getTime() - 12 * 60 * 60 * 1000);
        System.out.println(DateUtil.calcIntervalOurs(date1, date2));
    }

}