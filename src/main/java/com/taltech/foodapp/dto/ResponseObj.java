package com.taltech.foodapp.dto;

import com.taltech.foodapp.dao.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseObj {

    private List<Product> rows= new LinkedList<>();
}
