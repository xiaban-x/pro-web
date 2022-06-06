package com.xiaban.qqzone.controller;

import com.xiaban.qqzone.pojo.*;
import com.xiaban.qqzone.pojo.UserBasic;
import com.xiaban.qqzone.service.ReplyService;
import com.xiaban.qqzone.pojo.Reply;
import com.xiaban.qqzone.pojo.Topic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

public class ReplyController {

    private ReplyService replyService ;

    public String addReply(String content , Integer topicId , HttpServletRequest req){
        HttpSession session = req.getSession();
        UserBasic author = (UserBasic)session.getAttribute("userBasic");
        Reply reply = new Reply(content , new Date() , author , new Topic(topicId));
        replyService.addReply(reply);
        return "redirect:topic.do?operate=topicDetail&id="+topicId;
        // detail.html
    }

    public String delReply(Integer replyId , Integer topicId){
        replyService.delReply(replyId);
        return "redirect:topic.do?operate=topicDetail&id="+topicId;
    }
}
