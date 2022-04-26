package com.yuan.service;

import com.yuan.dao.CityDAO;
import com.yuan.dao.CountyDAO;
import com.yuan.pojo.City;
import com.yuan.pojo.County;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountyService {
    @Autowired
    private CountyDAO countyDAO;
    @Autowired
    private CityService cityService;
    public List<County> get(City city){
        return countyDAO.findByCity(city);
    }
    public County get(String countyName){
        return countyDAO.findByName(countyName);
    }
}
