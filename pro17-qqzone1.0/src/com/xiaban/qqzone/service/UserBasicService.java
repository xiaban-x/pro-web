package com.xiaban.qqzone.service;

import com.xiaban.qqzone.pojo.UserBasic;

import java.util.List;

public interface UserBasicService {
    UserBasic login(String loginId, String pwd);
    //注册
    UserBasic register(String loginId,String nickName, String pwd, String headImg);
    //修改密码
    void changePsd(String loginId,String psd);

    List<UserBasic> getFriendList(UserBasic userBasic);

    UserBasic getUserBasicById(Integer id);
    //根据nickName获取信息
    UserBasic getUserBasicByNickName(String nickName);
    //根据loginId获取信息
    UserBasic getUserBasicByLoginId(String loginId);
    //根据id添加好友
    int addFriend(Integer fid,Integer uid);
}
