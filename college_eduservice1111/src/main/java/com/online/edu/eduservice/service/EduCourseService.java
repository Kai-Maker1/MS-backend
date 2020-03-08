package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.dto.CourseInfoDto;
import com.online.edu.eduservice.entity.form.CourseInfoForm;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-02-23
 */
public interface EduCourseService extends IService<EduCourse> {

    String insertCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getIdCourse(String id);

    Boolean updateCourse(CourseInfoForm courseInfoForm);

    boolean removeCourseId(String id);

    CourseInfoDto getCourseInfoAll(String courseId);

}
