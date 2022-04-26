package com.yuan.service;

import com.yuan.dao.PropertyValueDAO;
import com.yuan.pojo.Product;
import com.yuan.pojo.Property;
import com.yuan.pojo.PropertyValue;
import com.yuan.tools.PageNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueService {
    @Autowired
    private PropertyValueDAO propertyValueDAO;
    @Autowired
    private PropertyService propertyService;

    public void update(PropertyValue propertyValue){
        propertyValueDAO.save(propertyValue);
    }

    public void init(Product product){
        List<Property> properties = propertyService.listByProperty(product.getCategory());
        for(Property property : properties){
            PropertyValue propertyValue = getByPropertyAndProduct(product, property);
            if(propertyValue == null){
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                propertyValueDAO.save(propertyValue);
            }
        }
    }

    public PropertyValue getByPropertyAndProduct(Product product, Property property){
        return propertyValueDAO.getByPropertyAndProduct(property, product);
    }

    public PageNavigator<PropertyValue> list(Product product, int start, int size, int navigatePages){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        Page<PropertyValue> page = propertyValueDAO.findByProductOrderByIdDesc(product, pageable);
        return new PageNavigator<>(page, navigatePages);
    }
    public List<PropertyValue> list(Product product){
        return propertyValueDAO.findByProductOrderByIdDesc(product);
    }
}
