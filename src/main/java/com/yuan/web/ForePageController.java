package com.yuan.web;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

@Controller
public class ForePageController {
    @Autowired
    private Producer captchaProducer;

    @RequestMapping("/mykaptcha")
    public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //生成验证码
        String capText = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        //向客户端写出
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

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
    @GetMapping("testAddress")
    public String test(){
        return "staticPage/othersPage/addressInfoEdit";
    }
}
