package com.online.edu.vidservice.controller;

import com.online.edu.vidservice.service.VidService;
import com.online.edu.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
@RestController
@RequestMapping("/vidservice/vod")
@CrossOrigin
public class VidController {

    @Autowired
    private VidService vidService;

    //删除阿里云多个视频
    //参数是多个视频id的list集合
    @DeleteMapping("removeMoreVideo")
    public R deleteMoreVideo(@RequestParam("videoList") List videoList) {
        vidService.deleteVideoMore(videoList);
        return R.ok();
    }
    //实现删除云端视频的方法
    @DeleteMapping("{videoId}")
    public R deleteVideoIdAliyun(@PathVariable String videoId) {
        vidService.deleteAliyunVideoId(videoId);
        return R.ok();
    }
    //实现上传视频到阿里云服务器的方法
    @PostMapping("upload")
    public R uploadAliyunVideo(@RequestParam("file") MultipartFile file) {
        //调用方法实现视频上传，返回上传之后视频id
        String videoId = vidService.uploadVideoAlyun(file);
        return R.ok().data("videoId",videoId);
    }
}
