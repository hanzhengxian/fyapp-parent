package com.onway.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.onway.common.lang.DateUtils;
import com.onway.common.lang.StringUtils;
import com.onway.result.DateCal;

/**
 * 
 * <p>
 * Title: DateUtil
 * </p>
 * <p>
 * Description: 日期转换
 * </p>
 * 
 * @author yugang.ni
 * @date 2018年6月27日 下午6:03:40
 */
public class DateUtil extends DateUtils {

    public static void main(String[] args) {
//        List<DateCal> halfYears = getHalfYearsHB("2017", "2019");
//        for (DateCal dateCal : halfYears) {
//            System.out.println(dateToString(dateCal.getStartDate(), newFormat));
//            System.out.println(dateToString(dateCal.getEndDate(), newFormat));
//
//            System.out.println(dateToString(dateCal.getStartDates(), newFormat));
//            System.out.println(dateToString(dateCal.getEndDates(), newFormat));
//            
//            System.err.println("-------");
//        }
        
        System.out.println(getMiddleDayByYear());
    }

    public final static String pointFormat           = "yyyy.MM.dd";

    public final static String pointFormat2          = "yyyy.MM.dd HH:mm:ss";

    public final static String monthOnlyFormatChines = "MM月";

    public final static String hoursFormat           = "yyyy年MM月dd日 HH时";

    public final static String mounthFormat          = "yyyy-MM";

    public final static String hourFormat            = "yyyy-MM-dd HH";

    public static String getMsgTime(Date msgDate) {
        String msgTime = null;
        try {
            String msgDay = dateToString(msgDate, shortFormat);
            // 今天
            String today = dateToString(new Date(), shortFormat);
            // 昨天
            Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
            calendar.add(Calendar.DATE, -1); // 得到昨天
            Date yesterday;

            yesterday = parseDate(new SimpleDateFormat(shortFormat).format(calendar.getTime()),
                shortFormat);

            String yesDay = dateToString(yesterday, shortFormat);

            msgTime = dateToString(msgDate, newFormat);
            if (today.equals(msgDay)) {
                msgTime = "今天" + " " + dateToString(msgDate, HH_MM);
            }
            if (yesDay.equals(msgDay)) {
                msgTime = "昨天" + " " + dateToString(msgDate, HH_MM);
            }

        } catch (ParseException e) {

        }

        return msgTime;
    }

    public static String dateToString(Date date, String format) {
        if (null == date)
            return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(date);
        return str;
    }

    public static Date stringToDate(String str, String format) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            if (!StringUtils.isBlank(str) && StringUtils.notEquals(str, "undefined")) {
                date = sdf.parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取当天日期
     * 
     * @return
     */
    public static Date getToday() {
        Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 0);
        return calendar.getTime();
    }

    public static String getNowString() {
        DateFormat dateFormat = getNewDateFormat(pointFormat2);
        return getDateString(new Date(), dateFormat);
    }

    /**
     * 由出生日期获得年龄
     * 
     * @param birthDay
     * @return
     * @throws Exception
     */
    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            return 0;
            // throw new IllegalArgumentException(
            // "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth)
                    age--;
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * 判断当前日期是否在当月
     * @param birthDay
     * @return
     */
    public static boolean isThisMounth(Date birthDay) {
        if (null == birthDay)
            return false;

        String birthdayMounth = dateToString(birthDay, monthOnlyFormat);
        String todayMounth = dateToString(new Date(), monthOnlyFormat);
        if (StringUtils.equals(birthdayMounth, todayMounth)) {
            return true;
        }

        return false;
    }

    public static Date getTimeByMinute(int minute) {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MINUTE, minute);

