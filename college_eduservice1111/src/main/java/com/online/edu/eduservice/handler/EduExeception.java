package com.online.edu.eduservice.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//自定义异常类
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EduExeception extends RuntimeException{
    private Integer code;//状态码
    private String message;//

}
