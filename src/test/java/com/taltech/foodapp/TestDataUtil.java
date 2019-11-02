package com.taltech.foodapp;

import com.taltech.foodapp.dao.Category;
import com.taltech.foodapp.dao.User;
import com.taltech.foodapp.dao.Product;
import com.taltech.foodapp.dto.CategoryRequestObj;
import com.taltech.foodapp.dto.UserRequestObj;
import com.taltech.foodapp.dto.UserResponseObj;
import com.taltech.foodapp.dto.ProductRequestObj;
import com.taltech.foodapp.util.Constants;

import java.util.*;

public abstract class TestDataUtil {

    protected List<UserRequestObj> getUserRequestObj(){
        List<UserRequestObj> userRequestObjs = new ArrayList<>();

        UserRequestObj userRequestObj = new UserRequestObj();
        userRequestObj.setName("Anupam");
        userRequestObj.setPassword("12345pwd");
        userRequestObj.setEmail("anupam@gmail.com");
        userRequestObj.setAddress1("Estonia");
        userRequestObj.setCreditCard(new StringBuilder("xxxxxxxxxxxxxxxx"));
        userRequestObjs.add(userRequestObj);

        UserRequestObj userRequestObj1 = new UserRequestObj();
        userRequestObj1.setName("Anupam");
        userRequestObj1.setPassword("12345pwd");
        userRequestObj1.setEmail("anupam12@gmail.com");
        userRequestObj1.setAddress1("Estonia");
        userRequestObj1.setCreditCard(new StringBuilder("xxxxxxxxxxxxxxxx"));
        userRequestObjs.add(userRequestObj1);

        return userRequestObjs;
    }

    protected List<UserResponseObj> getUserResponseObj(){
        List<UserResponseObj> userResponseObjs = new ArrayList<>();
        User user = new User();
        user.setUserId(1);
        user.setName("Anupam");
        user.setEmail("anupam@gmail.com");
        user.setPassword("12345pwd");
        user.setAddress1("Estonia");
        UserResponseObj userRequestObj= new UserResponseObj();
        userRequestObj.setUser(user);
        userResponseObjs.add(userRequestObj);

        User user1 = new User();
        user1.setUserId(1);
        user1.setName("Anupam");
        user1.setEmail("anupam12@gmail.com");
        user1.setPassword("12345pwd");
        user1.setAddress1("Estonia");
        UserResponseObj userRequestObj1= new UserResponseObj();
        userRequestObj1.setUser(user1);
        userResponseObjs.add(userRequestObj1);
        return userResponseObjs;
    }

    protected UserRequestObj getUpdateUserPaylod(){
        UserRequestObj userRequestObj = new UserRequestObj();
        userRequestObj.setUserId(1);
        userRequestObj.setName("Anupam Rakshit");
        userRequestObj.setEmail("anupam@gmail.com");
        userRequestObj.setAddress1("Estonia updated");
        return userRequestObj;
    }
    protected UserResponseObj getUpdatedUserResponseObj(){
        User user = new User();
        user.setUserId(1);
        user.setName("Anupam Rakshit");
        user.setEmail("anupam@gmail.com");
        user.setAddress1("Estonia");
        UserResponseObj userResponseObj = new UserResponseObj();
        userResponseObj.setUser(user);
        return userResponseObj;
    }

    protected UserResponseObj getUpdatedCCResponseObj() {
        User user = new User();
        user.setUserId(1);
        user.setCreditCard("123498763456");
        UserResponseObj userRequestObj= new UserResponseObj();
        userRequestObj.setUser(user);
        return userRequestObj;
    }

    protected List<Category> getCategoryList(){
        List<Category> categories= new ArrayList<>();
        Category category1 = new Category();
        category1.setCategoryId(1);
        category1.setName("Category 1");
        category1.setDescription("Description 1");
        category1.setProducts(new HashSet<>());
        categories.add(category1);
        Category category2 = new Category();
        category2.setCategoryId(1);
        category2.setName("Category 2");
        category2.setDescription("Description 2");
        category2.setProducts(new HashSet<>());
        categories.add(category2);
        return categories;
    }
    protected Category getCategoryData(){
        return getCategoryList().get(1);
    }

    protected List<Product> getProductResponseList(){

        List<Product> products= new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId(1);
        product1.setName("Product Name1");
        product1.setDescription("Product Description1");
        product1.setDiscountedPrice(1034.2);
        product1.setPrice(2014.2);

        Product product2 = new Product();
        product2.setProductId(2);
        product2.setName("Product Name2");
        product2.setDescription("Product Description2");
        product2.setDiscountedPrice(104.2);
        product2.setPrice(204.2);

        products.add(product1);
        products.add(product2);
        return products;
    }

    protected List<ProductRequestObj> getProductRequestDataList(){
        List<ProductRequestObj> productRequestObjs= new ArrayList<>();
        ProductRequestObj product1 = new ProductRequestObj();
        product1.setProductId(1);
        product1.setName("Product Name1");
        product1.setDescription("Product Description1");
        product1.setDiscountedPrice(1034.2);
        product1.setPrice(2014.2);
        product1.setCategoryId(1);
        productRequestObjs.add(product1);

        ProductRequestObj product2 = new ProductRequestObj();
        product2.setProductId(1);
        product2.setName("Product Name1");
        product2.setDescription("Product Description1");
        product2.setDiscountedPrice(1034.2);
        product2.setPrice(2014.2);
        product2.setCategoryId(1);
        productRequestObjs.add(product2);

        return productRequestObjs;
    }

    protected List<CategoryRequestObj> getCategoryRequestObj(){
        List<CategoryRequestObj> categoryRequestObjs= new ArrayList<>();
        CategoryRequestObj categoryRequestObj= new CategoryRequestObj();
        categoryRequestObj.setDescription("Category Description 1");
        categoryRequestObj.setName("Category 1");
        categoryRequestObj.setProductIds(Arrays.asList(getProductResponseList().get(0).getProductId()));
        categoryRequestObjs.add(categoryRequestObj);

        CategoryRequestObj categoryRequestObj1= new CategoryRequestObj();
        categoryRequestObj1.setDescription("Category Description 2");
        categoryRequestObj1.setName("Category 2");
        categoryRequestObj1.setProductIds(Arrays.asList(getProductResponseList().get(1).getProductId()));
        categoryRequestObjs.add(categoryRequestObj1);
        return categoryRequestObjs;
    }


}
