package com.yuan.dao;

import com.yuan.pojo.Order;
import com.yuan.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order, Integer>{
    public List<Order> findByUserAndStatusNotOrderByIdDesc(User user, String status);
}
