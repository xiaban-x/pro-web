package com.xiaban.qqzone.service.impl;

import com.xiaban.qqzone.dao.UserBasicDAO;
import com.xiaban.qqzone.pojo.UserBasic;
import com.xiaban.qqzone.service.UserBasicService;

import java.util.ArrayList;
import java.util.List;

public class UserBasicServiceImpl implements UserBasicService {

    private UserBasicDAO userBasicDAO = null;
    @Override
    public UserBasic login(String loginId, String pwd) {
        UserBasic userBasic = userBasicDAO.getUserBasic(loginId,pwd);
        return userBasic;
    }

    @Override
    public UserBasic register(String loginId,String nickName, String pwd,String headImg) {
        userBasicDAO.setUserBasic(loginId,nickName,pwd,headImg);
        return getUserBasicByNickName(nickName);
    }

    @Override
    public List<UserBasic> getFriendList(UserBasic userBasic) {
        List<UserBasic> userBasicList = userBasicDAO.getUserBasicList(userBasic);
        List<UserBasic> friendList = new ArrayList<>(userBasicList.size());
        for (int i = 0; i < userBasicList.size(); i++) {
            UserBasic friend = userBasicList.get(i);
            friend = userBasicDAO.getUserBasicById(friend.getId());
            friendList.add(friend);
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
    public int addFriend(Integer fid, Integer uid) {
        return userBasicDAO.addFriend(fid,uid);
    }
}
