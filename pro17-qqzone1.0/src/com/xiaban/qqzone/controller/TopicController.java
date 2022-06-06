package com.xiaban.qqzone.controller;

import com.xiaban.qqzone.pojo.Reply;
import com.xiaban.qqzone.pojo.Topic;
import com.xiaban.qqzone.pojo.UserBasic;
import com.xiaban.qqzone.service.TopicService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

public class TopicController {
    private TopicService topicService ;

    public String topicDetail(Integer id , HttpServletRequest req){
        Topic topic = topicService.getTopicById(id);

        HttpSession session = req.getSession();
        session.setAttribute("topic",topic);
        return "frames/detail";
    }

    public String delTopic(Integer topicId){
        topicService.delTopic(topicId);
        return "redirect:topic.do?operate=getTopicList" ;
    }

    public String getTopicList(HttpSession session){
        //从session中获取当前用户信息
        UserBasic userBasic = (UserBasic)session.getAttribute("userBasic");
        //再次查询当前用户关联的所有的日志
        List<Topic> topicList = topicService.getTopicList(userBasic);
        //设置一下关联的日志列表(因为之前session中关联的friend的topicList和此刻数据库中不一致）
        userBasic.setTopicList(topicList);
        //重新覆盖一下friend中的信息(为什么不覆盖userbasic中？因为main.html页面迭代的是friend这个key中的数据)
        session.setAttribute("friend",userBasic);
        return "frames/main";
    }

    public String lookNewTopic(HttpSession session){
        //从session中获取当前用户信息
        UserBasic userBasic = (UserBasic)session.getAttribute("userBasic");
        //再次查询当前用户关联的所有的日志
        List<Topic> topicList = topicService.getTopicList(userBasic);
        //设置一下关联的日志列表(因为之前session中关联的friend的topicList和此刻数据库中不一致）
        userBasic.setTopicList(topicList);
        //重新覆盖一下friend中的信息(为什么不覆盖userbasic中？因为main.html页面迭代的是friend这个key中的数据)
        session.setAttribute("friend",userBasic);
        return "frames/main";
    }
    public String addTopic(String title,String content,Integer id,HttpServletRequest req){
        HttpSession session = req.getSession();
        //UserBasic author = (UserBasic)session.getAttribute("userBasic");
        Topic topic = new Topic(id,title,content,new Date());
        topicService.addTopic(topic);
        return "redirect:topic.do?operate=getTopicList";
    }
}
