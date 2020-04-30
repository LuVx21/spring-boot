package org.luvx.upload;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: org.luvx.upload
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/23 20:00
 */
@RestController
@RequestMapping("/file1")
public class FileUploadController1 {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadController1.class);
    private static final String PATH   = "E:\\temp\\upload\\";

    @RequestMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "文件为空";
        }
        String fileName = file.getOriginalFilename();
        logger.info("上传的文件名：{}", fileName);

        File dest = new File(PATH + fileName);
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
     *
     * @param multipartFiles
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/upload1")
    @ResponseBody
    public ModelAndView upload(@RequestParam("file") MultipartFile[] multipartFiles) throws IOException {
        try {
            for (MultipartFile multipartFile : multipartFiles) {
                String fname = multipartFile.getOriginalFilename();
                logger.info(fname);

                if (multipartFile.getSize() <= 1048576) {
                    File file = new File(PATH + fname);
                    try {
                        FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                }
            }
        } catch (Exception e) {
        }
        return new ModelAndView();
    }

    @PostMapping("/batch")
    public String handleFileUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(PATH + file.getOriginalFilename())))) {
                    byte[] bytes = file.getBytes();
                    stream.write(bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                    return "第 " + i + " 个文件上传失败 ==> "
                            + e.getMessage();
                }
            } else {
                return "第 " + i
                        + " 个文件上传失败因为文件为空";
            }
        }
        return "上传成功";
    }

    @GetMapping("/download")
    public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "application.properties";
        if (fileName != null) {
            //设置文件路径
            File file = new File(PATH + "application.properties");
            if (file.exists()) {
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);

                try (FileInputStream fis = new FileInputStream(file)) {
                    IOUtils.copy(fis, response.getOutputStream());
                    response.flushBuffer();

                    return "下载成功";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "下载失败";
    }
}
