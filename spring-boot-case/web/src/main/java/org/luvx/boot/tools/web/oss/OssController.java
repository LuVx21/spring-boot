package org.luvx.boot.tools.web.oss;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.tools.dao.entity.OssFile;
import org.luvx.boot.tools.service.oss.OssService;
import org.luvx.boot.web.response.R;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/oss")
public class OssController {
    @Resource
    private OssService ossService;

    @RequestMapping(value = {"/{scope}/{fileName}"}, method = {RequestMethod.GET})
    public ResponseEntity oss(@PathVariable("scope") String scope, @PathVariable("fileName") String fileName) throws Exception {
        byte[] bytes = ossService.get(scope, fileName);

        MediaType mediaType = MediaTypeFactory.getMediaType(fileName).orElse(MediaType.ALL);
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(bytes);
    }

    @RequestMapping(value = {"delete"}, method = {RequestMethod.GET})
    public R<List<OssFile>> delete(long count) throws Exception {
        List<OssFile> ossFiles = ossService.deleteByVisitCount(count);
        return R.success(ossFiles);
    }
}
