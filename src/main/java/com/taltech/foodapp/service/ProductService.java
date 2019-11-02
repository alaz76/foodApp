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
        return productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
    }

    public ResponseObj getAllProductsByCategory(int categoryId) {
        Optional<List<Product>> products= productRepository.findAllByCategory(categoryId);
        ResponseObj responseObj = new ResponseObj();
        responseObj.setRows(products.orElseGet(LinkedList::new));
        return responseObj;
    }

    public Product save(ProductRequestObj productRequestObj) throws CategoryException {
        Category category= categoryRepository.findById(productRequestObj.getCategoryId())
                .orElseThrow(() -> new CategoryException(Constants.CAT_01_CODE, HttpStatus.NOT_FOUND.value(), Constants.CAT_01_MESSAGE, "category_id"));
        Product product= new Product();
        product.setCategory(category);
        product.setDiscountedPrice(productRequestObj.getDiscountedPrice());
        product.setName(productRequestObj.getName());
        product.setDescription(productRequestObj.getDescription());
        product.setPrice(productRequestObj.getPrice());
        return productRepository.save(product);
    }

    public void delete(int productId) {
        productRepository.deleteById(productId);
    }

    public Product update(int productId, ProductRequestObj productRequestObj) throws ProductException, CategoryException {
        Product product= productRepository.findById(productId)
                .orElseThrow(() -> new ProductException(Constants.PROD_01_CODE, HttpStatus.NOT_FOUND.value(), Constants.PROD_01_MESSAGE, "productId"));
        product.setPrice(productRequestObj.getPrice());
        product.setDescription(productRequestObj.getDescription());
        product.setName(productRequestObj.getName());
        product.setDiscountedPrice(productRequestObj.getDiscountedPrice());
        if(productRequestObj.getCategoryId() != 0){
            product.setCategory(categoryRepository.findById(productRequestObj.getCategoryId())
                    .orElseThrow(() -> new CategoryException(Constants.CAT_01_CODE, HttpStatus.NOT_FOUND.value(), Constants.CAT_01_MESSAGE, "categoryId")));
        }

        return productRepository.save(product);
    }
}
