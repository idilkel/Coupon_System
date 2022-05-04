package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateUtils {

    public static String beautifyDate(java.sql.Date date) {
        LocalDate localDate = date.toLocalDate();
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        return String.format("%02d/%02d/%04d", day, month, year);
    }

    public static String format(LocalDateTime time) {
        int day = time.getDayOfMonth();
        int month = time.getMonthValue();
        int year = time.getYear();
        int hour = time.getHour();
        int min = time.getMinute();
        int sec = time.getSecond();
        return String.format("%02d/%02d/%04d - %02d:%02d:%02d", day, month, year, hour, min, sec);
    }

}
