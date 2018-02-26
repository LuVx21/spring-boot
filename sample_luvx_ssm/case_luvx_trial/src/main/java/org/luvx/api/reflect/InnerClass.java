package org.luvx.api.reflect;


/**
 * 反射机制用类
 */
public class InnerClass {

    private String astrofInner = "a000";
    private Integer num = 17;

    /**
     * 无参构造
     */
    public InnerClass() {

    }

    /**
     * 有参构造
     */
    public InnerClass(String astrofInner, Integer num) {
        this.astrofInner = astrofInner;
        this.num = num;
    }

    public class Apple {
        private String astrInApple = "aaa";
    }

    public class Bear {
        public String astroInBear = "bbb";
    }

    public static class Peach {
        private static String astrInPeach = "ccc1";
        public static String bstrInPeach = "ccc2";
    }

    public static class Watermelon {
        public String name = "old Watermelon";
        private String name2 = "small Watermelon";
        private static String astrInWatermelon = "ccc1";
        public static String bstrInWatermelon = "ccc2";
    }

    /**
     * getter&setter
     *
     * @return
     */
    public String getAstrofInner() {
        return astrofInner;
    }

    public void setAstrofInner(String astrofInner) {
        this.astrofInner = astrofInner;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }


    private Runnable r = new Runnable() {
        @Override
        public void run() {
            System.out.println("no name");
        }
    };

    /**
     * 静态方法
     *
     * @param a
     * @param b
     * @return
     */
    public static int mul1(int a, int b) {
        return b * a;
    }

    /**
     * 私有
     *
     * @param a
     * @param b
     * @return
     */
    private int mul(int a, int b) {
        return a * b;
    }

    /**
     * 公有
     *
     * @param a
     * @param b
     * @return
     */
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * 参数类型不同
     *
     * @param a
     * @param b
     * @return
     */
    public String strAdd(String a, Integer b) {
        return a + b.toString();
    }

    /**
     * 参数为包装类
     *
     * @param a
     * @param b
     * @return
     */
    public Integer add(Integer a, Integer b) {
        return a + b;
    }


}
