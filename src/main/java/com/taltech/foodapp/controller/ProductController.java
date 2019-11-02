package com.taltech.foodapp.controller;

import com.taltech.foodapp.dao.User;
import com.taltech.foodapp.dao.Product;
import com.taltech.foodapp.dto.ProductRequestObj;
import com.taltech.foodapp.dto.ResponseObj;
import com.taltech.foodapp.exceptions.AuthenticationException;
import com.taltech.foodapp.exceptions.CategoryException;
import com.taltech.foodapp.exceptions.ProductException;
import com.taltech.foodapp.exceptions.UserException;
import com.taltech.foodapp.util.Constants;
import com.taltech.foodapp.util.TokenValidator;
import com.taltech.foodapp.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
class ProductController extends TokenValidator {

    private Logger log= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProductService productService;

    /**
     * This endpoint returns a list of products in the database.
     * @return
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAll(){
        log.info("Fetching all products from the data store.");
        return null;
    }

    /**
     * This endpoint creates a product by user with role ADMIN.
     * @param productRequestObj
     * @return
     * @throws CategoryException
     */
    @PostMapping("/products")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Product> create(HttpServletRequest request, @RequestBody ProductRequestObj productRequestObj) throws CategoryException, AuthenticationException, UserException {
        log.info("Authorizing the request with received token");
        log.info("Creating a product.");
        return null;

    }

    /**
     * This endpoint returns a single product object using the product id.
     * @param product_id
     * @return
     */
    @GetMapping("/products/{product_id}")
    public ResponseEntity<Product> getProduct(@PathVariable int product_id){
        log.info("Find a product with productId, {}", product_id);
        return null;
    }

    /**
     * This endpoint should return list of products in a category using the category id in the request params.
     * @param category_id
     * @return
     */
    @GetMapping("/products/inCategory/{category_id}")
    public ResponseEntity<ResponseObj> getAllProductsInCategory(
            @PathVariable int category_id){

        log.info("Fetching all products in a category with category id, {}", category_id);
        return null;
    }

    /**
     * This endpoint deletes a product by productid, by ADMIN users only
     * @param request
     * @param productId
     * @return
     * @throws AuthenticationException
     * @throws UserException
     */
    @DeleteMapping("/products/{productId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity delete(HttpServletRequest request, @PathVariable int productId) throws AuthenticationException, UserException {
        log.info("Authenticating against the provided token.");
        log.info("Attempting to delete a product.");
        return null;
    }


    /**
     * This endpoint updates a product by ADMIN users only.
     * @param request
     * @param productId
     * @param productRequestObj
     * @return
     */
    @PutMapping("/products/{productId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Product> update(HttpServletRequest request,
                                          @PathVariable int productId,
                                          @RequestBody ProductRequestObj productRequestObj) throws AuthenticationException, UserException, ProductException, CategoryException {

        log.info("Authenticating against the provided token.");
        return null;
    }
}
