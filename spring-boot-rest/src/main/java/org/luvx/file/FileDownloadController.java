package org.luvx.file;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author
 * @ClassName: org.luvx
 * @Description:
 */
@RestController
@Path("/file1")
public class FileDownloadController {
    private static final byte[] UTF8_BOM = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
    private static final String FAV_ICO  = "fav.ico";

    /**
     * @RequestMapping(value = "/download/", method = RequestMethod.POST, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
     * public ResponseEntity<byte[]> downloadFile(@RequestBody FileInfo entity) {
     * return new ResponseEntity(null, null);
     * }
     */

    @GET
    @Path("/getFile")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile(String fileName) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        ZipOutputStream zos = new ZipOutputStream(bos);
        try {
            zos.putNextEntry(new ZipEntry("utf-8.txt"));
            zos.write(UTF8_BOM);
            zos.write("这是一段UTF-8文本".getBytes("UTF-8"));
            zos.closeEntry();
            zos.flush();
            zos.finish();
            return Response.ok(bos.toByteArray(), "application/zip")
                    .header("Content-Disposition", "attachment; filename=demo2.zip")
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
            }
        }
    }


    @GET
    @Path("/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getMyFile() throws IOException {
        File file = new File("E:\\temp\\upload\\application.properties");
        long fileLength = file.length();
        Response.ResponseBuilder responseBuilder = Response.ok(file);
        responseBuilder.type("application/x-msdownload");
        responseBuilder.header("Content-Disposition", "attachment; filename=" + URLEncoder.encode("application.properties", "UTF-8"));
        responseBuilder.header("Content-Length", Long.toString(fileLength));
        Response response = responseBuilder.build();
        return response;
    }

    @GET
    @Path("/d")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@Context HttpServletResponse response) {
        File file = new File("E:\\temp\\upload\\application.properties");

        if (!file.exists()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        String nFileName = null;
        try {
            nFileName = URLEncoder.encode("application.properties", "UTF-8");
            response.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return Response.ok(file).header("Content-disposition", "attachment;filename=" + nFileName + ";filename*=utf-8''" + nFileName).header("Cache-Control", "no-cache").build();
    }
}
