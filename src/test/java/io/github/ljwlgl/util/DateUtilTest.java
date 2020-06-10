package io.github.ljwlgl.util;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DateUtilTest {

    @Test
    public void testGetCurrentDate() {
        assertEquals(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), DateUtil.getCurrentDate());
    }

    @Test
    public void testIsSameDay() {
        assertTrue(DateUtil.isSameDay(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    public void testToCalendar() {
        // Setup
        final Calendar expectedResult = Calendar.getInstance(Locale.US);

        // Run the test
        final Calendar result = DateUtil.toCalendar(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime());

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testCalcIntervalDays() {
        assertEquals(0, DateUtil.calcIntervalDays("dateStr1", "dateStr2"));
    }

    @Test
    public void testCalcIntervalOurs() {
        assertEquals(0, DateUtil.calcIntervalOurs(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    public void testCalcIntervalMinutes() {
        assertEquals(0, DateUtil.calcIntervalMinutes(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    public void testCalcIntervalDays1() {
        assertEquals(0, DateUtil.calcIntervalDays(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    public void testDayOfWeek() {
        assertEquals(0, DateUtil.dayOfWeek(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    public void testGetTodayMinutes() {
        assertEquals(0, DateUtil.getTodayMinutes());
    }

    @Test
    public void testGetIntervalDaysDate() {
        assertEquals(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), DateUtil.getIntervalDaysDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), 0));
    }

    @Test
    public void testGetIntervalHourDate() {
        assertEquals(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), DateUtil.getIntervalHourDate(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), 0));
    }

    @Test
    public void testDateToShortDateString() {
        assertEquals("result", DateUtil.dateToShortDateString(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    public void testDateToString() {
        assertEquals("result", DateUtil.dateToString(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), "format"));
    }

    @Test
    public void testStringToDate() {
        assertEquals(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime(), DateUtil.stringToDate("dateStr"));
    }

    @Test
    public void testDateToVoString() {
        assertEquals("result", DateUtil.dateToVoString(new GregorianCalendar(2019, Calendar.JANUARY, 1).getTime()));
    }

    @Test
    public void getYear() {
        assertEquals(2020, DateUtil.getYear(new Date()));
    }

    @Test
    public void getMonth() {
//        assertEquals(5, DateUtil.getMonth(new Date("2020-05-05")));
    }

    @Test
    public void getDay() {

        int day = DateUtil.getDay(new Date("2020-02-02"));
    }

    @Test
    public void getMonthLastDay() {


        int day = DateUtil.getMonthLastDay(2020, 5);
    }

    @Test
    public void getPercentage() {
        float per = DateUtil.getPercentage(new Date());
    }

    @Test
    public void isLeap() {

        assertEquals(false, DateUtil.isLeap(2021));
    }


    @Test
    public void startTime() {

        //  Date date = DateUtil.startTime(null);
    }

    @Test
    public void endTime() {

        // assertEquals(new Date(), DateUtil.getMonthFirstDay(2020, 5));
    }

    @Test
    public void getMonthFirstDay() {
        assertEquals(1, DateUtil.getMonthFirstDay(2020, 5));

    }
}
