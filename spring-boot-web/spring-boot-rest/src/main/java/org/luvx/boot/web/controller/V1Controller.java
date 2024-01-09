package org.luvx.boot.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.web.entity.json.UserVo;
import org.luvx.boot.web.response.R;
import org.luvx.boot.web.service.V1Service;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/user")
public class V1Controller {
    @jakarta.annotation.Resource
    private V1Service service;

    /**
     * 新增, 可以和 update 合成一个
     */
    @PostMapping(value = {"/user"})
    public R<UserVo> add(@RequestBody UserVo user) {
        log.info("add1:{}", user);
        return R.success(user);
    }

    @PostMapping(value = {"/users"})
    public R<List<UserVo>> addBatch(@RequestBody List<UserVo> users) {
        log.info("add2:{}", users);
        return R.success(users);
    }

    /**
     * 删除
     */
    @DeleteMapping(value = {"/users/{userId}"})
    public void delete(@PathVariable Long userId) {
        log.info("delete:{}", userId);
    }

    /**
     * 全部更新
     */
    @PutMapping(value = {"/users/{userId}"})
    public void update1(@PathVariable String userId, @RequestBody UserVo user) {
        log.info("update1:{}--{}", userId, user);
    }

    /**
     * 部分修改
     */
    @PatchMapping(value = {"/users/{userId}"})
    public void update2(@PathVariable Long userId, @RequestBody UserVo user) {
        log.info("update1:{}--{}", userId, user);
    }

    /**
     * 指定查询
     */
    @GetMapping(value = {"/users/{userId}"})
    public R<UserVo> select(@PathVariable Long userId) {
        log.info("select1:{}", userId);
        return R.success(UserVo.create(1));
    }

    /**
     * 批量查询
     */
    @GetMapping(value = {"/users"})
    public List<UserVo> list(@RequestBody UserVo user) {
        log.info("select2:{}", user);
        return null;
    }

    /**
     * 上传, 上传多个文件时可以重复调用这个方法
     * application/octet-stream
     * multipart/form-data
     * http://127.0.0.1:8090/v1/user/users/1/upload
     */
    @PostMapping(value = {"/users/{userId}/upload"})
    public void upload(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        /// List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String fileName = service.storeFile(file);
        log.info("用户{}增加了照片:{}", userId, fileName);
    }

    /**
     * 下载
     * http://127.0.0.1:8090/v1/user/users/1/download/ud
     */
    @GetMapping(value = {"/users/{userId}/download/{fileName:.*}"})
    public ResponseEntity<Resource> download(@PathVariable Long userId, @PathVariable String fileName,
                                             HttpServletRequest request) {
        log.info("下载用户{}的照片:{}", userId, fileName);
        Resource resource = service.loadFile(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            log.info("获取类型异常");
        }
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
