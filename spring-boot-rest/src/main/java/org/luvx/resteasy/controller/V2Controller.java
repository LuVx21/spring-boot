package org.luvx.resteasy.controller;

import lombok.extern.slf4j.Slf4j;
import org.luvx.spring.entity.Pet;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import java.util.List;

/**
 * @ClassName: org.luvx.resteasy
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/8/27 16:18
 */
@Slf4j
@Component
@Path("/v2/pet")
@Consumes(MediaType.APPLICATION_JSON_UTF8_VALUE)
@Produces(MediaType.APPLICATION_JSON_UTF8_VALUE)
public class V2Controller {
    /**
     * 新增, 可以和 update 合成一个
     *
     * @param pet
     */
    @POST
    @Path("/pets")
    public void add(Pet pet) {
        System.out.println(pet);
    }

    /**
     * 删除
     *
     * @param petId
     */
    @DELETE
    @Path("/pets/{petId}")
    public void delete(@PathParam("petId") Long petId) {
        log.info("delete:{}", petId);
    }

    /**
     * 全部更新
     *
     * @param petId
     * @param pet
     */
    @PUT
    @Path("/pets/{petId}")
    public void update(@PathParam("petId") String petId, Pet pet) {
        log.info("update:{}--{}", petId, pet);
    }

    /**
     * 指定查询
     *
     * @param petId
     * @return
     */
    @GET
    @Path("/pets/{petId}")
    public Pet select(@PathParam("petId") Long petId) {
        log.info("select:{}", petId);
        return null;
    }

    /**
     * 批量查询
     *
     * @return
     */
    @GET
    @Path("/pets")
    public List<Pet> list(Pet pet) {
        log.info("select2:{}", pet);
        return null;
    }

    // private V1Service service;
    //
    // @Autowired
    // public void setService(V1Service service) {
    //     this.service = service;
    // }
    //
    // /**
    //  * 上传, 上传多个文件时可以重复调用这个方法
    //  * application/octet-stream
    //  * multipart/form-data
    //  */
    // @POST
    // @Path("/pets/{petId}/upload")
    // public void upload(@PathVariable Long petId, @RequestParam("file") MultipartFile file) {
    //     /// List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
    //     String fileName = service.storeFile(file);
    //     log.info("宠物{}增加了照片:{}", petId, fileName);
    // }
    //
    // /**
    //  * 下载
    //  *
    //  * @param petId
    //  * @param request
    //  * @return
    //  */
    // @GET
    // @Path("/pets/{petId}/download")
    // public ResponseEntity<Resource> download(@PathVariable Long petId, HttpServletRequest request) {
    //     // 假设根据petId获取到一个文件名
    //     String fileName = "";
    //     Resource resource = service.loadFile(fileName);
    //
    //     String contentType = null;
    //     try {
    //         contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    //     } catch (IOException ex) {
    //         log.info("获取类型异常");
    //     }
    //     if (contentType == null) {
    //         contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
    //     }
    //
    //     return ResponseEntity.ok()
    //             .contentType(MediaType.parseMediaType(contentType))
    //             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
    //             .body(resource);
    // }

}
