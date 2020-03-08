package com.online.edu.statistics.service;

import com.online.edu.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author lucy
 * @since 2019-04-24
 */
public interface DailyService extends IService<Daily> {

    void getCountRegisterNum(String day);

    Map<String, Object> getDataCount(String type, String begin, String end);
}
