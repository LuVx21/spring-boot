package org.luvx.guava;

import com.google.common.base.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import com.google.common.io.Files;
import org.junit.Test;
import org.luvx.entity.User;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: org.luvx.guava
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/24 21:51
 */
public class ArgsCheckTest {
    /**
     * 前置条件: 参数检查
     */
    @Test
    public void method0() {
        String str = "aa";
        System.out.println(Strings.isNullOrEmpty(str));

        int count = 1;
        Preconditions.checkArgument(count > 0, "Argument was %s but expected non negative", count);

        Preconditions.checkNotNull(str);

        Preconditions.checkElementIndex(9, 10);

        Preconditions.checkPositionIndexes(1, 2, 10);
    }

    @Test
    public void method1() {
        User person = new User("foo", "bar", 11);
        String str = MoreObjects.toStringHelper("Person").add("age", person.getAge()).toString();
        System.out.println(str);
    }

    @Test
    public void method2() {
        User person = new User("aa", "aa", 14);
        User ps = new User("bb", "bb", 13);
        Ordering<User> byOrdering = Ordering.natural().nullsFirst().onResultOf((u) -> u.getAge() + "");
        byOrdering.compare(person, ps);
        // 1 person的年龄比ps大 所以输出1
        System.out.println(byOrdering.compare(person, ps));
    }

    /**
     * org.springframework.util.StopWatch
     */
    @Test
    public void method3() throws Exception {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Thread.sleep(1000);
        long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println(nanos);
    }

    @Test
    public void method4() {
        File file = new File("/test.txt");
        List<String> list = null;
        try {
            list = Files.readLines(file, Charsets.UTF_8);
        } catch (Exception e) {
        }

        // 复制文件
        // Files.copy(from, to);
        // 清空指定文件夹
        // Files.deleteDirectoryContents(File directory); //删除文件夹下的内容(包括文件与子文件夹)
        // 删除文件
        // Files.deleteRecursively(File file); //删除文件或者文件夹
        // 移动文件
        // Files.move(File from, File to); //移动文件
        // 获取文件路径
        // URL url = Resources.getResource("abc.xml"); //获取classpath根下的abc.xml文件url
    }

    @Test
    public void method5() throws Exception {
        LoadingCache<String, String> cahceBuilder = CacheBuilder
                .newBuilder()
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        String strProValue = "hello " + key + "!";
                        return strProValue;
                    }
                });
        System.out.println(cahceBuilder.apply("begincode"));  //hello begincode!
        System.out.println(cahceBuilder.get("begincode")); //hello begincode!
        System.out.println(cahceBuilder.get("wen")); //hello wen!
        System.out.println(cahceBuilder.apply("wen")); //hello wen!
        System.out.println(cahceBuilder.apply("da"));//hello da!
        cahceBuilder.put("begin", "code");
        System.out.println(cahceBuilder.get("begin")); //code
    }

    @Test
    public void method6() throws Exception {
        Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1000).build();
        String resultVal = cache.get("code", new Callable<String>() {
            public String call() {
                String strProValue = "begin " + "code" + "!";
                return strProValue;
            }
        });
        System.out.println("value : " + resultVal); //value : begin code!
    }

    /**
     * null值检测
     */
    @Test
    public void method7() {
        method7_1();
        // method7_2();
    }

    public void method7_1() {
        String str = "foobar";
        Optional<String> possible = Optional.of(str);
        System.out.println(possible.isPresent());
        System.out.println(possible.get());

        str = null;
        possible = Optional.fromNullable(str);

    }

    public void method7_2() {
        List<String> list = ImmutableList.of("a", "b", "c", "d");
        list = Optional.fromNullable(list).or(Collections.EMPTY_LIST);
        System.out.println(list);
        list = null;
        list = Optional.fromNullable(list).or(Collections.EMPTY_LIST);
        System.out.println(list);
    }
}
