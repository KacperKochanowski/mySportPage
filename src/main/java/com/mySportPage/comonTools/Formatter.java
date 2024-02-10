package com.mySportPage.comonTools;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

public class Formatter {

    public static final SimpleDateFormat DATE_TIME_NANO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    public static final SimpleDateFormat DATE_TIME_MIN = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat DATE_TIME_MIN_SEC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE = new SimpleDateFormat("yyyy-MM-dd");

    public static String parseDate(Date date, SimpleDateFormat dateFormat) {
        return dateFormat.format(date);
    }

    public static Date mapToDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        long milliseconds = localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        return new Date(milliseconds);
    }
}
