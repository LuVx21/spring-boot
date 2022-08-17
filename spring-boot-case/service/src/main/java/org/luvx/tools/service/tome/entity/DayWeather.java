package org.luvx.tools.service.tome.entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import com.google.common.collect.ImmutableMap;

import lombok.Data;

@Data
public class DayWeather {
    private String conditionDay;
    private String conditionNight;
    private String conditionIdDay;
    private String conditionIdNight;
    private String tempDay;
    private String tempNight;
    private String windDegreesDay;
    private String windDegreesNight;
    private String windDirDay;
    private String windDirNight;
    private String windLevelDay;
    private String windLevelNight;

    private String humidity;
    private String pop;
    private String qpf;
    private String uvi;
    private String predictDate;
    private String updatetime;

    public String info() {
        ImmutableMap<Long, String> map = ImmutableMap.of(0L, "今", 1L, "明", 2L, "后");
        LocalDate day = LocalDate.parse(predictDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        long until = LocalDate.now().until(day, ChronoUnit.DAYS);

        StringBuilder sb = new StringBuilder();
        sb.append("----" + map.get(until) + "天(" + predictDate + ")----\n");
        sb.append("状况: " + a(conditionDay, conditionNight) + "\n");
        sb.append("温度: " + a(tempDay, tempNight) + "\n");
        sb.append("风向: " + a(windDirDay, windDirNight) + "\n");
        sb.append("风级: " + a(windLevelDay, windLevelNight) + "\n");
        return sb.toString();
    }

    private String a(String a, String b) {
        return Objects.equals(a, b) ? a : a + " ~ " + b;
    }
}
