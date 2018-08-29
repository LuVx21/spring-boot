package org.luvx.pattern.Strategy;

import org.luvx.entity.User;

import java.util.Comparator;
import java.util.List;

/**
 * 策略模式
 */
public class UserComUtil {
    /**
     * 针对两个自定义对象
     * 选用不同的依据对其进行比较
     * <p>
     * 在增加新的比较依据时,不用修改现有代码
     */
    public static int getComResult(List<User> list, Comparator comparator) {
        User user1 = list.get(0);
        User user2 = list.get(1);
        return comparator.compare(user1, user2);
    }
}