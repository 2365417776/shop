package com.yuan.dao;

import com.yuan.pojo.City;
import com.yuan.pojo.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityDAO extends JpaRepository<City,String> {
    List<City> findByProvince(Province province);
    City findByName(String name);
}
