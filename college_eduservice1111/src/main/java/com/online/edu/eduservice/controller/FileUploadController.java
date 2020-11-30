package com.online.edu.eduservice.controller;


import com.aliyun.oss.OSSClient;
import com.online.edu.common.R;
import com.online.edu.eduservice.handler.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@RestController
@RequestMapping("/eduservice/oss")
@CrossOrigin


public class FileUploadController {


    //上传讲师头像的方法
    @PostMapping("upload")
    public R uploadTeacherImg(@RequestParam("file") MultipartFile file,@RequestParam(value="host",required = false) String host){

        String endpoint = ConstantPropertiesUtil.ENDPOINT;
        String accessKeyId = ConstantPropertiesUtil.KEYID;
        String accessKeySecret = ConstantPropertiesUtil.KEYSECRET;
        String yourBucketName = ConstantPropertiesUtil.BUCKERTNAME;
        try{
            //1.获取到上传文件 Multipartfile file 跟表单中名字一样
            //2.获取上传文件名称，获取上传文件输入流
            String filename = file.getOriginalFilename();
            //在文件名称之前添加uuid值，保证文件名称不重复
            String uuid = UUID.randomUUID().toString();
            filename = uuid+ filename;

            //获取当前日期 2019/04/13
            String filePath = new DateTime().toString("yyyy/MM/dd");

            //拼接文件完整名称
            filename = filePath+"/"+filename;

            String hostName = ConstantPropertiesUtil.HOST;
            //如果上传头像 host里面为空，如果上传封面host有值
            if(!StringUtils.isEmpty(host)) { //不为空
                hostName = host;
            }
            filename = filePath+"/"+hostName+"/"+filename;

            InputStream in = file.getInputStream();
            //3.把上传文件存储到阿里云oss里面
            //创建OSSClient实例
            OSSClient ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret);

            //上传文件流
            //第一个参数BuckeName，第二个参数文件名称，第三个参数文件输入流
            ossClient.putObject(yourBucketName,filename, in);

            // 关闭OSSClient。
            ossClient.shutdown();
            //返回上传之后oss存储路径
            //http：//
            String hostname = ConstantPropertiesUtil.HOST;
            String path = "http://"+yourBucketName+"."+endpoint+"/"+hostname+"/"+filename;

            return R.ok().data("imgurl",path);

        }
        catch(Exception e){
            e.printStackTrace();
            return R.error();
        }
    }
}
