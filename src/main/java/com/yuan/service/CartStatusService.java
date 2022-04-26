package com.yuan.service;

import com.yuan.dao.CartStatusDAO;
import com.yuan.pojo.CartStatus;
import com.yuan.pojo.Product;
import com.yuan.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartStatusService {
    @Autowired
    private CartStatusDAO cartStatusDAO;

    public void set(CartStatus cartStatus){
        cartStatusDAO.save(cartStatus);
    }
    public CartStatus get(int sid){
        return cartStatusDAO.getOne(sid);
    }
    public CartStatus get(User user, Product product){
        return cartStatusDAO.findByUserAndProduct(user, product);
    }
}
