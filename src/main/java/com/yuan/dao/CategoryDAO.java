package com.yuan.dao;

import com.yuan.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category, Integer> {
}
