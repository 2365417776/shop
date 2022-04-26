package com.yuan.service;

import com.yuan.dao.PropertyDAO;
import com.yuan.pojo.Category;
import com.yuan.pojo.Property;
import com.yuan.tools.PageNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    @Autowired
    private PropertyDAO propertyDAO;
    @Autowired
    private CategoryService categoryService;

    public void add(Property bean){
        propertyDAO.save(bean);
    }

    public void delete(int id){
        propertyDAO.delete(id);
    }

    public Property get(int id){
        return propertyDAO.findOne(id);
    }

    public void update(Property property){
        propertyDAO.save(property);
    }

    public PageNavigator<Property> list(int cid, int start, int size, int navigatePages){
        Category category = categoryService.getOne(cid);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<Property> pageFromJPA = propertyDAO.findByCategory(category, pageable);
        return new PageNavigator<>(pageFromJPA, navigatePages);
    }

    public List<Property> listByProperty(Category category){
        return propertyDAO.findByCategory(category);
    }

}
