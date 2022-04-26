package com.yuan.web;

import com.yuan.pojo.User;
import com.yuan.service.UserService;
import com.yuan.tools.PageNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public PageNavigator<User> list(
            @RequestParam(value = "start", defaultValue = "0")int start,
            @RequestParam(value = "size", defaultValue = "5")int size) throws Exception{
        start = start < 0 ? 0 : start;
        PageNavigator<User> page = userService.list(start, size, 5);
        return page;
    }
}
