package io.github.ljwlgl.date;

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
    public void testDateToVoString() {
        Date date = DateUtil.stringToDate("2018-05-02 15:00:00");
        System.out.println(DateUtil.dateToVoString(date));

        date.setTime(new Date().getTime() - 12 * 60 * 60 * 1000);
        System.out.println(DateUtil.dateToVoString(date));

        date.setTime(new Date().getTime() - 6 * 1000);
        System.out.println(DateUtil.dateToVoString(date));
    }
}