package com.yuan.service;

import com.yuan.dao.AddressDAO;
import com.yuan.pojo.Address;
import com.yuan.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    private AddressDAO addressDAO;
    public void set(Address address){
        addressDAO.save(address);
    }
    public Address get(int addressId){
        return addressDAO.getOne(addressId);
    }
    public List<Address> get(User user){
        return addressDAO.findByUser(user);
    }
    public void save(Address address){
        addressDAO.save(address);
    }
    public void delete(int addressId){
        addressDAO.delete(addressId);
    }
    public void update(Address address){
        addressDAO.save(address);
    }
}
