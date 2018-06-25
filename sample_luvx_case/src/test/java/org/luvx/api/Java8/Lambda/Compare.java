package org.luvx.api.Java8.Lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Compare {

    @Test
    public void compareTest() {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");


//        Collections.sort(names, new Comparator<String>() {
//            @Override
//            public int compare(String a, String b) {
//                return b.compareTo(a);
//            }
//        });

//        Collections.sort(names, (String a, String b) -> {
//            return b.compareTo(a);
//        });

//        Collections.sort(names, (String a, String b) -> b.compareTo(a));
        Collections.sort(names, (a, b) -> b.compareTo(a));

        System.out.println(names);
    }


}
