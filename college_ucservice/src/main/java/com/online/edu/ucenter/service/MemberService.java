package com.online.edu.ucenter.service;

import com.online.edu.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author lucy
 * @since 2019-04-24
 */
public interface MemberService extends IService<Member> {

    Integer countRegisterNum(String day);
}
