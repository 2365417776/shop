package com.yuan.dao;

import com.yuan.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findByName(String name);
    User getByNameAndPassword(String name, String password);
    User getByPhoneAndPassword(String phone, String password);
    User getByEmailAndPassword(String email, String password);
    User getByPhone(String phone);
}
