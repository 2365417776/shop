package com.yuan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminPageController {
    @RequestMapping(value = "/index")
    public ModelAndView index(HttpServletRequest request){
        String page = request.getParameter("page");
        String content = request.getParameter("content");
        // 不带参数请求
        if (page == null || page.equals("") || content == null || content.equals("")) {
            page = "include/back_stage_category";
            content = "category";
        }
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("page", page);
        modelAndView.addObject("content", content);
        return modelAndView;
    }
}
