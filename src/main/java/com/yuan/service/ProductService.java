package com.yuan.service;

import com.yuan.dao.ProductDAO;
import com.yuan.pojo.Category;
import com.yuan.pojo.Product;
import com.yuan.tools.PageNavigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {
    @Autowired
    private ProductDAO productDAO;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductImageService productImageService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ReviewService reviewService;

    public void add(Product bean){
        productDAO.save(bean);
    }

    public void delete(int id){
        productDAO.delete(id);
    }

    public Product get(int id){
        return productDAO.findOne(id);
    }

    public void update(Product bean){
        productDAO.save(bean);
    }

    public PageNavigator<Product> list(int cid, int start, int size, int navigatePages){
        Category category = categoryService.getOne(cid);
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size ,sort);
        Page<Product> page = productDAO.findByCategory(category, pageable);
        return new PageNavigator<>(page, navigatePages);
    }
//    以分页方式列出所有产品
    public PageNavigator<Product> list(int start, int size, int navigatePages){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size ,sort);
        Page<Product> productAll = null;
        productAll = productDAO.findAll(pageable);
        PageNavigator<Product> page = new PageNavigator<>(productAll,navigatePages);
        Set<Product> sets = new HashSet<>();
        assert false;
        sets.addAll(page.getContent());
        List<Product> lists = new ArrayList<>();
        lists.addAll(sets);
        fillProduct(lists);
        page.setContent(lists);
        return page;
    }
//    为分类填充产品
    public void fill(List<Category> categorys){
        for (Category category : categorys){
            fill(category);
        }
    }
    public void fill(Category category){
        List<Product> products = listByCategory(category);
        productImageService.setFirstProductImages(products);
        category.setProducts(products);
    }
    //为每一个产品填充图片
    public void fillProduct(List<Product> products){
        productImageService.setFirstProductImages(products);
    }
    public void fillByRow(List<Category> categories){
        int productNumberEachRow = 8;
        for (Category category : categories){
            List<Product> products = category.getProducts();
            List<List<Product>> productsByRow = new ArrayList<>();
            for (int i = 0;i < products.size();i += productNumberEachRow){
                int size = i + productNumberEachRow;
                size = size > products.size() ? products.size():size;
                List<Product> productsOfEachRow = products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }
    public List<Product> listByCategory(Category category){
        return productDAO.findByCategoryOrderById(category);
    }
    public void setSaleAndReviewNumber(Product product){
        int saleCount = orderItemService.getSaleCount(product);
        product.setSaleCount(saleCount);
        int reviewCount = reviewService.getCount(product);
        product.setReviewCount(reviewCount);
    }
    public void setSaleAndReviewNumber(List<Product> products){
        for (Product product : products){
            setSaleAndReviewNumber(product);
        }
    }
    public List<Product> search(String keyword, int start, int size){
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = new PageRequest(start, size, sort);
        List<Product> products = productDAO.findByNameLike("%"+keyword+"%", pageable);
        return products;
    }
}
