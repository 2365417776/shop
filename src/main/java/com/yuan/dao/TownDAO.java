package com.yuan.dao;

import com.yuan.pojo.County;
import com.yuan.pojo.Town;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TownDAO extends JpaRepository<Town,String> {
    List<Town> findByCounty(County county);
}
