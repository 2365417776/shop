package com.yuan.dao;

import com.yuan.pojo.City;
import com.yuan.pojo.County;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountyDAO extends JpaRepository<County, String> {
    List<County> findByCity(City city);
    County findByName(String name);
}
