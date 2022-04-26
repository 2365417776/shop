package com.yuan.service;

import com.yuan.dao.OrderItemDAO;
import com.yuan.pojo.Order;
import com.yuan.pojo.OrderItem;
import com.yuan.pojo.Product;
import com.yuan.pojo.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemDAO orderItemDAO;
    @Autowired
    private ProductImageService productImageService;

    public void update(OrderItem orderItem){
        orderItemDAO.save(orderItem);
    }
    public void add(OrderItem orderItem){
        orderItemDAO.save(orderItem);
    }
    public OrderItem get(int id){
        return orderItemDAO.getOne(id);
    }
    public void delete(int id){
        orderItemDAO.delete(id);
    }
    public void fill(List<Order> orders){
        for (Order order:orders){
            fill(order);
        }
    }
    public void fill(Order order){
        List<OrderItem> orderItems = listByOrder(order);
        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi : orderItems){
            total += oi.getNumber()*oi.getProduct().getPromotePrice();
            totalNumber += oi.getNumber();
            productImageService.setFirstProductImage(oi.getProduct());
        }
        order.setTotal(total);
        order.setOrderItemList(orderItems);
        order.setTotalNumber(totalNumber);
    }
    public List<OrderItem> listByOrder(Order order){
        return orderItemDAO.findByOrderOrderByIdDesc(order);
    }

    public int getSaleCount(Product product){
        List<OrderItem> ois = listByProduct(product);
        int result = 0;
        for (OrderItem oi : ois){
            if(oi.getProduct() != null){
                if(oi.getOrder() != null && oi.getOrder().getPayDate() != null){
                    result += oi.getNumber();
                }
            }
        }
        return result;
    }
    public List<OrderItem> listByProduct(Product product){
        return orderItemDAO.findByProduct(product);
    }
    public List<OrderItem> listByUser(User user){
//        OrderIsNull表示当确认订单后便查不到，购物车对应项变空
        return orderItemDAO.findByUserAndOrderIsNull(user);
    }
}
