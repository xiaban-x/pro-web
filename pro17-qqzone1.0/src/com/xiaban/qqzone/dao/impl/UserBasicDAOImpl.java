package com.xiaban.qqzone.dao.impl;

import com.xiaban.myssm.basedao.BaseDAO;
import com.xiaban.qqzone.dao.UserBasicDAO;
import com.xiaban.qqzone.pojo.UserBasic;
import com.xiaban.qqzone.pojo.UserDetail;
import com.xiaban.util.JDBCUtilsQQZoneDb;

import java.sql.Connection;
import java.util.List;

public class UserBasicDAOImpl extends BaseDAO<UserBasic> implements UserBasicDAO {
    @Override
    public UserBasic getUserBasic(String loginId, String psd) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "select * from t_user_basic where loginId = ? and pwd = ?";
        return super.getInstance(conn,sql,loginId,psd);
    }

    @Override
    public void setUserBasic(String loginId, String nickName, String psw,String headImg) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "insert into t_user_basic values(0,?,?,?,?)";
        super.updateDB(conn,sql,loginId,nickName,psw,headImg);
    }

    @Override
    public List<UserBasic> getUserBasicList(UserBasic userBasic) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "SELECT fid as id FROM t_friend WHERE uid = ?";
        return super.getForList(conn,sql,userBasic.getId());
    }

    @Override
    public UserBasic getUserBasicById(Integer id) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "select * from t_user_basic where id = ?";
        return super.getInstance(conn,sql,id);
    }

    @Override
    public UserBasic getUserBasicByNickName(String nickName) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "SELECT * FROM t_user_basic where nickName = ?";
        return super.getInstance(conn,sql,nickName);
    }

    @Override
    public UserBasic getUserBasicByLoginId(String loginId) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "select * from t_user_basic where loginId = ?";
        return super.getInstance(conn,sql,loginId);
    }

    @Override
    public int addFriend(Integer fid, Integer uid) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "insert into t_friend values(0,?,?)";
        return super.updateDB(conn,sql,uid,fid);
    }

    @Override
    public void changePsd(String loginId , String psd) {
        Connection conn = JDBCUtilsQQZoneDb.getConn();
        String sql = "update t_user_basic set pwd= ?  where loginId = ?";
        super.updateDB(conn,sql,psd,loginId);
    }
}
