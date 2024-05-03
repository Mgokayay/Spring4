package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.exceptions.ApiException;
import com.example.demo.validation.CourseValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private List<Course> courses;

    private final CourseGpa highCourseGpa;

    private final CourseGpa lowCourseGpa;

    private final CourseGpa mediumCourseGpa;
    @Autowired
    public CourseController(@Qualifier("highCourseGpa") CourseGpa highCourseGpa,
                            @Qualifier("lowCourseGpa") CourseGpa lowCourseGpa,
                            @Qualifier("mediumCourseGpa") CourseGpa mediumCourseGpa) {
        this.highCourseGpa = highCourseGpa;
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
    }

    @PostConstruct
    public void init(){
        courses = new ArrayList<>();
    }

    @GetMapping
    public List<Course> getAll(){
        return this.courses;
    }

    @GetMapping("/{name}")
    public Course getByName(@PathVariable("name") String name ){
        CourseValidation.checkName(name);
        return courses.stream()
                .filter(course -> course.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new ApiException("Course not found with name:" +name, HttpStatus.NOT_FOUND));

    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody Course course){
        CourseValidation.checkName(course.getName());
        CourseValidation.checkCredit(course.getCredit());
        CourseValidation.checkNameExist(courses,course.getName());
        courses.add(course);
        Integer totalGpa = getTotalGpa(course);
        ApiResponse apiResponse = new ApiResponse(course,totalGpa);

        return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
    }

    private Integer getTotalGpa(Course course) {
        if(course.getCredit()<=2){
            return course.getGrade().getCoefficient()*course.getCredit()*lowCourseGpa.getGpa();
        } else if (course.getCredit() == 3) {
            return course.getGrade().getCoefficient()*course.getCredit()*mediumCourseGpa.getGpa();
        }
        else {
            return course.getGrade().getCoefficient()*course.getCredit()*highCourseGpa.getGpa();
        }

    }
}
