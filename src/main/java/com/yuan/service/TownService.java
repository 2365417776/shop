package com.yuan.service;

import com.yuan.dao.TownDAO;
import com.yuan.pojo.County;
import com.yuan.pojo.Town;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TownService {
    @Autowired
    private TownDAO townDAO;
    @Autowired
    private CountyService countyService;
    public List<Town> get(County county){
        return townDAO.findByCounty(county);
    }
}
