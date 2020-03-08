package com.online.edu.eduservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.online.edu.common.R;
import com.online.edu.eduservice.controller.query.QueryTeacher;
import com.online.edu.eduservice.entity.EduTeacher;
import com.online.edu.eduservice.service.EduTeacherService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-15
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    //https://wipimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif
    //注入service
    @Autowired
    private EduTeacherService eduTeacherService;
    //{"code":20000,"data":{"token":"admin"}}
    //模拟登录
    @PostMapping("login")
    public R login(){
        return R.ok().data("token","admin");
    }
    //{"code":20000,"data":{"roles":"admin"},"name":"admin","avatar":"http://"}
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    //7.根据id修改的方法
    @PostMapping("updateTeacher/{id}")
    public R updateTeacher(@PathVariable String id,
                           @RequestBody EduTeacher eduTeacher){
        boolean b = eduTeacherService.updateById(eduTeacher);
        if (b) {
            return R.ok();
        }else {
            return R.error();
        }

    }
    //6.根据id查询讲师
    @GetMapping("getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher",eduTeacher);
    }
    //5.添加讲师的方法
    @PostMapping("")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        }else {
            return R.error();
        }
    }
    //4.多条件组合查询带分页
    //根据名字头衔时间范围进行查询
    @PostMapping("moreConditionPageList/{page}/{limit}")
    public R getMoreConditionPageList(@PathVariable Long page,
                                      @PathVariable Long limit,
                                      @RequestBody( required = false ) QueryTeacher queryTeacher){
        Page<EduTeacher> pageTeacher =new Page<>(page,limit);
        //调用service的方法实现条件查询分页
        eduTeacherService.pageListCondition(pageTeacher,queryTeacher);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("items",records);

    }
    //3.分页查询讲师列表
    @GetMapping("pageList/{page}/{limit}")
    public R getPageTeacherList(@PathVariable Long page,
                                @PathVariable Long limit){
        //创建page对象，传递两个参数
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        //调用方法分页查询
        eduTeacherService.page(pageTeacher,null);
        //从pageTeacher对象里面获取分页数据
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total",total).data("items",records);
    }
    //1.查询所有讲师
    @GetMapping
    public R getAllTeacherList(){
        //调用service方法 因为有response注解 所以最后传出的值以json数组的形式传出
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    //2.逻辑删除讲师 路径传值
    @DeleteMapping("{id}")
    public boolean deleteTeacherById(@PathVariable("id") String id){
        boolean b = eduTeacherService.removeById(id);

        return b;
    }
}

