package com.taltech.foodapp.controller;

import com.taltech.foodapp.dto.ErrorResponseObj;
import com.taltech.foodapp.exceptions.AuthenticationException;
import com.taltech.foodapp.exceptions.CategoryException;
import com.taltech.foodapp.exceptions.ProductException;
import com.taltech.foodapp.exceptions.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
class CustomErrorController{

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Map<String, ErrorResponseObj>> authException(AuthenticationException ex){
        ErrorResponseObj errorResponse= new ErrorResponseObj(ex.getCode(), HttpStatus.valueOf(ex.getStatus()), ex.getMessage(), ex.getField());
        Map<String, ErrorResponseObj> error= new HashMap<>();
        error.put("error", errorResponse);
        return ResponseEntity.status(errorResponse.getStatus()).body(error);
    }

    @ExceptionHandler(value = UserException.class)
    public ResponseEntity<Map<String, ErrorResponseObj>> userException(UserException ex){
        ErrorResponseObj errorResponse= new ErrorResponseObj(ex.getCode(), HttpStatus.valueOf(ex.getStatus()), ex.getMessage(), ex.getField());
        Map<String, ErrorResponseObj> error= new HashMap<>();
        error.put("error", errorResponse);
        return ResponseEntity.status(errorResponse.getStatus()).body(error);
    }

    @ExceptionHandler(value = CategoryException.class)
    public ResponseEntity<Map<String, ErrorResponseObj>> catException(CategoryException ex){
        ErrorResponseObj errorResponse= new ErrorResponseObj(ex.getCode(), HttpStatus.valueOf(ex.getStatus()), ex.getMessage(), ex.getField());
        Map<String, ErrorResponseObj> error= new HashMap<>();
        error.put("error", errorResponse);
        return ResponseEntity.status(errorResponse.getStatus()).body(error);
    }

    @ExceptionHandler(value = ProductException.class)
    public ResponseEntity<Map<String, ErrorResponseObj>> prodException(ProductException ex){
        ErrorResponseObj errorResponse= new ErrorResponseObj(ex.getCode(), HttpStatus.valueOf(ex.getStatus()), ex.getMessage(), ex.getField());
        Map<String, ErrorResponseObj> error= new HashMap<>();
        error.put("error", errorResponse);
        return ResponseEntity.status(errorResponse.getStatus()).body(error);
    }
}
