package org.luvx.tools.base.validate;

import javax.annotation.Nullable;

import org.junit.jupiter.api.Test;

class ValidationControllerTest {
    @Nullable
    public String a() {
        return null;
    }

    @Test
    void main() {
        String a = a();
        // 提示检查 null
        assert a != null;
        System.out.println(a.length());
    }
}