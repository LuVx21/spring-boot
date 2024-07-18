package org.luvx.boot.web.controller;

import org.luvx.boot.web.response.R;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/param")
public class ParamController {

    @GetMapping("a/{ids}")
    public R<Object> a(@PathVariable Collection<Long> ids) {
        return R.success(ids);
    }

    @GetMapping("b")
    public R<Object> b(@RequestParam List<Long> ids) {
        return R.success(ids);
    }

    @GetMapping("c")
    public R<Object> c(@RequestParam Map<String, Long> ids) {
        return R.success(ids);
    }

    @PostMapping("d")
    public R<Object> d(@RequestBody Map<String, Object> ids) {
        return R.success(ids);
    }
}
