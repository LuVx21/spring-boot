package org.luvx.upload;

import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

/**
 * @ClassName: org.luvx.upload
 * @Description:
 * @Author: Ren, Xie
 * @Date: 2019/1/23 20:20
 */
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        path();
        System.out.println("index");
        return "index";
    }

    private void path() {
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) {
                path = new File("");
            }
            System.out.println("path:" + path.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
