package com.yuan.interceptor;

import com.yuan.pojo.User;
import org.apache.commons.lang.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception{
        HttpSession session = request.getSession();
        //获取工程路径名 shop
        String contextPath = session.getServletContext().getContextPath();
        //需要拦截的url
        String[] requireAuthPages = new String[]{
                "settleOrder",
                "cart",
                "alipay",
                "myorder",

                "forebuyone",
                "forebuy",
                "foreaddCart",
                "forecart",
                "foredeleteOrderItem"
        };
        String url = request.getRequestURI();
        url = StringUtils.remove(url, contextPath+"/");
        String page = url;
        if(beginWith(page, requireAuthPages)){
            User user = (User)session.getAttribute("user");
            if(user == null){
                response.sendRedirect("login");
                return false;
            }
        }
        return true;
    }
    private boolean beginWith(String page, String[] requiredAuthPages){
        boolean result = false;
        for(String requiredAuthPage : requiredAuthPages){
            if(StringUtils.startsWith(page, requiredAuthPage)){
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
