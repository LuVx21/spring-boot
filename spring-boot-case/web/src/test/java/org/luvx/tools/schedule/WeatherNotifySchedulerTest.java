package org.luvx.tools.schedule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WeatherNotifySchedulerTest {
    @Autowired
    WeatherNotifyScheduler scheduler;

    @Test
    void m3() throws Exception {
        String json = """
                {
                    "code": 0,
                    "data": {
                        "city": {
                            "cityId": 542,
                            "counname": "中国",
                            "ianatimezone": "Asia/Shanghai",
                            "name": "江夏区",
                            "pname": "湖北省",
                            "secondaryname": "武汉市",
                            "timezone": "8"
                        },
                        "forecast": [
                            {
                                "conditionDay": "阵雨",
                                "conditionIdDay": "3",
                                "conditionIdNight": "7",
                                "conditionNight": "小雨",
                                "humidity": "74",
                                "pop": "53",
                                "predictDate": "2022-04-27",
                                "qpf": "7.4",
                                "tempDay": "27",
                                "tempNight": "18",
                                "updatetime": "2022-04-27 20:12:00",
                                "uvi": "5",
                                "windDegreesDay": "90",
                                "windDegreesNight": "90",
                                "windDirDay": "东风",
                                "windDirNight": "东风",
                                "windLevelDay": "3-4",
                                "windLevelNight": "3-4"
                            },
                            {
                                "conditionDay": "大雨",
                                "conditionIdDay": "9",
                                "conditionIdNight": "7",
                                "conditionNight": "小雨",
                                "humidity": "83",
                                "pop": "77",
                                "predictDate": "2022-04-28",
                                "qpf": "34.3",
                                "tempDay": "20",
                                "tempNight": "11",
                                "updatetime": "2022-04-27 20:12:00",
                                "uvi": "1",
                                "windDegreesDay": "0",
                                "windDegreesNight": "0",
                                "windDirDay": "北风",
                                "windDirNight": "北风",
                                "windLevelDay": "4-5",
                                "windLevelNight": "4-5"
                            },
                            {
                                "conditionDay": "多云",
                                "conditionIdDay": "1",
                                "conditionIdNight": "7",
                                "conditionNight": "小雨",
                                "humidity": "74",
                                "pop": "13",
                                "predictDate": "2022-04-29",
                                "qpf": "0.0",
                                "tempDay": "20",
                                "tempNight": "13",
                                "updatetime": "2022-04-27 20:12:00",
                                "uvi": "2",
                                "windDegreesDay": "0",
                                "windDegreesNight": "0",
                                "windDirDay": "北风",
                                "windDirNight": "北风",
                                "windLevelDay": "3-4",
                                "windLevelNight": "1"
                            }
                        ]
                    },
                    "msg": "success",
                    "rc": {
                        "c": 0,
                        "p": "success"
                    }
                }
                """;
        scheduler.weatherJson(json);
    }
}