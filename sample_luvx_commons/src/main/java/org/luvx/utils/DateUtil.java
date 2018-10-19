package org.luvx.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static int getDateSecond(Date date1, Date date2) throws Exception {
        if ((date1 == null) || (date2 == null)) {
            return 0;
        }
        long time1 = date1.getTime();
        long time2 = date2.getTime();

        long diff = time1 - time2;

        Long longValue = new Long(diff / 1000L);

        int di = longValue.intValue();
        return di;
    }
}
