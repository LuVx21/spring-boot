package org.luvx.boot.thymeleaf.upload;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

@Slf4j
@RestController
@RequestMapping("/download")
public class DownloadController {
    private final String path     = "/Users/renxie/Desktop/upload/";
    private final String fileName = "application.yml";

    @GetMapping("/download1")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        File file = new File(path + fileName);
        try (FileInputStream fis = new FileInputStream(file)) {
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
        }
    }

    @GetMapping("/download2")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        if (fileName == null) {
            return "下载失败";
        }

        //设置文件路径
        File file = new File(path + fileName);
        if (file.exists()) {
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            try (FileInputStream fis = new FileInputStream(file)) {
                IOUtils.copy(fis, response.getOutputStream());
                response.flushBuffer();
            } catch (Exception e) {
                log.error("下载失败", e);
            }
        }
        return "下载成功";
    }
}