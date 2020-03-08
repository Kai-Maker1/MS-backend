package com.online.edu.eduservice.service.impl;

import com.online.edu.eduservice.entity.EduCourse;
import com.online.edu.eduservice.entity.EduCourseDescription;
import com.online.edu.eduservice.entity.dto.CourseInfoDto;
import com.online.edu.eduservice.entity.form.CourseInfoForm;
import com.online.edu.eduservice.handler.EduExeception;
import com.online.edu.eduservice.mapper.EduCourseMapper;
import com.online.edu.eduservice.service.EduChapterService;
import com.online.edu.eduservice.service.EduCourseDescriptionService;
import com.online.edu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.online.edu.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-02-23
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    //课程描述
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    //课程章节
    @Autowired
    private EduChapterService eduChapterService;

    //课程小节
    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public String  insertCourseInfo(CourseInfoForm courseInfoForm) {
        //1 课程基本信息到课程表
        //courseInfoForm数据复制到EduCourse对象里面，进行添加
        EduCourse eduCourse = new EduCourse();
        //映射实体类属性
        BeanUtils.copyProperties(courseInfoForm,eduCourse);

        int result = baseMapper.insert(eduCourse);
        //判断如果添加课程信息成功，添加描述
        if(result == 0) {//失败
            //抛出异常
            throw new EduExeception(20001,"添加课程信息失败");
        }

        //2 课程描述添加到课程描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        //获取描述信息
        String description = courseInfoForm.getDescription();
        eduCourseDescription.setDescription(description);
        //课程id
        String courseId = eduCourse.getId();
        eduCourseDescription.setId(courseId);

        boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        if(save) {
            return courseId;
        }else {
            return null;
        }
    }

    //根据id查询课程信息
    @Override
    public CourseInfoForm getIdCourse(String id) {
        //查询两张表
        //1 根据id查询课程基本信息表
        EduCourse eduCourse = baseMapper.selectById(id);
        if (eduCourse == null) {
            //没有课程信息
            throw new EduExeception(20001,"没有课程信息");

        }
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse,courseInfoForm);
        //到上一行代码时候，courseInfoForm对象里面已经有课程基本信息，没有描述信息

        //2 根据id查询课程描述表
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(id);
        String description = eduCourseDescription.getDescription();
        courseInfoForm.setDescription(description);

        //返回封装之后对象
        return courseInfoForm;
    }

    @Override
    public Boolean updateCourse(CourseInfoForm courseInfoForm) {
        //1 修改课程基本信息表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm,eduCourse);
        int result = baseMapper.updateById(eduCourse);
        if(result == 0) {
            throw new EduExeception(20001,"修改分类失败");
        }
        //2 修改描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        String id = courseInfoForm.getId();
        String description = courseInfoForm.getDescription();
        eduCourseDescription.setId(id);
        eduCourseDescription.setDescription(description);
        boolean b = eduCourseDescriptionService.updateById(eduCourseDescription);
        return b;
    }

    @Override
    public boolean removeCourseId(String id) {
        //1 根据课程id删除章节
        eduChapterService.deleteChapterByCourseId(id);

        //2 根据课程id删除小节
        eduVideoService.deleteVideoByCourseId(id);

        //3 根据课程id删除课程描述
        eduCourseDescriptionService.deleteDescriptionByCourseId(id);

        //4 删除课程本身
        int result = baseMapper.deleteById(id);
        return result>0;
    }

    //根据课程id查询课程详情
    @Override
    public CourseInfoDto getCourseInfoAll(String courseId) {
        CourseInfoDto courseInfoAll = baseMapper.getCourseInfoById(courseId);
        return courseInfoAll;
    }
}
