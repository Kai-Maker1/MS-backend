package com.online.edu.eduservice.mapper;

import com.online.edu.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.online.edu.eduservice.entity.dto.CourseInfoDto;
import com.online.edu.eduservice.entity.dto.CoursePublishDto;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author lucy
 * @since 2019-03-26
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //根据课程id查询课程详情
    CourseInfoDto getCourseInfoById(String courseId);
}
