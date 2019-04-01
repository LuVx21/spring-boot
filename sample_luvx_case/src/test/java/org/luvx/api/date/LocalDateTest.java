package org.luvx.api.date;

import org.junit.Test;

import java.time.LocalDate;

/**
 * @ClassName: org.luvx.api.date
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/3/27 11:30
 */
public class LocalDateTest {

    @Test
    public void method0() {
        LocalDate now = LocalDate.now();
        LocalDate weekAgo = LocalDate.now().plusDays(-8);
        LocalDate oneDay = LocalDate.now().plusDays(-7);
        System.out.println(now + ":" + weekAgo + ":" + oneDay);

        System.out.println(oneDay.isBefore(now));
        System.out.println(oneDay.isAfter(weekAgo));
    }
}
