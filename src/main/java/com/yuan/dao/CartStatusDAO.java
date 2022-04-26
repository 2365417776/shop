package com.yuan.dao;

import com.yuan.pojo.CartStatus;
import com.yuan.pojo.Product;
import com.yuan.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartStatusDAO extends JpaRepository<CartStatus, Integer> {
    CartStatus findByUserAndProduct(User user, Product product);
}
