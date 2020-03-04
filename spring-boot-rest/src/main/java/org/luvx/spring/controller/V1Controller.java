package org.luvx.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.luvx.spring.entity.Pet;
import org.luvx.spring.service.V1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author: Ren, Xie
 * @desc:
 */
@Slf4j
@RestController
@RequestMapping("/v1/pet")
public class V1Controller {
    /**
     * 新增, 可以和 update 合成一个
     *
     * @param pet
     */
    @PostMapping(value = {"/pets"})
    public void add(@RequestBody Pet pet) {
        System.out.println(pet);
    }

    /**
     * 删除
     *
     * @param petId
     */
    @DeleteMapping(value = {"/pets/{petId}"})
    public void delete(@PathVariable Long petId) {
        log.info("delete:{}", petId);
    }

    /**
     * 全部更新
     *
     * @param petId
     * @param pet
     */
    @PutMapping(value = {"/pets/{petId}"})
    public void update(@PathVariable String petId, @RequestBody Pet pet) {
        log.info("update:{}--{}", petId, pet);
    }

    /**
     * 部分修改
     *
     * @param petId
     */
    @PatchMapping(value = {"/pets/{petId}"})
    public void update1(@PathVariable Long petId, @RequestBody Pet pet) {
        log.info("update1:{}--{}", petId, pet);
    }

    /**
     * 指定查询
     *
     * @param petId
     * @return
     */
    @GetMapping(value = {"/pets/{petId}"})
    public Pet select(@PathVariable Long petId) {
        log.info("select:{}", petId);
        return null;
    }

    /**
     * 批量查询
     *
     * @return
     */
    @GetMapping(value = {"/pets"})
    public List<Pet> list(@RequestBody Pet pet) {
        log.info("select2:{}", pet);
        return null;
    }

    private V1Service service;

    @Autowired
    public void setService(V1Service service) {
        this.service = service;
    }

    /**
     * 上传, 上传多个文件时可以重复调用这个方法
     * application/octet-stream
     * multipart/form-data
     */
    @PostMapping(value = {"/pets/{petId}/upload"})
    public void upload(@PathVariable Long petId, @RequestParam("file") MultipartFile file) {
        /// List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        String fileName = service.storeFile(file);
        log.info("宠物{}增加了照片:{}", petId, fileName);
    }

    /**
     * 下载
     *
     * @param petId
     * @param request
     * @return
     */
    @GetMapping(value = {"/pets/{petId}/download"})
    public ResponseEntity<Resource> download(@PathVariable Long petId, HttpServletRequest request) {
        // 假设根据petId获取到一个文件名
        String fileName = "";
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
