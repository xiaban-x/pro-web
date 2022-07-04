package com.xiaban.qqzone.service.impl;

import com.xiaban.qqzone.dao.UserBasicDAO;
import com.xiaban.qqzone.pojo.UserBasic;
import com.xiaban.qqzone.service.UserBasicService;

import java.util.ArrayList;
import java.util.List;

public class UserBasicServiceImpl implements UserBasicService {

    private UserBasicDAO userBasicDAO;
    @Override
    public UserBasic login(String loginId, String pwd) {
        return userBasicDAO.getUserBasic(loginId,pwd);
    }

    @Override
    public UserBasic register(String loginId,String nickName, String pwd,String headImg) {
        userBasicDAO.setUserBasic(loginId,nickName,pwd,headImg);
        return getUserBasicByNickName(nickName);
    }

    @Override
    public void changePsd(String loginId, String psd) {
        userBasicDAO.changePsd(loginId,psd);
    }

    @Override
    public List<UserBasic> getFriendList(UserBasic userBasic) {
        //userBasicList获得的是t_friend表的信息，以userBasic的id为uid，得到它的fid，所以这里的List存储的是一堆fid
        List<UserBasic> userBasicList = userBasicDAO.getUserBasicList(userBasic);
        //friendList用来存储最终展示的好友
        List<UserBasic> friendList = new ArrayList<>(userBasicList.size());
        for (int i = 0; i < userBasicList.size(); i++) {
            //依次取出和userBasic的id有联系的fid
            UserBasic friend = userBasicList.get(i);
            //再以有联系的fid来查询它们作为uid的时候关联的fid，所以userBasicList_friend存储的是userBasic的id的fid的fid
            List<UserBasic> userBasicList_friend = userBasicDAO.getUserBasicList(friend);
            for (int j = 0; j < userBasicList_friend.size(); j++) {
                //依次取出和userBasic的id的fid有联系的fid
                UserBasic friend1 = userBasicList_friend.get(j);
                //只有当userBasic的id的fid等于fid的fid的时候才证明两个人互相是好友
                if(userBasic.getId().equals(friend1.getId())){
                    friend = userBasicDAO.getUserBasicById(friend.getId());
                    friendList.add(friend);
                }
            }
        }
        return friendList;
    }

    @Override
    public UserBasic getUserBasicById(Integer id) {
        return userBasicDAO.getUserBasicById(id);
    }

    @Override
    public UserBasic getUserBasicByNickName(String nickName) {
        return userBasicDAO.getUserBasicByNickName(nickName);
    }

    @Override
    public UserBasic getUserBasicByLoginId(String loginId) {
        return userBasicDAO.getUserBasicByLoginId(loginId);
    }

    @Override
    public int addFriend(Integer fid, Integer uid) {
        return userBasicDAO.addFriend(fid,uid);
    }
}
