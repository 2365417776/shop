package com.yuan.service;

import com.yuan.dao.ProvinceDAO;
import com.yuan.pojo.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService {
    @Autowired
    private ProvinceDAO provinceDAO;

    public Province get(String provinceName){
        return provinceDAO.findByName(provinceName);
    }
    public List<Province> get(){
        return provinceDAO.findAll();
    }
}
