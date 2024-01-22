package org.luvx.boot.tools.service.oss;

import com.github.phantomthief.util.MoreFunctions;
import com.google.common.io.ByteStreams;
import io.mybatis.mapper.example.Example;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.luvx.boot.tools.dao.entity.OssFile;
import org.luvx.boot.tools.dao.mapper.OssFileMapper;
import org.luvx.coding.common.consts.Common;
import org.luvx.coding.common.consts.Properties;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class OssService {
    public static final String IMG_HOME = STR."\{Properties.DIR_USER_HOME}/data/luvx/oss";

    @Resource
    private OssFileMapper ossFileMapper;

    public void add(OssScopeEnum scope, String fileName) {
        OssFile ossFile = new OssFile();
        ossFile.setScope(scope.getCode());
        ossFile.setFile(fileName);
        ossFile.setVisitCount(0);
        ossFileMapper.insert(ossFile);
    }

    public byte[] get(String scope, String fileName) throws Exception {
        CompletableFuture.runAsync(() -> {
            Example<OssFile> example = new Example<>();
            example.set("visit_count = visit_count + 1").createCriteria()
                    .andEqualTo(OssFile::getScope, scope)
                    .andEqualTo(OssFile::getFile, fileName);
            ossFileMapper.updateByExampleSetValues(example);
        }, Common.THREAD_POOL_EXECUTOR_SUPPLIER.get());
        File file = Path.of(IMG_HOME, scope, fileName).toFile();
        InputStream inputStream = new FileInputStream(file);
        return ByteStreams.toByteArray(inputStream);
    }

    public List<OssFile> deleteByVisitCount(Long count, String fileName) {
        Example<OssFile> example = new Example<>();
        Example.Criteria<OssFile> criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(fileName)) {
            criteria.andEqualTo(OssFile::getFile, fileName);
        } else {
            criteria.andLessThanOrEqualTo(OssFile::getVisitCount, count)
                    .andLessThan(OssFile::getCreateTime, LocalDateTime.now().plusDays(-7));
        }

        List<OssFile> ossFiles = ossFileMapper.selectByExample(example);
        ossFiles.forEach(f -> {
            ossFileMapper.deleteByPrimaryKey(f.getId());
            MoreFunctions.runCatching(() -> Files.delete(Path.of(IMG_HOME, f.getScope(), f.getFile())));
        });
        return ossFiles;
    }
}
