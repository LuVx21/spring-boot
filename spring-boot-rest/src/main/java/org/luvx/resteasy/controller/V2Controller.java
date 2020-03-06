package org.luvx.resteasy.controller;

import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.luvx.resteasy.service.V2Service;
import org.luvx.spring.entity.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import java.io.File;
import java.io.IOException;
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

    private V2Service service;

    @Autowired
    public void setService(V2Service service) {
        this.service = service;
    }

    /**
     * 上传 // TODO NPE
     *
     * @param petId
     * @param input
     */
    @POST
    @Path("/pets/{petId}/upload")
    // @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public void upload(@PathParam("petId") Long petId, MultipartFormDataInput input) {
        System.out.println("id: " + petId);
        String fileName = service.storeFile(input);
        log.info("宠物{}增加了照片:{}", petId, fileName);
    }

    /**
     * 下载
     *
     * @param petId
     * @param request
     * @return
     */
    @GET
    @Path("/pets/{petId}/download/{fileName}")
    public Response download(@PathParam("petId") Long petId, @PathParam("fileName") String fileName,
                             @Context HttpServletRequest request) throws IOException {
        log.info("下载宠物{}的照片:{}", petId, fileName);

        if (fileName == null || fileName.isEmpty()) {
            return Response
                    .status(Status.NOT_FOUND)
                    .build();
        }
        Resource resource = service.loadFile(fileName);
        File file = resource.getFile();

        return Response.ok(file)
                .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                .header("Cache-Control", "no-cache")
                .build();
    }

}
