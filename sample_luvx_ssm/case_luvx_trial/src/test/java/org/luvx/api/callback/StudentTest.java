package org.luvx.api.callback;

import org.junit.Test;

import java.util.Arrays;

public class StudentTest {

    @Test
    public void run01() {
        Student s1 = new Student(2, "a", 18, 89);
        Student s2 = new Student(3, "x", 22, 94);
        Student s3 = new Student(1, "w", 19, 78);
        Student[] arrs = {s1, s2, s3};
        Arrays.sort(arrs);
        for (Student student : arrs) {
            System.out.println(student);
        }
    }
}
