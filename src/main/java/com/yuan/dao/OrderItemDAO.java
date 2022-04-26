package com.yuan.dao;

import com.yuan.pojo.Order;
import com.yuan.pojo.OrderItem;
import com.yuan.pojo.Product;
import com.yuan.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemDAO extends JpaRepository<OrderItem,Integer> {
    List<OrderItem> findByOrderOrderByIdDesc(Order order);
    List<OrderItem> findByProduct(Product product);
    List<OrderItem> findByUserAndOrderIsNull(User user);
}
