package com.xiaban.qqzone.dao;

import com.xiaban.qqzone.pojo.Topic;
import com.xiaban.qqzone.pojo.UserBasic;

import java.util.List;

public interface UserBasicDAO {
    //1.根据登录的账号密码查看信息
    public UserBasic getUserBasic(String id,String psw);
    //注册
    public void setUserBasic(String loginId,String nickName,String psw,String headImg);
    //2.获取指定用户的所有好友列表
    public List<UserBasic> getUserBasicList(UserBasic userBasic);
    //3.根据id查询UserBasic的信息
    public UserBasic getUserBasicById(Integer id);
    //根据nickName查询UserBasic的信息
    public UserBasic getUserBasicByNickName(String nickName);
    //根据userBasicId和friendId给userBasicId添加好友
    public int addFriend(Integer fid,Integer uid);
}
