package org.luvx.boot.mars.service.oss;

import com.github.phantomthief.util.MoreFunctions;
import com.google.common.io.ByteStreams;
import io.mybatis.mapper.example.Example;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.luvx.boot.mars.common.Consts;
import org.luvx.boot.mars.dao.entity.OssFile;
import org.luvx.boot.mars.dao.mapper.OssFileMapper;
import org.luvx.coding.common.consts.Common;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
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
    public static final String IMG_HOME = Consts.DATA_HOME + "/luvx/oss";

    @Resource
    private OssFileMapper ossFileMapper;

    public void add(OssScopeEnum scope, String fileName) {
        OssFile ossFile = new OssFile();
        ossFile.setScope(scope.getCode());
        ossFile.setFile(fileName);
        ossFile.setVisitCount(0);
        try {
            ossFileMapper.insert(ossFile);
        } catch (DuplicateKeyException ignore) {
        }
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
        if (!file.exists()) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        InputStream inputStream = new FileInputStream(file);
        return ByteStreams.toByteArray(inputStream);
    }

    public List<Long> deleteByVisitCount(Long count, String fileName) {
        Example<OssFile> example = new Example<>();
        Example.Criteria<OssFile> criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(fileName)) {
            criteria.andEqualTo(OssFile::getFile, fileName);
        } else {
            criteria.andLessThanOrEqualTo(OssFile::getVisitCount, count)
                    .andLessThan(OssFile::getCreateTime, LocalDateTime.now().plusDays(-7));
        }

        List<OssFile> ossFiles = ossFileMapper.selectByExample(example);
        return ossFiles.stream()
                .peek(f -> {
                    ossFileMapper.deleteByPrimaryKey(f.getId());
                    MoreFunctions.runCatching(() -> Files.delete(Path.of(IMG_HOME, f.getScope(), f.getFile())));
                })
                .map(OssFile::getId)
                .toList();
    }
}
