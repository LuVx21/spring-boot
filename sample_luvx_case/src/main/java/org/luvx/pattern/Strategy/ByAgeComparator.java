package org.luvx.pattern.Strategy;

import org.luvx.entity.User;

import java.util.Comparator;

public class ByAgeComparator implements Comparator<User> {
    public int compare(User user1, User user2) {
        return user1.getAge() - user2.getAge();
    }
}