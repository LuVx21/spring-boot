package org.luvx.boot.thymeleaf.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {

    private final String path = "/Users/renxie/Desktop/upload/";

    @PostMapping(value = "/upload1")
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        String fileName = file.getOriginalFilename();
        log.info("上传的文件名：{}", fileName);

        File dest = new File(path + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return "上传成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

    /**
     * 批量上传, 不可用
     */
    @PostMapping(value = "/batchUpload1")
    public void upload(@RequestParam("files") MultipartFile[] files) {
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            log.info("上传的文件名：{}", fileName);
            File dest = new File(path + fileName);
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                log.info("上传失败:{}", file.getOriginalFilename());
            }
        }
    }

    @PostMapping("/batchUpload2")
    public String handleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        List<MultipartFile> collect = files.stream()
                .filter(f -> !f.isEmpty())
                .toList();
        if (collect.size() != files.size()) {
            return "存在空文件";
        }
        for (MultipartFile file : collect) {
            try {
                file.transferTo(new File(path + file.getOriginalFilename()));
            } catch (Exception e) {
                log.info("上传失败:{}", file.getOriginalFilename());
            }
        }
        return "上传成功";
    }
}
