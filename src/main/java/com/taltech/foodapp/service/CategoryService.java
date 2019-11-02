package com.taltech.foodapp.service;

import com.taltech.foodapp.dao.Category;
import com.taltech.foodapp.dao.Product;
import com.taltech.foodapp.dto.CategoryRequestObj;
import com.taltech.foodapp.dto.CategoryResponseObj;
import com.taltech.foodapp.exceptions.CategoryException;
import com.taltech.foodapp.repository.ProductRepository;
import com.taltech.foodapp.util.Constants;
import com.taltech.foodapp.repository.CategoryRepository;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    @Autowired
    private
    CategoryRepository categoryRepository;

    @Autowired
    private
    ProductRepository productRepository;


    public List<CategoryResponseObj> findAll() {

        return null;
    }

    public CategoryResponseObj findById(int categoryId) throws CategoryException {

        return null;
    }

    public CategoryResponseObj findByProduct(int productId) throws CategoryException {

        return null;
    }

    public Category save(CategoryRequestObj categoryRequestObj) {
        return null;
    }

    public void delete(int categoryId) throws CategoryException {
    }

    public Category update(int categoryId, CategoryRequestObj categoryRequestObj) throws CategoryException {
        return null;
    }
}
