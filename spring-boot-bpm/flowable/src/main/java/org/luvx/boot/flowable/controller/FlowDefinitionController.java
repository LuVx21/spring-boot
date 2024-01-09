package org.luvx.boot.flowable.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.luvx.boot.flowable.service.FlowDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
@RestController
@RequestMapping("/flowable/def")
public class FlowDefinitionController {

    @Autowired
    private FlowDefinitionService flowDefinitionService;

    @PostMapping("/import")
    public Object importFile(@RequestParam(required = false) String name,
                             @RequestParam(required = false) String category,
                             MultipartFile file) {
        try (InputStream in = file.getInputStream()) {
            flowDefinitionService.importFile(name, category, in);
        } catch (Exception e) {
            log.error("导入失败:", e);
            return e.getMessage();
        }
        return "导入成功";
    }

    @GetMapping(value = "/list")
    public Object list(@RequestParam(defaultValue = "0") Integer pageNum, @RequestParam(defaultValue = "20") Integer pageSize) {
        return flowDefinitionService.list(pageNum, pageSize);
    }

    @GetMapping("/readXml")
    public Object readXml(@RequestParam String deployId, @RequestParam String procDefKey) {
        try {
            return flowDefinitionService.readXml(deployId, procDefKey);
        } catch (Exception e) {
            log.error("加载xml文件异常", e);
            return "加载xml文件异常";
        }
    }

    @GetMapping("/readImage")
    public void readImage(@RequestParam String deployId, @RequestParam String procDefKey, HttpServletResponse response) {
        OutputStream os = null;
        try {
            BufferedImage image = ImageIO.read(flowDefinitionService.readImage(deployId, procDefKey));
            response.setContentType("image/png");
            os = response.getOutputStream();
            if (image != null) {
                ImageIO.write(image, "png", os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    @PutMapping(value = "/updateState")
    public Object updateState(@RequestParam Integer state, @RequestParam String deployId) {
        flowDefinitionService.updateState(state, deployId);
        return "ok";
    }

    @DeleteMapping(value = "/delete")
    public Object delete(@RequestParam String deployId) {
        flowDefinitionService.delete(deployId);
        return "ok";
    }
}