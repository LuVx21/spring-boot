package org.luvx.tools.web;

import lombok.extern.slf4j.Slf4j;
import org.luvx.boot.web.response.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class IndexController {
    @RequestMapping(value = {"app"}, method = {RequestMethod.GET})
    public R<Object> index() {
        return R.success("ok!");
    }
}
