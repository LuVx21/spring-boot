package org.luvx.validate;

import javax.annotation.Nullable;

public class ValidationService {
    @Nullable
    public String a() {
        return null;
    }

    public static void main(String[] args) {
        ValidationService exec = new ValidationService();
        String a = exec.a();
        // 提示检查 null
        assert a != null;
        System.out.println(a.length());
    }
}
