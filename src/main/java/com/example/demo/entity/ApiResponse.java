package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class ApiResponse {
    private Course course;

    private Integer totalGpa;
}
