package com.yuan.service;

import com.yuan.dao.CityDAO;
import com.yuan.pojo.City;
import com.yuan.pojo.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private ProvinceService provinceService;

    public List<City> get(Province province){
        return cityDAO.findByProvince(province);
    }
    public City get(String cityName){
        return cityDAO.findByName(cityName);
    }
}
