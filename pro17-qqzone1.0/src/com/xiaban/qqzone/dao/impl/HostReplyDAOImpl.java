package com.xiaban.qqzone.dao.impl;

import com.xiaban.myssm.basedao.BaseDAO;
import com.xiaban.qqzone.dao.HostReplyDAO;
import com.xiaban.qqzone.pojo.HostReply;
import com.xiaban.util.JDBCUtilsQQZoneDb;

import java.sql.Connection;

public class HostReplyDAOImpl extends BaseDAO<HostReply> implements HostReplyDAO {
    @Override
    public HostReply getHostReplyByReplyId(Integer replyId) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "select * from t_host_reply where reply = ?";
        return super.getInstance(conn,sql,replyId);
    }
    @Override
    public void delHostReply(Integer id) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "delete from t_host_reply where id = ? ";
        super.updateDB( conn,sql, id);
    }
}
