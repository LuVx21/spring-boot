package org.luvx.boot.tools.web.oss;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.tools.service.oss.OssService;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/oss")
public class OssController {
    @Resource
    private OssService imgService;

    @RequestMapping(value = {"/{scope}/{fileName}"}, method = {RequestMethod.GET})
    public ResponseEntity oss(@PathVariable("scope") String scope, @PathVariable("fileName") String fileName) throws Exception {
        byte[] bytes = imgService.img(scope, fileName);

        MediaType mediaType = MediaTypeFactory.getMediaType(fileName).orElse(MediaType.ALL);
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(bytes);
    }
}
