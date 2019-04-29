package org.luvx.api.java8.Time;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeCase {
    long timestamp = LocalDate.now()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant().toEpochMilli();

    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    public void run01() {
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();
        System.out.println(millis);
        Instant instant = clock.instant();
        System.out.println(instant);
        Date legacyDate = Date.from(instant);
    }

    @Test
    public void run02() {
        System.out.println(ZoneId.getAvailableZoneIds());

        ZoneId zone1 = ZoneId.of("Etc/GMT+8");
        ZoneId zone2 = ZoneId.of("Brazil/East");
        System.out.println(zone1.getRules());
    }


    @Test
    public void run03() {
        LocalDate date = LocalDate.now().plusDays(-1);
        // String str = dateTimeFormatter.format(date);

        LocalDateTime time = LocalDateTime.of(date, LocalTime.MIN);
        String str = dateTimeFormatter.format(time);
        System.out.println(str);

        time = LocalDateTime.of(date, LocalTime.MAX);
        str = dateTimeFormatter.format(time);
        System.out.println(str);
    }


    @Test
    public void run04() {
        String dateStr = "2019-04-26";
        LocalDate date = LocalDate.parse(dateStr);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String str = dateFormatter.format(date);

        date = LocalDate.parse(str, dateFormatter);

        System.out.println(str);
        System.out.println(date);
    }



}

