package org.luvx.boot.thymeleaf.upload;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/file")
public class FileUploadController {

    private final String path = "/Users/renxie/Desktop/upload/";

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void uploadFileAction(@RequestParam("file") MultipartFile uploadFile) {
        try (InputStream fis = uploadFile.getInputStream();
             OutputStream os = new FileOutputStream(path + uploadFile.getOriginalFilename())
        ) {
            IOUtils.copy(fis, os);
        } catch (Exception e) {
        }
    }

    @RequestMapping("download")
    public void downloadFileAction(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        File file = new File(path + "runtime.js");
        try (FileInputStream fis = new FileInputStream(file)) {
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            IOUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
        }
    }
}
