package org.luvx.tome.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
}
