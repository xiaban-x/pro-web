package com.xiaban.qqzone.service.impl;

import com.xiaban.qqzone.dao.TopicDAO;
import com.xiaban.qqzone.pojo.Reply;
import com.xiaban.qqzone.pojo.Topic;
import com.xiaban.qqzone.pojo.UserBasic;
import com.xiaban.qqzone.service.ReplyService;
import com.xiaban.qqzone.service.TopicService;
import com.xiaban.qqzone.service.UserBasicService;

import java.util.List;

public class TopicServiceImpl implements TopicService {

    private TopicDAO topicDAO = null;
    private UserBasicService userBasicService ;
    private ReplyService replyService;
    @Override
    public List<Topic> getTopicList(UserBasic userBasic) {

        return topicDAO.getTopicList(userBasic);
    }

    @Override
    public Topic getTopic(Integer id){
        Topic topic = topicDAO.getTopic(id);
        UserBasic author = topic.getAuthor();
        author = userBasicService.getUserBasicById(author.getId());
        topic.setAuthor(author);
        return topic;
    }

    @Override
    public void delTopic(Integer id) {
        Topic topic = topicDAO.getTopic(id);
        if(topic!=null){
            replyService.delReplyList(topic);
            topicDAO.delTopic(topic);
        }
    }

    @Override
    public void addTopic(Topic topic) {
        topicDAO.addTopic(topic);
    }

    @Override
    public Topic getTopicById(Integer id) {
        Topic topic = getTopic(id);
        List<Reply> replyList = replyService.getReplyListByTopicId(topic.getId());
        topic.setReplyList(replyList);

        return topic ;
    }
}
