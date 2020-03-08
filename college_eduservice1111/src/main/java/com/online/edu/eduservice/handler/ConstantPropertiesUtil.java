package com.online.edu.eduservice.handler;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
//定义阿里云数据传输常量
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyid;

    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    //读取文件夹名称
    @Value("{aliyun.oss.file.host}")
    private String host;
    //定义常量，为了能够方便使用
    public static String ENDPOINT;
    public static String KEYID;
    public static String KEYSECRET;
    public static String BUCKERTNAME;
    public static String HOST;
    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT=endpoint;
        KEYID=keyid;
        KEYSECRET=keysecret;
        BUCKERTNAME=bucketName;
        HOST=host;
    }
}
