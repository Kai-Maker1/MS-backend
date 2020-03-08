package com.online.edu.eduservice.service;

import com.online.edu.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.online.edu.eduservice.entity.dto.OneSubjectDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-02-21
 */
public interface EduSubjectService extends IService<EduSubject> {
    List<String> importSubject(MultipartFile file);
    //返回所有分类
    List<OneSubjectDto> getSubjectList();

    //删除分类
    boolean deleteSubjectById(String id);

    //添加一级分类
    boolean saveOneLevel(EduSubject eduSubject);

    //添加二级分类
    boolean saveTwoLevel(EduSubject eduSubject);
}
