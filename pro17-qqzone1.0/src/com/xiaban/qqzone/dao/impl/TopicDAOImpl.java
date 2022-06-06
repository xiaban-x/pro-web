package com.xiaban.qqzone.dao.impl;

import com.xiaban.myssm.basedao.BaseDAO;
import com.xiaban.qqzone.dao.TopicDAO;
import com.xiaban.qqzone.pojo.Topic;
import com.xiaban.qqzone.pojo.UserBasic;
import com.xiaban.util.JDBCUtilsQQZoneDb;

import java.sql.Connection;
import java.util.List;

public class TopicDAOImpl extends BaseDAO<Topic> implements TopicDAO {
    @Override
    public List<Topic> getTopicList(UserBasic userBasic) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "select * from t_topic where author = ?";
        return super.getForList(conn,sql,userBasic.getId());
    }

    @Override
    public void addTopic(Topic topic) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "insert into t_topic values(0,?,?,?,?)";
        Integer id = topic.getId();
        super.updateDB(conn,sql,topic.getTitle(),topic.getContent(),topic.getTopicDate(),topic.getId());
    }

    @Override
    public void delTopic(Topic topic) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "delete from t_topic where id = ?";
        super.updateDB(conn,sql,topic.getId());
    }

    @Override
    public Topic getTopic(Integer id) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "select * from t_topic where id = ?";
        return super.getInstance(conn,sql,id);
    }
}
