package com.taltech.foodapp.service;

import com.taltech.foodapp.dao.Category;
import com.taltech.foodapp.dao.Product;
import com.taltech.foodapp.dto.ProductRequestObj;
import com.taltech.foodapp.dto.ResponseObj;
import com.taltech.foodapp.exceptions.CategoryException;
import com.taltech.foodapp.exceptions.ProductException;
import com.taltech.foodapp.repository.CategoryRepository;
import com.taltech.foodapp.repository.ProductRepository;
import com.taltech.foodapp.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> findAllProducts(){
        return (List<Product>) productRepository.findAll();
    }

    public Product findProductById(int productId) {
        return null;
    }

    public ResponseObj getAllProductsByCategory(int categoryId) {
        return null;
    }

    public Product save(ProductRequestObj productRequestObj) throws CategoryException {
        return null;
    }

    public void delete(int productId) {

    }

    public Product update(int productId, ProductRequestObj productRequestObj) throws ProductException, CategoryException {
        return null;
    }
}
