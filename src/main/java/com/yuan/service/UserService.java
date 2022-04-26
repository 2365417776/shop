package com.yuan.service;

import com.yuan.dao.UserDAO;
import com.yuan.pojo.User;
import com.yuan.tools.PageNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public PageNavigator<User> list(int start, int size, int navigatePages){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page page = userDAO.findAll(pageable);
        return new PageNavigator<>(page, navigatePages);
    }
    public User getByName(String name){
        return userDAO.findByName(name);
    }
    public User get(String name, String password){
        return userDAO.getByNameAndPassword(name, password);
    }
    public boolean isExist(String name){
        User user = getByName(name);
        return user != null;
    }
    public void add(User user){
        userDAO.save(user);
    }
    public User get(int uid){
        return userDAO.getOne(uid);
    }
}
