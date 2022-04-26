package com.yuan.dao;

import com.yuan.pojo.Product;
import com.yuan.pojo.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewDAO extends JpaRepository<Review, Integer> {
    List<Review> findByProductOrderByIdDesc(Product product);
    int countByProduct(Product product);
}
