package org.luvx.api.date;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

/**
 * @ClassName: org.luvx.api.date
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/20 17:52
 */
public class LocalDateTimeCaseTest {
    // https://lw900925.github.io/java/java8-newtime-api.html

    private static DateTimeFormatter dateFormatter     = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static DateTimeFormatter timeFormatter     = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss.SSS");

    @Test
    public void LocalDateTest() {
        LocalDate date = LocalDate.now().plusDays(-1);
        System.out.println(dateFormatter.format(date));
    }

    @Test
    public void LocalTimeTest() {
        LocalTime time = LocalTime.now();
        System.out.println(timeFormatter.format(time));
    }

    @Test
    public void LocalDateTimeTest() {
        LocalDateTime dateTime = LocalDateTime.now().plusDays(-1);
        System.out.println(dateTimeFormatter.format(dateTime));
    }

}