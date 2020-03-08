package com.online.edu.vidservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VidService {
    String uploadVideoAlyun(MultipartFile file);

    void deleteAliyunVideoId(String videoId);

    void deleteVideoMore(List videoList);

}
