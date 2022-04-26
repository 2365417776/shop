package com.yuan.dao;

import com.yuan.pojo.Province;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProvinceDAO extends JpaRepository<Province,String>{
    Province findByName(String name);
}
