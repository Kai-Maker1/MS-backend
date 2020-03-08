package com.online.edu.eduservice.controller.query;

import lombok.Data;

//用于封装条件值，专门用于传递多条件查询带分页的对象类
@Data
public class QueryTeacher {
    private String name;
    private String level;
    private String begin;//开始时间
    private String end;//结束时间
}