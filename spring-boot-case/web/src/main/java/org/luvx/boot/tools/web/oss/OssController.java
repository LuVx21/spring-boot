package org.luvx.boot.tools.web.oss;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.tools.dao.entity.OssFile;
import org.luvx.boot.tools.service.oss.OssService;
import org.luvx.boot.web.response.R;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        MediaType mediaType = MediaTypeFactory.getMediaType(fileName).orElse(MediaType.APPLICATION_OCTET_STREAM);
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(bytes);
    }

    @RequestMapping(value = {"delete"}, method = {RequestMethod.GET})
    public R<List<Long>> delete(@RequestParam(value = "count", required = false) Long count,
                                   @RequestParam(value = "fileName", required = false) String fileName) throws Exception {
        List<Long> ossFiles = ossService.deleteByVisitCount(count, fileName);
        return R.success(ossFiles);
    }
}
