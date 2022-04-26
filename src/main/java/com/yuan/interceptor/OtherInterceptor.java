package com.yuan.interceptor;

import com.yuan.pojo.Category;
import com.yuan.pojo.OrderItem;
import com.yuan.pojo.User;
import com.yuan.service.CategoryService;
import com.yuan.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class OtherInterceptor implements HandlerInterceptor {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderItemService orderItemService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        int cartTotalItemNumber = 0;
        if(user != null){
            List<OrderItem> ois = orderItemService.listByUser(user);
            for(OrderItem oi : ois){
                cartTotalItemNumber += oi.getNumber();
            }
        }

        List<Category> cs = categoryService.list();
        String contextPath = request.getServletContext().getContextPath();
        request.getServletContext().setAttribute("categories_below_search", cs);
        session.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
        request.getServletContext().setAttribute("contextPath", contextPath);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
