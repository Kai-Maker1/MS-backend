package com.online.edu.eduservice.controller;


import com.online.edu.common.R;
import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.dto.CourseInfoDto;
import com.online.edu.eduservice.entity.form.CourseInfoForm;
import com.online.edu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-02-23
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    //最终发布课程的方法，修改课程状态
    @GetMapping("publishCourse/{courseId}")
    public  R publishCourse(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        boolean result = eduCourseService.updateById(eduCourse);
        if(result) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    //根据课程id查询课程详细信息
    @GetMapping("getAllCourseInfo/{courseId}")
    public R getAllCourseInfo(@PathVariable String courseId) {
        CourseInfoDto courseInfoDto = eduCourseService.getCourseInfoAll(courseId);
        return R.ok().data("courseInfo",courseInfoDto);
    }

    //删除课程的方法
    @DeleteMapping("deleteCourse/{id}")
    public R deleteCourse(@PathVariable String id) {
        boolean flag = eduCourseService.removeCourseId(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    //课程列表
    //TODO  完善列表带分页，带条件查询 （课程名称，价格）
    @GetMapping("listCourse")
    public R getCourseList() {
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("items",list);
    }

    //修改课程的方法
    @PostMapping("updateCourseInfo/{id}")
    public R updateCourseInfo(@PathVariable String id,
                              @RequestBody CourseInfoForm courseInfoForm) {
        Boolean flag = eduCourseService.updateCourse(courseInfoForm);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    //根据id查询课程信息
    @GetMapping("getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable String id){
        CourseInfoForm courseInfoForm = eduCourseService.getIdCourse(id);
        return R.ok().data("courseInfoForm",courseInfoForm);
    }

    //1 添加课程信息的方法
    @PostMapping
    public R addCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
        String courseId = eduCourseService.insertCourseInfo(courseInfoForm);
        return R.ok().data("courseId",courseId);
    }
}

