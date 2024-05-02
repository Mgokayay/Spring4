package com.example.demo.validation;

import com.example.demo.exceptions.ApiException;
import org.springframework.http.HttpStatus;

public class CourseValidation {

    public static void checkName(String name){
        if(name == null || name.isEmpty()){
            throw new ApiException("name cannot be null or empty ", HttpStatus.BAD_REQUEST);
        }
    }
}
