package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Course {

    private Integer id;

    private String name;
    private Integer credit;

    private Grade grade;
}
