package com.yuan.web;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    @GetMapping("addPhone")
    public String addPhone(Model model){
        model.addAttribute("title","添加手机");
        return "staticPage/otherMainPage/PhoneEmailEditAdd";
    }
    @GetMapping("editPhone2")
    public String editPhone2(Model model){
        model.addAttribute("title","修改手机");
        return "staticPage/otherMainPage/PhoneEmailEditAdd";
    }
    @GetMapping("addEmail2")
    public String addEmail2(Model model){
        model.addAttribute("title","添加邮箱");
        return "staticPage/otherMainPage/PhoneEmailEditAdd";
    }
    @GetMapping("editEmail2")
    public String editEmail2(Model model){
        model.addAttribute("title","修改邮箱");
        return "staticPage/otherMainPage/PhoneEmailEditAdd";
    }
    @GetMapping("editPassword2")
    public String editPassword2(Model model){
        model.addAttribute("title","修改密码");
        return "staticPage/otherMainPage/PhoneEmailEditAdd";
    }
    @GetMapping("forgetPassword2")
    public String forgetPassword2(Model model){
        model.addAttribute("title","忘记密码");
        return "staticPage/otherMainPage/PhoneEmailEditAdd";
    }
    @GetMapping("editPhone")
    public String editPhone(Model model){
        model.addAttribute("title","修改手机");
        return "staticPage/otherMainPage/verifyInfo";
    }
    @GetMapping("addEmail")
    public String addEmail(Model model){
        model.addAttribute("title","添加邮箱");
        return "staticPage/otherMainPage/verifyInfo";
    }
    @GetMapping("editEmail")
    public String editEmail(Model model){
        model.addAttribute("title","修改邮箱");
        return "staticPage/otherMainPage/verifyInfo";
    }
    @GetMapping("editPassword")
    public String editPassword(Model model){
        model.addAttribute("title","修改密码");
        return "staticPage/otherMainPage/verifyInfo";
    }
    @GetMapping("forgetPassword")
    public String forgetPassword(Model model){
        model.addAttribute("title","忘记密码");
        return "staticPage/otherMainPage/verifyInfo";
    }
    @GetMapping("addPhoneSuccess")
    public String addPhoneSuccess(Model model){
        model.addAttribute("title","添加手机");
        return "staticPage/otherMainPage/editSafeInfoSuccess";
    }
    @GetMapping("editPhoneSuccess")
    public String editPhoneSuccess(Model model){
        model.addAttribute("title","修改手机");
        return "staticPage/otherMainPage/editSafeInfoSuccess";
    }
    @GetMapping("addEmailSuccess")
    public String addEmailSuccess(Model model){
        model.addAttribute("title","添加邮箱");
        return "staticPage/otherMainPage/editSafeInfoSuccess";
    }
    @GetMapping("editEmailSuccess")
    public String editEmailSuccess(Model model){
        model.addAttribute("title","修改邮箱");
        return "staticPage/otherMainPage/editSafeInfoSuccess";
    }
    @GetMapping("editPasswordSuccess")
    public String editPasswordSuccess(Model model){
        model.addAttribute("title","修改密码");
        return "staticPage/otherMainPage/editSafeInfoSuccess";
    }
}
