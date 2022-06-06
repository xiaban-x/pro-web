package com.xiaban.qqzone.dao.impl;

import com.xiaban.myssm.basedao.BaseDAO;
import com.xiaban.qqzone.dao.ReplyDAO;
import com.xiaban.qqzone.pojo.Reply;
import com.xiaban.qqzone.pojo.Topic;
import com.xiaban.util.JDBCUtilsQQZoneDb;

import java.sql.Connection;
import java.util.List;

public class ReplyDAOImpl extends BaseDAO<Reply> implements ReplyDAO {
    @Override
    public List<Reply> getReplyList(Topic topic) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "select * from t_reply where topic = ?";
        return super.getForList(conn,sql,topic.getId());
    }

    @Override
    public void addReply(Reply reply) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "insert into t_reply values(0,?,?,?,?)";
        super.updateDB(conn,sql,reply.getContent(),reply.getReplyDate(),reply.getAuthor().getId() , reply.getTopic().getId());
    }

    @Override
    public void delReply(Integer id) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "delete from t_reply where id = ?";
        super.updateDB(conn,sql,id);
    }

    @Override
    public Reply getReply(Integer id) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "select * from t_reply where id = ?";
        return super.getInstance(conn,sql,id);
    }
}
