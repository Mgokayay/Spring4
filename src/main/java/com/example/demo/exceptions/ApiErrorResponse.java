package com.example.demo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class ApiErrorResponse {

    private Integer status;
    private String message;
    private Long timeStamp;
}
