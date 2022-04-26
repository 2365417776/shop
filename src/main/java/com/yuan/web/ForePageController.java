package com.yuan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ForePageController {
    @GetMapping(value = "/")
    public String index(){
        return "redirect:home";
    }
    @GetMapping(value = "/home")
    public String home(){
        return "staticPage/home";
    }
    @GetMapping(value = "/register")
    public String register(){
        return "staticPage/otherMainPage/register";
    }
    @GetMapping(value = "registerSuccess")
    public String registerSuccess(){
        return "staticPage/otherMainPage/registerSuccess";
    }
    @GetMapping(value = "login")
    public String login(){
        return "staticPage/otherMainPage/logging";
    }
    @GetMapping(value = "product")
    public String product(){
        return "staticPage/otherMainPage/products";
    }
    @GetMapping(value = "category")
    public String category(){
        return "staticPage/otherMainPage/category";
    }
    @GetMapping("search")
    public String searchResult(){
        return "staticPage/otherMainPage/search";
    }
    @GetMapping("settleOrder")
    public String settleOrder(){
        return "staticPage/otherMainPage/settleAccount";
    }
    @GetMapping("/cart")
    public String cart(){
        return "staticPage/otherMainPage/cargo";
    }
    @GetMapping("/alipay")
    public String pay(){
        return "staticPage/otherMainPage/payMoney";
    }
    @GetMapping("/paySuccess")
    public String paySuccess(){
        return "staticPage/otherMainPage/paySuccess";
    }
    @GetMapping("myorder")
    public String myorder(){
        return "staticPage/otherMainPage/order";
    }
    @GetMapping("confirmReceive")
    public String confirmReceive(){
        return "staticPage/otherMainPage/confirmReceipt";
    }
    @GetMapping("receiptSuccess")
    public String receiptSuccess(){
        return "staticPage/otherMainPage/receiptSuccess";
    }
    @GetMapping("review")
    public String review(){
        return "staticPage/otherMainPage/review";
    }
    @GetMapping("userInfo")
    public String userInfo(){
        return "staticPage/otherMainPage/myUserInfo";
    }
}
