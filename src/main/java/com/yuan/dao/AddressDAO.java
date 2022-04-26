package com.yuan.dao;

import com.yuan.pojo.Address;
import com.yuan.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressDAO extends JpaRepository<Address, Integer> {
    List<Address> findByUser(User user);
}
