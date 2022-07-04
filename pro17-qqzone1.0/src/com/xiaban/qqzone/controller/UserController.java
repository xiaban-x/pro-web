package com.xiaban.qqzone.controller;

import com.xiaban.qqzone.pojo.Topic;
import com.xiaban.qqzone.pojo.UserBasic;
import com.xiaban.qqzone.pojo.UserDetail;
import com.xiaban.qqzone.service.TopicService;
import com.xiaban.qqzone.service.UserBasicService;
import com.xiaban.qqzone.service.UserDetailService;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class UserController {

    private UserBasicService userBasicService;
    private TopicService topicService;
    private UserDetailService userDetailService;
    public String login(String loginId, String pwd, HttpServletRequest req){

        HttpSession session = req.getSession();
        //1.登录验证
        UserBasic userBasic = userBasicService.login(loginId,pwd);
        if (userBasic!=null){
            //2.得到好友列表
            List<UserBasic> friendList = userBasicService.getFriendList(userBasic);
            //3.获得日志列表
            List<Topic> topicList = topicService.getTopicList(userBasic);
            //4.获取好友个人信息
            for (int i = 0; i < friendList.size(); i++) {
                UserBasic friend = friendList.get(i);
                String loginId1 = friend.getLoginId();
                UserDetail userDetail = userDetailService.getUserDetail(loginId1);
                friend.setUserDetail(userDetail);
            }
            //5.获取登录进来得账户得个人信息
            userBasic.setUserDetail(userDetailService.getUserDetail(loginId));
            userBasic.setFriendList(friendList);
            userBasic.setTopicList(topicList);

            //userBasic保存的登入进来的信息
            //friend保存的是访问的空间的信息
            session.setAttribute("userBasic",userBasic);
            session.setAttribute("friend",userBasic);
            return "index";
        }else{
            return "login";
        }
    }
    public String register(String registerId,String nickName, String pwd,String headImg,HttpServletRequest req){
        HttpSession session = req.getSession();
        UserBasic userBasic = userBasicService.register(registerId,nickName,pwd,headImg);
        userBasic = userBasicService.login(registerId,pwd);
        if (userBasic!=null){
            //2.得到好友列表
            List<UserBasic> friendList = userBasicService.getFriendList(userBasic);
            //3.获得日志列表
            List<Topic> topicList = topicService.getTopicList(userBasic);

            userBasic.setFriendList(friendList);
            userBasic.setTopicList(topicList);

            //userBasic保存的登入进来的信息
            //friend保存的是访问的空间的信息
            session.setAttribute("userBasic",userBasic);
            session.setAttribute("friend",userBasic);
            return "index";
        }else{
            return "login";
        }
    }

    public String friend(Integer id,HttpServletRequest req){
        //1.根据id获取用户信息
        UserBasic currFriend = userBasicService.getUserBasicById(id);

        List<Topic> topicList = topicService.getTopicList(currFriend);

        currFriend.setTopicList(topicList);

        HttpSession session = req.getSession();

        session.setAttribute("friend",currFriend);

        return "index";
    }

    public String addFriend(Integer fid, Integer uid,String nickName,UserBasic userBasic){
        UserBasic friendBasic;
        if (fid == 0){
            friendBasic=userBasicService.getUserBasicByNickName(nickName);
            fid = friendBasic.getId();
        }
        userBasic = userBasicService.getUserBasicById(uid);
        List<UserBasic> friendList = userBasicService.getFriendList(userBasic);
        for (UserBasic basic : friendList) {
            if (basic.getId().equals(fid)) {
                return "frames/lose";
            }
        }
        int count = userBasicService.addFriend(fid,uid);
        if (count == -1){
            return "frames/lose";
        }
        return "redirect:user.do?operate=getFriendList";
    }

    public String getFriendList(HttpServletRequest req){
        HttpSession session = req.getSession();
        //从session中获取当前用户信息
        UserBasic userBasic = (UserBasic)session.getAttribute("userBasic");
        //再次查询当前用户关联的所有的朋友
        List<UserBasic> friendList = userBasicService.getFriendList(userBasic);
        //设置一下关联的好友列表(因为之前session中关联的friend和此刻数据库中不一致）
        userBasic.setFriendList(friendList);
        //重新覆盖一下friend中的信息(为什么不覆盖userbasic中？因为left.html页面迭代的是friend这个key中的数据)
        session.setAttribute("friend",userBasic);
        return "frames/left";
    }

    public String editInfo(String nickName,Integer id,String realName,String tel,String email,String birth,String star,HttpServletRequest req) throws ParseException {
        HttpSession session = req.getSession();
        UserBasic userBasic = (UserBasic) session.getAttribute("userBasic");
        //LocalDate date = LocalDate.parse(birth);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = (Date) simpleDateFormat.parse(birth);
        SimpleDateFormat dateFormatHiddenHour = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormatHiddenHour.parse(birth);
        //System.out.println(date);
        UserDetail userDetail = new UserDetail(realName,tel,email,date,star,userBasic.getLoginId());
        userDetailService.editInfo(userDetail);
        userBasic.setUserDetail(userDetail);
        session.setAttribute("userBasic",userBasic);
        return "redirect:user.do?operate=findInfo";
    }

    public String addInfo(String nickName,Integer id,String realName,String tel,String email,String birth,String star,HttpServletRequest req) throws ParseException {
        HttpSession session = req.getSession();
        UserBasic userBasic = (UserBasic) session.getAttribute("userBasic");
        SimpleDateFormat dateFormatHiddenHour = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormatHiddenHour.parse(birth);
        //System.out.println(date);
        UserDetail userDetail = new UserDetail(realName,tel,email,date,star,userBasic.getLoginId());
        userDetailService.addInfo(userDetail);
        userBasic.setUserDetail(userDetail);
        session.setAttribute("userBasic",userBasic);
        return "redirect:user.do?operate=findInfo";
    }
    public String findInfo(){
        return "frames/findInfo";
    }

    public String verifyPsd(String loginId,String realName,String email,String star,HttpServletRequest req){
        UserBasic userBasic = userBasicService.getUserBasicByLoginId(loginId);
        UserDetail userDetail = userDetailService.getUserDetail(loginId);
        HttpSession session = req.getSession();
        session.setAttribute("loginId",loginId);
        if(userDetail == null){
            return "verifyPsdLose";
        }
        if (userDetail.getRealName().equals(realName) && userDetail.getEmail().equals(email) && userDetail.getStar().equals(star)){
            return "verifyPsdWin";
        }else{
            return "verifyPsdLose";
        }
    }
    public String changePsd(String loginId,String pwd){
        userBasicService.changePsd(loginId,pwd);
        return "login";
    }
}
