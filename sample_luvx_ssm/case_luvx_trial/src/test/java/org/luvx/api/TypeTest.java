package org.luvx.api;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author renxie
 */
public class TypeTest {

    /**
     * double->int:会丢失小数部分
     * 效果上相当于向下取整
     */
    @Test
    public void doubleToInt() {
        // 2
        int num = (int) 2.62;
        // 8位有效数字,double为17位
        float f = 3.12345678f;
        System.out.println(num);
    }

    /**
     * valueOf()
     * parseInt()
     */
    @Test
    public void intToString() {
        int num = 2354;
        String str = num + "";
        System.out.println(str);

        // 调用包装类:Integer.toString()
        String str1 = String.valueOf(num);
        assert str != str1;
    }

    @Test
    public void stringToint() {
        int num = Integer.parseInt("3245");
        System.out.println(num);
    }

    @Test
    public void toDate() throws ParseException {
        String str = "2018-02-03 a";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(str);
        System.out.println(date);

        String adate = sdf.format(new Date());
        System.out.println(adate);
    }

}