        return calendar.getTime();
    }

    public static Date getTimeByDay(int day) {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * 获取当前日期  前/后  mounth 个月  的第一天
     * @param mounth
     * @return
     */
    public static Date getTimeByMounthForFirstDay(int mounth) {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, mounth);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    public static Date getFirstDayByYear() {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_YEAR, 1);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    public static Date getMiddleDayByYear() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getLastMiddleDayByYear() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取年集合 （环比）
     * 
     * @param cacle   向前取cacle年
     * @return
     */
    public static List<DateCal> getYearListHb(String cacle) {

        List<DateCal> dateCals = new LinkedList<DateCal>();
        int index = 5;
        if (StringUtils.isNotBlank(cacle))
            index = Integer.valueOf(cacle);

        for (int i = index; i > 0; i--) {
            Calendar calendar = Calendar.getInstance();
            Date startDates = getCalendarByYear(calendar, -i);
            Date endDates = getCalendarByYear(calendar, 1);
            Date endDate = getCalendarByYear(calendar, 1);

            DateCal cal = new DateCal();
            cal.setStartDate(endDates);
            cal.setEndDate(endDate);
            cal.setStartDates(startDates);
            cal.setEndDates(endDates);
            dateCals.add(cal);
        }
        return dateCals;
    }

    public static Date getCalendarByYear(Calendar calendar, int index) {
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.YEAR, index);

        return calendar.getTime();
    }

    public static Date getCalendarByYearS(Calendar calendar, int index) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.YEAR, index);

        return calendar.getTime();
    }

    /**
     * 获取月集合（环比）
     * 
     * @param cacle  向前取cacle月
     * @return
     */
    public static List<DateCal> getMounthListHb(String cacle) {
        List<DateCal> dateCals = new LinkedList<DateCal>();
        int index = 12;
        if (StringUtils.isNotBlank(cacle))
            index = Integer.valueOf(cacle);

        for (int i = index; i > 0; i--) {
            Calendar calendar = Calendar.getInstance();
            Date startDates = getCalendarByMounth(calendar, -i);
            Date endDates = getCalendarByMounth(calendar, 1);
            Date endDate = getCalendarByMounth(calendar, 1);

            DateCal cal = new DateCal();
            cal.setStartDate(endDates);
            cal.setEndDate(endDate);
            cal.setStartDates(startDates);
            cal.setEndDates(endDates);
            dateCals.add(cal);
        }
        return dateCals;
    }

    public static Date getCalendarByMounth(Calendar calendar, int index) {
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.MONTH, index);

        return calendar.getTime();
    }

    /**
     * 获取月集合 （同比）
     * 
     * @param cacle
     * @return
     */
    public static List<DateCal> getMounthListTb(String cacle) {
        List<DateCal> dateCals = new LinkedList<DateCal>();
        int index = 12;
        if (StringUtils.isNotBlank(cacle))
            index = Integer.valueOf(cacle);

        for (int i = index - 1; i >= 0; i--) {
            Calendar calendar = Calendar.getInstance();//19-4
            Date startDate = getCalendarByMounth(calendar, -i);//19-4
            Date endDate = getCalendarByMounth(calendar, 1);//19-5

            Date startDates = getCalendarByMounth(calendar, -13);//18-4
            Date endDates = getCalendarByMounth(calendar, 1);//18-5

            DateCal cal = new DateCal();
            cal.setStartDate(startDate);
            cal.setEndDate(endDate);
            cal.setStartDates(startDates);
            cal.setEndDates(endDates);
            dateCals.add(cal);
        }
        return dateCals;
    }

    public static List<DateCal> getHoursByDay(Date startDate, Date endDate) {
        //默认取7天
        if (null == startDate) {
            startDate = getTimeByDay(-7);
        }
        if (null == endDate) {
            endDate = new Date();
        }

        Calendar nextHour = Calendar.getInstance();
        nextHour.add(Calendar.HOUR_OF_DAY, 1);
        nextHour.set(Calendar.MINUTE, 0);
        nextHour.set(Calendar.SECOND, 0);
        nextHour.add(Calendar.DATE, 0);
        Date timeNow = nextHour.getTime();

        List<DateCal> dateCals = new LinkedList<DateCal>();

        int differentDaysByMillisecond = differentDaysByMillisecond(startDate, endDate);
        for (int i = 0; i < differentDaysByMillisecond + 1; i++) {
            //起始日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, i);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            for (int j = 9; j < 24; j++) {
                calendar.set(Calendar.HOUR_OF_DAY, j);
                if (differentMinutesByMillisecond(timeNow, calendar.getTime()) > 1) {
                    DateCal dateCal = new DateCal();
                    dateCal.setStartDate(calendar.getTime());
                    calendar.set(Calendar.HOUR_OF_DAY, j + 1);
                    dateCal.setEndDate(calendar.getTime());
                    dateCals.add(dateCal);
                } else {
                    break;
                }
            }
        }
        return dateCals;
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @param startDate
     * @param endDate
     * @return
     */
    public static int differentDaysByMillisecond(Date startDate, Date endDate) {
        int days = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    public static int differentMinutesByMillisecond(Date date1, Date date2) {
        int minutes = (int) ((date1.getTime() - date2.getTime()) / (1000 * 3600));
        return minutes;
    }

    /**
     * 取值年区间
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<DateCal> getYears(String startTime, String endTime) {

        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.yearOnlyFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.yearOnlyFormat);

        List<DateCal> dateCals = new LinkedList<DateCal>();

        int getDiffYears = getDiffYears(endDate, startDate);
        for (int i = 0; i < getDiffYears + 1; i++) {
            //起始日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.YEAR, i);

            DateCal dateCal = new DateCal();
            dateCal.setStartDate(calendar.getTime());
            calendar.add(Calendar.YEAR, 1);
            dateCal.setEndDate(calendar.getTime());
            
            dateCal.setDateFormat(dateToString(dateCal.getStartDate(), yearOnlyFormat));
            
            dateCals.add(dateCal);
        }
        return dateCals;
    }

    @SuppressWarnings("deprecation")
    public static int getDiffYears(Date one, Date two) {
        return one.getYear() - two.getYear();
    }

    /**
     * 取值半年区间
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<DateCal> getHalfYears(String startTime, String endTime) {

        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.yearOnlyFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.yearOnlyFormat);

        List<DateCal> dateCals = new LinkedList<DateCal>();

        int getDiffYears = getDiffYears(endDate, startDate);
        for (int i = 0; i < getDiffYears + 1; i++) {
            //起始日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.YEAR, i);

            DateCal dateCal = new DateCal();
            dateCal.setStartDate(calendar.getTime());
            Date middleDayByYear = getMiddleDayByYear(calendar);
            dateCal.setEndDate(middleDayByYear);
            
            dateCal.setDateFormat(dateToString(dateCal.getStartDate(), yearOnlyFormat) + "上半年");
            
            dateCals.add(dateCal);

            DateCal dateCal_ = new DateCal();
            dateCal_.setStartDate(middleDayByYear);
            calendar.add(Calendar.YEAR, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            dateCal_.setEndDate(calendar.getTime());
            
            dateCal_.setDateFormat(dateToString(dateCal_.getStartDate(), yearOnlyFormat) + "下半年");
            
            dateCals.add(dateCal_);
        }
        return dateCals;
    }

    public static Date getMiddleDayByYear(Calendar calendar) {

        calendar.set(Calendar.MONTH, Calendar.JULY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 月区间
     * 
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<DateCal> getMounths(String startTime, String endTime) {

        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.mounthFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.mounthFormat);

        List<DateCal> dateCals = new LinkedList<DateCal>();

        int getDiffMounths = getDiffMounths(endDate, startDate);
        for (int i = 0; i < getDiffMounths + 1; i++) {
            //起始日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.MONTH, i);

            DateCal dateCal = new DateCal();
            dateCal.setStartDate(calendar.getTime());
            calendar.add(Calendar.MONTH, 1);
            dateCal.setEndDate(calendar.getTime());
            
            dateCal.setDateFormat(dateToString(dateCal.getStartDate(), mounthFormat));
            
            dateCals.add(dateCal);
        }
        return dateCals;
    }

    @SuppressWarnings("deprecation")
    public static int getDiffMounths(Date one, Date two) {
        return one.getMonth() - two.getMonth();
    }

    /**
     * 日区间
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<DateCal> getDays(String startTime, String endTime) {

        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
        endDate = addDays(endDate, 1);

        List<DateCal> dateCals = new LinkedList<DateCal>();

        long getDiffDays = getDiffDays(endDate, startDate);
        for (int i = 0; i < getDiffDays; i++) {
            //起始日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, i);

            DateCal dateCal = new DateCal();
            dateCal.setStartDate(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
            dateCal.setEndDate(calendar.getTime());
            
            dateCal.setDateFormat(dateToString(dateCal.getStartDate(), webFormat));
            
            dateCals.add(dateCal);
        }
        return dateCals;
    }

    /**
     * 小时区间
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<DateCal> getHoursByDay(String startTime, String endTime) {

        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.hourFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.hourFormat);

        List<DateCal> dateCals = new LinkedList<DateCal>();

        int differentDaysByMillisecond = differentMinutesByMillisecond(endDate, startDate);
        for (int i = 0; i < differentDaysByMillisecond + 1; i++) {
            //起始日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.HOUR, i);

            DateCal dateCal = new DateCal();
            dateCal.setStartDate(calendar.getTime());
            calendar.add(Calendar.HOUR, 1);
            dateCal.setEndDate(calendar.getTime());
            
            dateCal.setDateFormat(dateToString(dateCal.getStartDate(), hoursFormat));
            
            dateCals.add(dateCal);
        }
        return dateCals;
    }

    /**
     * 取值年区间  同比
     * 
     * 年（跟上个年度）
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<DateCal> getYearsTB(String startTime, String endTime) {

        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.yearOnlyFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.yearOnlyFormat);

        List<DateCal> dateCals = new LinkedList<DateCal>();

        int getDiffYears = getDiffYears(endDate, startDate);
        for (int i = 0; i < getDiffYears + 1; i++) {
            //起始日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.YEAR, i);

            Date startDate_ = calendar.getTime();
            Date endDate_ = getCalendarByYear(calendar, 1);

            Date startDates = getCalendarByYear(calendar, -2);
            Date endDates = getCalendarByYear(calendar, 1);

            DateCal cal = new DateCal();
            cal.setStartDate(startDate_);
            cal.setEndDate(endDate_);
            cal.setDateFormat(dateToString(startDate_, DateUtil.yearOnlyFormat));
            cal.setStartDates(startDates);
            cal.setEndDates(endDates);
            cal.setDateFormats(dateToString(startDates, DateUtil.yearOnlyFormat));
            dateCals.add(cal);
        }
        return dateCals;
    }

    /**
     * 取值年区间  环比
     * 
     * 年（跟上个年度）
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<DateCal> getYearsHB(String startTime, String endTime) {

        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.yearOnlyFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.yearOnlyFormat);

        List<DateCal> dateCals = new LinkedList<DateCal>();

        int getDiffYears = getDiffYears(endDate, startDate);
        for (int i = 0; i < getDiffYears + 1; i++) {
            //起始日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.YEAR, i);

            Date startDate_ = calendar.getTime();
            Date endDate_ = getCalendarByYear(calendar, 1);

            Date startDates = getCalendarByYear(calendar, -2);
            Date endDates = getCalendarByYear(calendar, 1);

            DateCal cal = new DateCal();
            cal.setStartDate(startDate_);
            cal.setEndDate(endDate_);
            cal.setDateFormat(dateToString(startDate_, DateUtil.yearOnlyFormat));
            cal.setStartDates(startDates);
            cal.setEndDates(endDates);
            cal.setDateFormats(dateToString(startDates, DateUtil.yearOnlyFormat));
            dateCals.add(cal);
        }
        return dateCals;
    }

    /**
     * 取值半年区间   同比
     * 
     * 半年(跟去年同一半年)
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<DateCal> getHalfYearsTB(String startTime, String endTime) {

        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.yearOnlyFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.yearOnlyFormat);

        List<DateCal> dateCals = new LinkedList<DateCal>();

        int getDiffYears = getDiffYears(endDate, startDate);
        for (int i = 0; i < getDiffYears + 1; i++) {
            //起始日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.YEAR, i);

            DateCal dateCal = new DateCal();
            dateCal.setStartDate(calendar.getTime());
            dateCal.setStartDates(getCalendarByYear(calendar, -1));
            getCalendarByYear(calendar, 1);//恢复
            Date middleDayByYear = getMiddleDayByYear(calendar);
            dateCal.setEndDate(middleDayByYear);
            getCalendarByYear(calendar, -1);
            dateCal.setEndDates(getMiddleDayByYear(calendar));
            dateCal.setDateFormat(dateToString(dateCal.getStartDate(), yearOnlyFormat) + "上半年");
            dateCal.setDateFormats(dateToString(dateCal.getStartDates(), yearOnlyFormat) + "上半年");
            dateCals.add(dateCal);

            DateCal dateCal_ = new DateCal();
            dateCal_.setStartDate(getCalendarByYearS(calendar, 1));
            dateCal_.setStartDates(getCalendarByYearS(calendar, -1));
            getCalendarByYearS(calendar, 1);//恢复
            dateCal_.setEndDate(getCalendarByYear(calendar, 1));
            dateCal_.setEndDates(getCalendarByYear(calendar, -1));
            dateCal_.setDateFormat(dateToString(dateCal_.getStartDate(), yearOnlyFormat) + "下半年");
            dateCal_.setDateFormats(dateToString(dateCal_.getStartDates(), yearOnlyFormat) + "下半年");
            dateCals.add(dateCal_);
        }
        return dateCals;
    }

    /**
     * 取值半年区间   环比
     * 
     * 半年(跟上个半年)
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<DateCal> getHalfYearsHB(String startTime, String endTime) {

        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.yearOnlyFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.yearOnlyFormat);

        List<DateCal> dateCals = new LinkedList<DateCal>();

        int getDiffYears = getDiffYears(endDate, startDate);
        for (int i = 0; i < getDiffYears + 1; i++) {
            //起始日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.YEAR, i);

            DateCal dateCal = new DateCal();
            dateCal.setStartDates(calendar.getTime());
            Date middleDayByYear = getMiddleDayByYear(calendar);
            dateCal.setEndDates(middleDayByYear);

            dateCal.setStartDate(middleDayByYear);
            calendar.add(Calendar.YEAR, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            dateCal.setEndDate(calendar.getTime());
            
            dateCal.setDateFormat(dateToString(dateCal.getStartDate(), yearOnlyFormat) + "下半年");
            dateCal.setDateFormats(dateToString(dateCal.getStartDates(), yearOnlyFormat) + "上半年");
            
            dateCals.add(dateCal);
        }
        return dateCals;
    }

    /**
     * 月区间   同比
     * 
     * 月(跟去年同月)
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<DateCal> getMounthsTB(String startTime, String endTime) {

        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.mounthFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.mounthFormat);

        List<DateCal> dateCals = new LinkedList<DateCal>();

        int getDiffMounths = getDiffMounths(endDate, startDate);
        for (int i = 0; i < getDiffMounths + 1; i++) {
            //起始日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.MONTH, i);

            DateCal dateCal = new DateCal();
            dateCal.setStartDate(calendar.getTime());
            dateCal.setStartDates(getCalendarByMounth(calendar, -12));
            getCalendarByMounth(calendar, 12);
            calendar.add(Calendar.MONTH, 1);
            dateCal.setEndDate(calendar.getTime());
            getCalendarByMounth(calendar, -12);
            dateCal.setEndDates(calendar.getTime());
            
            dateCal.setDateFormat(dateToString(dateCal.getStartDate(), mounthFormat));
            dateCal.setDateFormats(dateToString(dateCal.getStartDates(), mounthFormat));
            
            dateCals.add(dateCal);
        }
        return dateCals;
    }

    /**
     * 月区间   环比
     * 
     * 月（跟上月）
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<DateCal> getMounthsHB(String startTime, String endTime) {

        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.mounthFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.mounthFormat);

        List<DateCal> dateCals = new LinkedList<DateCal>();

        int getDiffMounths = getDiffMounths(endDate, startDate);
        for (int i = 0; i < getDiffMounths + 1; i++) {
            //起始日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.MONTH, i);

            DateCal dateCal = new DateCal();
            dateCal.setStartDate(calendar.getTime());
            dateCal.setStartDates(getCalendarByMounth(calendar, -1));
            getCalendarByMounth(calendar, 1);
            calendar.add(Calendar.MONTH, 1);
            dateCal.setEndDate(calendar.getTime());
            getCalendarByMounth(calendar, -1);
            dateCal.setEndDates(calendar.getTime());
            
            dateCal.setDateFormat(dateToString(dateCal.getStartDate(), mounthFormat));
            dateCal.setDateFormats(dateToString(dateCal.getStartDates(), mounthFormat));
            
            dateCals.add(dateCal);
        }
        return dateCals;
    }

    /**
     * 日区间   同比
     * 
     * 日（跟去年同月同日）对比
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<DateCal> getDaysTB(String startTime, String endTime) {

        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
        endDate = addDays(endDate, 1);

        List<DateCal> dateCals = new LinkedList<DateCal>();

        long getDiffDays = getDiffDays(endDate, startDate);
        for (int i = 0; i < getDiffDays; i++) {
            //起始日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, i);

            DateCal dateCal = new DateCal();
            dateCal.setStartDate(calendar.getTime());
            dateCal.setStartDates(getCalendarByMounthDay(calendar, -12));
            getCalendarByMounthDay(calendar, 12);
            calendar.add(Calendar.DATE, 1);
            dateCal.setEndDate(calendar.getTime());
            getCalendarByMounthDay(calendar, -12);
            dateCal.setEndDates(calendar.getTime());
            
            dateCal.setDateFormat(dateToString(dateCal.getStartDate(), webFormat));
            dateCal.setDateFormats(dateToString(dateCal.getStartDates(), webFormat));
            
            dateCals.add(dateCal);
        }
        return dateCals;
    }

    /**
     * 日区间   环比
     * 
     * 日（跟上一日）对比
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<DateCal> getDaysHB(String startTime, String endTime) {

        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.webFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.webFormat);
        endDate = addDays(endDate, 1);

        List<DateCal> dateCals = new LinkedList<DateCal>();

        long getDiffDays = getDiffDays(endDate, startDate);
        for (int i = 0; i < getDiffDays; i++) {
            //起始日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.DATE, i);

            DateCal dateCal = new DateCal();
            dateCal.setStartDate(calendar.getTime());
            dateCal.setStartDates(getCalendarByDay(calendar, -1));
            getCalendarByDay(calendar, 1);
            calendar.add(Calendar.DATE, 1);
            dateCal.setEndDate(calendar.getTime());
            getCalendarByDay(calendar, -1);
            dateCal.setEndDates(calendar.getTime());
            
            dateCal.setDateFormat(dateToString(dateCal.getStartDate(), webFormat));
            dateCal.setDateFormats(dateToString(dateCal.getStartDates(), webFormat));
            
            dateCals.add(dateCal);
        }
        return dateCals;
    }

    /**
     * 小时区间  同比
     * 
     * 时（跟去年同月同日同时）对比
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<DateCal> getHoursByDayTB(String startTime, String endTime) {

        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.hourFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.hourFormat);

        List<DateCal> dateCals = new LinkedList<DateCal>();

        int differentDaysByMillisecond = differentMinutesByMillisecond(endDate, startDate);
        for (int i = 0; i < differentDaysByMillisecond + 1; i++) {
            //起始日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.HOUR, i);

            DateCal dateCal = new DateCal();
            dateCal.setStartDate(calendar.getTime());
            dateCal.setStartDates(getCalendarByMounthDay(calendar, -12));
            getCalendarByMounthDay(calendar, 12);
            calendar.add(Calendar.HOUR, 1);
            dateCal.setEndDate(calendar.getTime());
            getCalendarByMounthDay(calendar, -12);
            dateCal.setEndDates(calendar.getTime());
            
            dateCal.setDateFormat(dateToString(dateCal.getStartDate(), hoursFormat));
            dateCal.setDateFormats(dateToString(dateCal.getStartDates(), hoursFormat));
            
            dateCals.add(dateCal);
        }
        return dateCals;
    }

    /**
     * 小时区间  环比
     * 
     * 时(跟上一时)对比
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<DateCal> getHoursByDayHB(String startTime, String endTime) {

        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime)) {
            return null;
        }

        Date startDate = DateUtil.stringToDate(startTime, DateUtil.hourFormat);
        Date endDate = DateUtil.stringToDate(endTime, DateUtil.hourFormat);

        List<DateCal> dateCals = new LinkedList<DateCal>();

        int differentDaysByMillisecond = differentMinutesByMillisecond(endDate, startDate);
        for (int i = 0; i < differentDaysByMillisecond + 1; i++) {
            //起始日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.HOUR, i);

            DateCal dateCal = new DateCal();
            dateCal.setStartDate(calendar.getTime());
            dateCal.setStartDates(getCalendarByHours(calendar, -1));
            getCalendarByHours(calendar, 1);
            calendar.add(Calendar.HOUR, 1);
            dateCal.setEndDate(calendar.getTime());
            getCalendarByHours(calendar, -1);
            dateCal.setEndDates(calendar.getTime());
            
            dateCal.setDateFormat(dateToString(dateCal.getStartDate(), hoursFormat));
            dateCal.setDateFormats(dateToString(dateCal.getStartDates(), hoursFormat));
            
            dateCals.add(dateCal);
        }
        return dateCals;
    }

    public static Date getCalendarByMounthDay(Calendar calendar, int index) {
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.MONTH, index);

        return calendar.getTime();
    }
    
    public static Date getCalendarByDay(Calendar calendar, int index) {
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, index);

        return calendar.getTime();
    }
    
    public static Date getCalendarByHours(Calendar calendar, int index) {
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.HOUR_OF_DAY, index);

        return calendar.getTime();
    }

}
