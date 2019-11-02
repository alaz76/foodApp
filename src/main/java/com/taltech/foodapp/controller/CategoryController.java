package com.taltech.foodapp.controller;

import com.taltech.foodapp.dao.Category;
import com.taltech.foodapp.dao.User;
import com.taltech.foodapp.dto.CategoryRequestObj;
import com.taltech.foodapp.dto.CategoryResponseObj;
import com.taltech.foodapp.exceptions.AuthenticationException;
import com.taltech.foodapp.exceptions.CategoryException;
import com.taltech.foodapp.exceptions.ProductException;
import com.taltech.foodapp.exceptions.UserException;
import com.taltech.foodapp.service.CategoryService;
import com.taltech.foodapp.util.Constants;
import com.taltech.foodapp.util.TokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
class CategoryController extends TokenValidator {

    private Logger log= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CategoryService categoryService;

    /**
     * This endpoint returns a list of product categories to the user.
     * @return
     */
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponseObj>> getAllCategories(){
        log.info("Finding all categories.");
        return null;
    }

    /**
     * This endpoint returns a single category using the category id.
     * @param category_id
     * @return
     * @throws CategoryException
     */
    @GetMapping("/categories/{category_id}")
    public ResponseEntity<CategoryResponseObj> getCategory(@PathVariable int category_id) throws CategoryException {
        log.info("Get a category having category id: {}", category_id);
        return null;
    }

    /**
     * This endpoint creates a category by ADMIN users only.
     * @param request
     * @param categoryRequestObj
     * @return
     * @throws AuthenticationException
     * @throws UserException
     * @throws ProductException
     */
    @PostMapping("/categories")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Category> create(HttpServletRequest request, @RequestBody CategoryRequestObj categoryRequestObj) throws AuthenticationException, UserException, ProductException {
        log.info("Attempting authentication against the token received.");
        return null;
    }

    /**
     * This endpoint deletes a category by ADMIN users only
     * @param request
     * @param categoryId
     * @return
     * @throws AuthenticationException
     * @throws UserException
     */
    @DeleteMapping("/categories/{categoryId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Integer> delete(HttpServletRequest request, @PathVariable int categoryId) throws AuthenticationException, UserException,CategoryException {
        log.info("Attempting authentication against the token received.");
        return null;
    }


    /**
     * This endpoint updates a category by ADMIN users only.
     * @param request
     * @param categoryId
     * @return
     * @throws AuthenticationException
     * @throws UserException
     */
    @PutMapping("/categories/{categoryId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Category> update(HttpServletRequest request,
                                           @PathVariable int categoryId,
                                           @RequestBody CategoryRequestObj categoryRequestObj) throws AuthenticationException, UserException, CategoryException {
        log.info("Attempting authentication against the token received.");
        return null;
    }

}
