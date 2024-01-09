
package org.luvx.boot.tools.web;

import jakarta.annotation.Resource;
import org.luvx.boot.tools.dao.entity.User;
import org.luvx.boot.tools.dao.mapper.UserMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @PostMapping
    public ModelAndView create(User user, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("user/form", "formErrors", result.getAllErrors());
        }
        Long id = user.getId();
        if (id != null) {
            userMapper.updateByPrimaryKey(user);
        } else {
            userMapper.insert(user);
        }
        redirect.addFlashAttribute("globalMessage", "添加用户成功");
        if (id != null) {
            return new ModelAndView("redirect:/user/{user.id}", "user.id", id);
        }
        List<User> users = userMapper.selectList(new User());
        return new ModelAndView("user/list", "users", users);
    }

    @GetMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        userMapper.deleteByPrimaryKey(id);
        List<User> users = userMapper.selectList(new User());
        return new ModelAndView("user/list", "users", users);
    }

    @GetMapping(value = "modify/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id) {
        User user = userMapper.selectByPrimaryKey(id).get();
        return new ModelAndView("user/form", "user", user);
    }

    @GetMapping
    public ModelAndView list() {
        List<User> users = userMapper.selectList(new User());
        return new ModelAndView("user/list", "users", users);
    }

    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        User user = userMapper.selectByPrimaryKey(id).get();
        return new ModelAndView("user/view", "user", user);
    }

    @GetMapping("form")
    public String createForm(@ModelAttribute User user) {
        return "user/form";
    }

}
