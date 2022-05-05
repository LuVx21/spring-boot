package org.luvx.tome;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.luvx.tome.pojo.Content;
import org.luvx.tome.pojo.MarkdownType;
import org.luvx.tome.pojo.TextType;
import org.luvx.tome.schedule.WeatherNotifyScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ApplicationTest {
    @Autowired
    WeatherNotifyScheduler scheduler;

    @Test
    void m1() throws IOException, InterruptedException {
        TextType textType = new TextType(new Content("你的快递已到，请携带工卡前往邮件中心领取"));
        scheduler.send(textType);

        MarkdownType markdownType = new MarkdownType(new Content("你的**快递**已到，请携带工卡前往`邮件`中心领取"));
        scheduler.send(markdownType);
        // String token = scheduler.getToken();
        // API.println(token);
    }

    @Test
    void m2() throws Exception {
        scheduler.a();
    }

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