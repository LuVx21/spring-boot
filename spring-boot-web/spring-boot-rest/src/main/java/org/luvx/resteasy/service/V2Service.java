package org.luvx.resteasy.service;

import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.luvx.common.property.FileProperties;
import org.luvx.resteasy.entity.DiskFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Service
public class V2Service {
    private final Path location;

    @Autowired
    public V2Service(FileProperties fileProperties) {
        location = Paths.get(fileProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(location);
        } catch (Exception ex) {
        }
    }

    /**
     * @param file
     * @return
     */
    public String storeFile(MultipartFormDataInput file) {
        Map<String, List<InputPart>> map = file.getFormDataMap();
        List<InputPart> inputParts = map.get("file");
        for (InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                String fileName = getFileName(header);
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                Path targetLocation = location.resolve(fileName);
                writeFile(bytes, targetLocation.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "xxx";
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    private void writeFile(byte[] content, String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(content);
        fop.flush();
        fop.close();
    }

    /**
     * @param file
     * @return
     */
    public String storeFile(DiskFile file) {
        String fileName = file.getFileName();
        java.nio.file.Path path = Paths.get("./upload").toAbsolutePath().normalize().resolve(fileName);
        try {
            File file1 = new File(path.toString());
            if (!file1.exists()) {
                file1.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file1);
            fos.write(file.getFileDate());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
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
