package com.online.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.online.edu.eduservice.entity.EduSubject;
import com.online.edu.eduservice.entity.dto.OneSubjectDto;
import com.online.edu.eduservice.entity.dto.TwoSubjectDto;
import com.online.edu.eduservice.handler.EduExeception;
import com.online.edu.eduservice.mapper.EduSubjectMapper;
import com.online.edu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-02-21
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public List<String> importSubject(MultipartFile file) {
         try{
             InputStream in = file.getInputStream();
             Workbook workbook = new HSSFWorkbook(in);
             Sheet sheet = workbook.getSheetAt(0);
             List<String> msg = new ArrayList<>();
             int lastRowNum = sheet.getLastRowNum();
             for (int i = 0; i <= lastRowNum; i++) {
                 Row row = sheet.getRow(i);
                 if (row == null) {
                     String str = "表格数据为空";
                     msg.add(str);
                     return msg;
                 }
                 Cell cellOne = row.getCell(0);
                 if(cellOne == null){
                     String str = "第"+i+"行数为空";
                     msg.add(str);
                     continue;
                 }
                 String cellOneValue = cellOne.getStringCellValue();
                 EduSubject eduSubjectExist = this.existOneSubject(cellOneValue);
                 String id_parent = null;
                 if (eduSubjectExist == null) {
                     EduSubject eduSubject = new EduSubject();
                     eduSubject.setTitle(cellOneValue);
                     eduSubject.setParentId("0");
                     eduSubject.setSort(0);
                     baseMapper.insert(eduSubject);
                     id_parent = eduSubject.getId();
                 }else {
                     id_parent = eduSubjectExist.getId();
                 }
                 Cell cellTwo = row.getCell(1);
                 if (cellTwo == null) {
                     String str = "第"+i+"行数据不为空";
                     msg.add(str);
                     continue;
                 }
                 String cellTwoValue = cellTwo.getStringCellValue();
                 EduSubject twoSubjectExist = this.existTwoSubject(cellTwoValue,id_parent);
                 if (twoSubjectExist == null) {
                     EduSubject twoSubject = new EduSubject();
                     twoSubject.setTitle(cellTwoValue) ;
                     twoSubject.setParentId(id_parent);
                     twoSubject.setSort(0);
                     baseMapper.insert(twoSubject);
                 }
             }
             return msg;
         }
         catch (Exception e) {
             e.printStackTrace();
             throw new EduExeception(20001,"导入失败出现异常");
         }
    }
    //返回所有的分类，封装要求的json格式
    @Override
    public List<OneSubjectDto> getSubjectList() {
        //1 查询所有一级分类
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id","0");
        List<EduSubject> allOneSubjects = baseMapper.selectList(wrapper1);

        //2 查询所有二级分类
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id","0");
        List<EduSubject> allTwoSubjects = baseMapper.selectList(wrapper2);

        //创建list集合，用于存储所有一级分类
        List<OneSubjectDto> oneSubjectDtolist = new ArrayList<>();
        //3 首先构建一级分类
        //遍历所有的一级分类，得到每个EduSubject对象，把每个EduSubject对象转换OneSubjectDto
        for (int i = 0; i < allOneSubjects.size(); i++) {
            //获取每个EduSubject对象
            EduSubject eduOneSubject = allOneSubjects.get(i);
            //创建OneSubjectDto对象
            OneSubjectDto oneSubjectDto = new OneSubjectDto();
            //把每个EduSubject对象转换OneSubjectDto
            BeanUtils.copyProperties(eduOneSubject,oneSubjectDto);
            //把dto对象放到list集合
            oneSubjectDtolist.add(oneSubjectDto);

            //获取一级分类所有二级分类，List<TwoSubjectDto>
            //把所有的二级分类添加到每个一级分类对象中oneSubjectDto.setChildren(list);
            //创建list集合，用于存储二级分类
            List<TwoSubjectDto>  twoSubjectDtoList = new ArrayList<>();
            //遍历所有的二级分类，得到每个二级分类
            for (int m = 0; m < allTwoSubjects.size(); m++) {
                //得到每个二级分类
                EduSubject eduTwoSubject = allTwoSubjects.get(m);
                //判断一级分类id和二级分类parentid是否一样
                if(eduTwoSubject.getParentId().equals(eduOneSubject.getId())) {
                    //二级分类转换TwoSubjectDto
                    TwoSubjectDto twoSubjectDto = new TwoSubjectDto();
                    //内省  反射
                    BeanUtils.copyProperties(eduTwoSubject,twoSubjectDto);
                    //放到list集合
                    twoSubjectDtoList.add(twoSubjectDto);
                }
            }
            //把二级分类放到每个一级分类中
            oneSubjectDto.setChildren(twoSubjectDtoList);
        }
        return oneSubjectDtolist;
    }

    //删除分类
    @Override
    public boolean deleteSubjectById(String id) {
        //判断一级分类下面有二级分类
        //根据parent_id查询
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        //判断如果有二级分类
        if (count>0) {
            return false;
        } else {//没有二级分类
            //进行删除
            int result = baseMapper.deleteById(id);
            return result>0;
        }
    }

    //添加一级分类
    @Override
    public boolean saveOneLevel(EduSubject eduSubject) {
        //判断一级分类是否存在，如果存在添加
        EduSubject eduSubjectExist = this.existOneSubject(eduSubject.getTitle());
        if(eduSubjectExist == null) {//不存在
            //添加
            //一级分类parentid=0
            eduSubject.setParentId("0");
            int result = baseMapper.insert(eduSubject);
            return result>0;
        }
        return false;
    }

    //添加二级分类
    @Override
    public boolean saveTwoLevel(EduSubject eduSubject) {
        //判断二级分类是否存在
        EduSubject eduSubjectExist = this.existTwoSubject(eduSubject.getTitle(), eduSubject.getParentId());
        if(eduSubjectExist == null) {//不存在
            //添加
            int insert = baseMapper.insert(eduSubject);
            return insert>0;
        }
        return false;
    }


    private EduSubject existTwoSubject(String name,String parentid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",parentid);
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }
    private  EduSubject existOneSubject(String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id","0");
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }
}
