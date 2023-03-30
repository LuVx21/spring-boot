package org.luvx.boot.web.service;

import org.luvx.boot.web.common.property.FileProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @author: Ren, Xie
 * @desc:
 */
@Service
public class V1Service {
    private final Path location;

    @Autowired
    public V1Service(FileProperties fileProperties) {
        location = Paths.get(fileProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(location);
        } catch (Exception ex) {
        }
    }

    /**
     * 存储上传的文件
     *
     * @param file
     * @return
     */
    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        if (fileName.contains("..")) {
            throw new RuntimeException("文件名含有非法字符");
        }

        Path targetLocation = location.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("保存文件异常", e);
        }

        return fileName;
    }

    /**
     * 读取资源
     *
     * @param fileName
     * @return
     */
    public Resource loadFile(String fileName) {
        Path path = location.resolve(fileName).normalize();
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists()) {
                return resource;
            }
            throw new RuntimeException("资源不存在");
        } catch (MalformedURLException e) {
            throw new RuntimeException("资源不存在", e);
        }
    }
}
