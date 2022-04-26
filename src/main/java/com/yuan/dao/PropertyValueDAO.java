package com.yuan.dao;

import com.yuan.pojo.Product;
import com.yuan.pojo.Property;
import com.yuan.pojo.PropertyValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyValueDAO extends JpaRepository<PropertyValue, Integer> {
    Page<PropertyValue> findByProductOrderByIdDesc(Product product, Pageable pageable);
    PropertyValue getByPropertyAndProduct(Property property, Product product);
    List<PropertyValue> findByProductOrderByIdDesc(Product product);
}
