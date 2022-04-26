package com.yuan.service;

import com.yuan.dao.OrderDAO;
import com.yuan.pojo.Order;
import com.yuan.pojo.OrderItem;
import com.yuan.pojo.User;
import com.yuan.tools.PageNavigator;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    public static final String waitPay = "waitPay";
    public static final String waitDelivery = "waitDelivery";
    public static final String waitConfirm = "waitConfirm";
    public static final String waitReview = "waitReview";
    public static final String finish = "finish";
    public static final String delete = "delete";

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private OrderItemService orderItemService;

    public PageNavigator<Order> list(int start, int size, int navigatePages){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page page = orderDAO.findAll(pageable);
        return new PageNavigator<>(page, navigatePages);
    }

    public void removeOrderFromOrderItem(List<Order> orders){
        for (Order order:orders){
            removeOrderFromOrderItem(order);
        }
    }
    public void removeOrderFromOrderItem(Order order){
        List<OrderItem> ordersItems = order.getOrderItemList();
        for (OrderItem orderItem:ordersItems){
            orderItem.setOrder(null);
        }
    }

    public Order get(int oid){
        return orderDAO.getOne(oid);
    }

    public void update(Order bean){
        orderDAO.save(bean);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    public float add(Order order, List<OrderItem> ois){
        float total = 0;
        add(order);
        if(false)
            throw new RuntimeException();
        for (OrderItem oi : ois){
            oi.setOrder(order);
            orderItemService.update(oi);
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
        }
        return total;
    }
    public void add(Order order){
        orderDAO.save(order);
    }
    public List<Order> listByUserWithoutDelete(User user){
        List<Order> orders = listByUserAndNotDeleted(user);
        orderItemService.fill(orders);
        return orders;
    }
    public List<Order> listByUserAndNotDeleted(User user){
        return orderDAO.findByUserAndStatusNotOrderByIdDesc(user, OrderService.delete);
    }
    public void cacl(Order order){
        List<OrderItem> orderItems = order.getOrderItemList();
        float total = 0;
        for(OrderItem orderItem : orderItems){
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }
        order.setTotal(total);
    }
}
