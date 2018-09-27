package org.luvx.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.log4j.Logger;

public class DateTimeUtils {
    private static Logger logger = Logger.getLogger(DateTimeUtils.class);

    public static Date parseTime(String timeString) {
        Date time = null;
        try {
            time = DateUtils.parseDate(timeString, new String[]{"HH:mm"});
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static Date trancateToDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTime();
    }

    public String formatDateEndWithMinute(Date date) {
        return FastDateFormat.getInstance("yyyy-MM-dd HH:mm").format(date);
    }

    public static String addToday(String timeString) {
        if (timeString.length() == 5) {
            timeString = timeString + ":00";
        }
        return DateFormatUtils.format(new Date(), "yyyy-MM-dd") + " " + timeString;
    }

    public static String formatFullDate(Date date) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static synchronized Date parseFullDate(String str) {
        if (str == null) {
            return null;
        }
        if ("".equals(str)) {
            return null;
        }
        try {
            return DateUtils.parseDate(str, new String[]{"yyyy-MM-dd HH:mm:ss"});
        } catch (Exception e) {
            logger.error("出错了", e);
        }
        return null;
    }

    public static String formatDate(Date date) {
        return DateFormatUtils.format(date, "yyyy-MM-dd");
    }

    public static Date getToday() {
        return DateUtils.truncate(new Date(), 5);
    }

    public static String getTodayString() {
        return DateFormatUtils.format(getToday(), "yyyy-MM-dd");
    }

    public static Date getYesterday() {
        return DateUtils.addDays(getToday(), -1);
    }

    public static String getYesterdayString() {
        return DateFormatUtils.format(getYesterday(), "yyyy-MM-dd");
    }

    public static Date getTomorrow() {
        return DateUtils.addDays(getToday(), 1);
    }

    public static String getTomorrowString() {
        return DateFormatUtils.format(getTomorrow(), "yyyy-MM-dd");
    }

    public static int getDateDiffMinute(Date date1, Date date2) {
        if ((date1 == null) || (date2 == null)) {
            return 0;
        }
        long time1 = date1.getTime();
        long time2 = date2.getTime();

        long diff = time1 - time2;

        Long longValue = new Long(diff / 60000L);

        int di = longValue.intValue();
        return di;
    }

    public static Date parseDate(String dateString) {
        Date date = null;
        try {
            date = DateUtils.parseDate(dateString, new String[]{"yyyy-MM-dd"});
        } catch (ParseException e) {
            logger.error("出错了", e);
        }
        return date;
    }

    public static Date addDay(Date date, int n) {
        return DateUtils.addDays(date, n);
    }

    public static Date addMinute(Date date, int n) {
        return DateUtils.addMinutes(date, n);
    }

    public static String format(Date date, String pattern) {
        return date == null ? null : DateFormatUtils.format(date, pattern);
    }

    public static boolean checkDate(String sourceDate, String pattern) {
        if (sourceDate == null) {
            return false;
        }
        try {
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            dateFormat.setLenient(false);
            dateFormat.parse(sourceDate);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static Date parse(String strDate, String pattern) {
        try {
            return (strDate == null) || (strDate.equals("")) ? null : DateUtils.parseDate(strDate, new String[]{pattern});
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parse(String strDate, String pattern, String localeString) {
        DateFormat dateFormat = new SimpleDateFormat(pattern, new Locale(localeString));
        try {
            return (strDate == null) || (strDate.equals("")) ? null : dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFlightDate() {
        Date now = new Date();
        if (isTimeBetweenClock(now, 0, 4)) {
            return getYesterdayString();
        }
        return getTodayString();
    }

    public static boolean isTimeBetweenClock(Date date, int startClock, int endClock) {
        if ((startClock > endClock) && (endClock == 0)) {
            endClock = 24;
        }
        long currentClock = DateUtils.getFragmentInHours(date, 5);
        return (startClock <= currentClock) && (endClock > currentClock);
    }

    public static boolean isTimeBetweenTime(Date date, Date startDate, Date endDate) {
        long currentClock = DateUtils.getFragmentInMilliseconds(date, 5);
        long startMilliseconds = DateUtils.getFragmentInMilliseconds(startDate, 5);
        long endMilliseconds = DateUtils.getFragmentInMilliseconds(endDate, 5);
        if (endMilliseconds == 0L) {
            endMilliseconds = 86400000L;
        }
        return (currentClock >= startMilliseconds) && (currentClock < endMilliseconds);
    }

    public static boolean isTimeBetweenTime(Date date, String startTimeString, String endTimeString) {
        Date startTime = parseTime(startTimeString);
        Date endTime = parseTime(endTimeString);
        long currentClock = DateUtils.getFragmentInMilliseconds(date, 5);
        long startMilliseconds = DateUtils.getFragmentInMilliseconds(startTime, 5);
        long endMilliseconds = DateUtils.getFragmentInMilliseconds(endTime, 5);
        if (endMilliseconds == 0L) {
            endMilliseconds = 86400000L;
        }
        return (currentClock >= startMilliseconds) && (currentClock < endMilliseconds);
    }

    public static String fillTimeWithFlightDate(String timeString, String pattern)
            throws ParseException {
        return fillTimeWithDate(timeString, pattern, getFlightDate());
    }

    public static String fillTimeWithFlightDate(String timeString, String pattern, String localeString)
            throws ParseException {
        return fillTimeWithDate(timeString, pattern, getFlightDate(), localeString);
    }

    public static String fillTimeWithToday(String timeString, String pattern)
            throws ParseException {
        return fillTimeWithDate(timeString, pattern, getTodayString());
    }

    public static String fillTimeHHMMWithFlightDate(String timeString)
            throws ParseException {
        return fillTimeWithFlightDate(timeString, "HH:mm");
    }

    public static String fillTimeHHMMWithToday(String timeString)
            throws ParseException {
        return fillTimeWithToday(timeString, "HH:mm");
    }

    public static String fillTimeWithDate(String timeString, String pattern, String dateString, String localeString) {
        Date date = parseDate(dateString);
        if ((pattern.indexOf("y") == -1) && (pattern.indexOf("M") == -1) && (pattern.indexOf("d") == -1)) {
            return formatFullDate(parse(dateString + " " + timeString, "yyyy-MM-dd " + pattern, localeString));
        }
        if ((pattern.indexOf("y") == -1) && (pattern.indexOf("M") == -1)) {
            return formatFullDate(parse(format(date, "yyyy-MM-") + timeString, "yyyy-MM-" + pattern, localeString));
        }
        if (pattern.indexOf("y") == -1) {
            return formatFullDate(parse(format(date, "yyyy-") + timeString, "yyyy-" + pattern, localeString));
        }
        return formatFullDate(parse(timeString, pattern));
    }

    public static String fillTimeWithDate(String timeString, String pattern, String dateString) {
        String localeLanguageString = "zh_CN";
        return fillTimeWithDate(timeString, pattern, dateString, localeLanguageString);
    }

    public static boolean isNotIncludeYMD(String pattern) {
        return (pattern.indexOf("y") == -1) && (pattern.indexOf("M") == -1) && (pattern.indexOf("d") == -1);
    }

    public static boolean isNotIncludeYM(String pattern) {
        return (pattern.indexOf("y") == -1) && (pattern.indexOf("M") == -1);
    }

    public static boolean isIncludeD(String pattern) {
        return pattern.indexOf("d") != -1;
    }

    public boolean isNotIncludeY(String pattern) {
        return pattern.indexOf("y") == -1;
    }

    public static String fillDateWithToday(String dateString, String pattern)
            throws ParseException {
        Date date = new Date();
        if ((pattern.indexOf("y") == -1) && (pattern.indexOf("M") == -1)) {
            return formatDate(parse(format(date, "yyyy-MM-") + dateString, "yyyy-MM-" + pattern));
        }
        if (pattern.indexOf("y") == -1) {
            return formatDate(parse(format(date, "yyyy-") + dateString, "yyyy-" + pattern));
        }
        return formatDate(parse(dateString, pattern));
    }

    public static String addDayInDateFormat(String dateString, int days) {
        Date date = parseDate(dateString);
        date = addDay(date, days);
        return formatDate(date);
    }

    public static String addDayInFullFormat(String dateString, int days) {
        Date date = parseFullDate(dateString);
        date = addDay(date, days);
        return formatFullDate(date);
    }

    public static String getCurrentYear() {
        return format(new Date(), "yyyy");
    }

    public static Date getNextRunTime(String timeString) throws Exception {
        long diffSeconds = DateUtil.getDateSecond(new Date(), parseFullDate(getTodayString() + " " + timeString));

        boolean passed = diffSeconds >= 0L;
        String scheduleDate = null;
        if (passed) {
            scheduleDate = getTomorrowString();
        } else {
            scheduleDate = getTodayString();
        }
        return parseFullDate(scheduleDate + " " + timeString);
    }

    public static Date getNextRunTimeOfWeek(String timeString, int dayOfWeek) throws Exception {
        Date date = new Date();
        date = getNextDayOfWeek(dayOfWeek, date);

        long diffSeconds = DateUtil.getDateSecond(date, parseFullDate(getTodayString() + " " + timeString));

        boolean passed = diffSeconds >= 0L;
        String scheduleDate = null;
        if (passed) {
            date = getNextDayOfWeek(dayOfWeek, addDay(date, 1));
        }
        scheduleDate = formatDate(date);
        return parseFullDate(scheduleDate + " " + timeString);
    }

    public static Date getNextDayOfWeek(int dayOfWeek, Date refDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(refDate);
        while (calendar.get(7) != dayOfWeek) {
            calendar.add(5, 1);
        }
        return calendar.getTime();
    }
}
