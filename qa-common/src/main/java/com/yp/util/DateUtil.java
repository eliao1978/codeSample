package com.yp.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * @return current timestamp
     */
    public static Timestamp getTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * @param timestamp timestamp
     * @return formatted start date
     */
    public static String getCurrentDate(Timestamp timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(timestamp);
    }


    /**
     * @param timestamp timestamp
     * @return formatted start date
     */
    public static String getStartDate(Timestamp timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(timestamp);
    }

    /**
     * @param days # of days
     * @return formatted end date
     */
    public static String getEndDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        Date date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(date);
    }

    /**
     * @param days # of days
     * @return future date
     */
    public static Timestamp getFutureDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return new Timestamp(calendar.getTime().getTime());
    }

    /**
     * @param days # of days
     * @return past date
     */
    public static Timestamp getPastDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -days);
        return new Timestamp(calendar.getTime().getTime());
    }
}
