package com.yuan.tools;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
    /**
     * 将获取到的前端参数转为string类型
     */
    public static String getString(String vertyCode) {
        try {
            String result = vertyCode;
            if(result != null) {
                result = result.trim();
            }
            if("".equals(result)) {
                result = null;
            }
            return result;
        }catch(Exception e) {
            return null;
        }
    }
    /**
     * 验证码校验
     */
    public static boolean checkVerifyCode(HttpServletRequest request, String vertyCode) {
        //获取生成的验证码
        String verifyCodeExpected = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        //获取用户输入的验证码
        System.out.println(vertyCode);
        String verifyCodeActual = CodeUtil.getString(vertyCode);
        if(verifyCodeActual == null)
            return false;
        verifyCodeExpected = verifyCodeExpected.toUpperCase();
        verifyCodeActual = verifyCodeActual.toUpperCase();
        System.out.println("--------------------------------------------");
        System.out.println(verifyCodeActual + " " + verifyCodeExpected);
        if(!verifyCodeActual.equals(verifyCodeExpected)) {
            return false;
        }
        return true;
    }
}
