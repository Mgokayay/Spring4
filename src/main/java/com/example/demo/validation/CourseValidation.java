package com.example.demo.validation;

import com.example.demo.entity.Course;
import com.example.demo.exceptions.ApiException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

public class CourseValidation {

    public static void checkName(String name){
        if(name == null || name.isEmpty()){
            throw new ApiException("name cannot be null or empty ", HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkCredit(Integer credit) {
        if(credit==null || credit<0 || credit>4){
            throw new ApiException("credit is null or not between 0-4",HttpStatus.BAD_REQUEST);
        }

    }

    public static void checkNameExist(List<Course> courses, String name) {
        Optional<Course> courseOptional=courses.stream().filter(c -> c.getName().equalsIgnoreCase(name))
                .findAny();
        if(courseOptional.isPresent()){
            throw new ApiException("course already exist with name: " + name,HttpStatus.BAD_REQUEST);
        }
    }

    public static void checkId(Long id) {
        if(id == null || id < 0){
            throw new ApiException("Id can not be null or less than zero " ,HttpStatus.NOT_FOUND);
        }
    }
}
