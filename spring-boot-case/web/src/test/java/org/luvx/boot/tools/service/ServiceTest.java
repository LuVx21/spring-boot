package org.luvx.boot.tools.service;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.luvx.boot.tools.BaseAppTests;
import org.luvx.boot.tools.dao.entity.OssFile;
import org.luvx.boot.tools.service.oss.OssService;

import java.util.List;

class ServiceTest extends BaseAppTests {
    @Resource
    private OssService ossService;

    @Test
    void deleteTest() {
    }
}
