package com.yuan.dao;

import com.yuan.pojo.Product;
import com.yuan.pojo.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageDAO extends JpaRepository<ProductImage, Integer> {
    public List<ProductImage> findByProductAndTypeOrderByIdDesc(Product product, String type);
}
