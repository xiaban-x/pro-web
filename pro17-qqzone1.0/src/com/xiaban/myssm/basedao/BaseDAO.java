package com.xiaban.myssm.basedao;

import com.xiaban.util.JDBCUtilsQQZoneDb;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ognl.Ognl.setValue;

public abstract class BaseDAO<T> {

    private Class<T> clazz = null;

    {
        //获取当前BaseDAO的子类继承的父类中的泛型
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) genericSuperclass;

        Type[] actualTypeArguments = paramType.getActualTypeArguments();//获取了父类的泛型参数
        clazz = (Class<T>)actualTypeArguments[0];//泛型的第一个参数
    }
    //通用的查询操作，用于返回数据库的一条记录
    public T getInstance(Connection conn,String sql, Object...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }

            rs = ps.executeQuery();
            //获取结果集的元数据；ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();
            if(rs.next()){
                T t = clazz.newInstance();
                //处理结果集一行数据中的每一列
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object columnValue = rs.getObject(i + 1);

                    //获取每个列的列名
                    String columnName = rsmd.getColumnLabel(i + 1);

                    //给cust对象指定的columnName属性，赋值为value，通过反射
                    Field field = clazz.getDeclaredField(columnName);
                    String typeName = field.getType().getName();
                    //判断如果是自定义类型，则需要调用这个自定义类的带一个参数的构造方法，创建出这个自定义的实例对象，然后
                    if (isMyType(typeName)){
                        //假设typeName是"com.xiaban.qqzone.pojo.UserBasic"
                        Class typeNameClass = Class.forName(typeName);
                        Constructor constructor = typeNameClass.getDeclaredConstructor(java.lang.Integer.class);
                        columnValue = constructor.newInstance(columnValue);
                    }
                    field.setAccessible(true);
                    field.set(t,columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("DAO getInstance 报错了");
        } finally {
            JDBCUtilsQQZoneDb.closeResource(null,ps,rs);
        }
        return null;
    }
    //执行查询，返回单个实体对象
    protected T load(String sql , Object... params){
        conn = getConn() ;
        try{
            psmt = conn.prepareStatement(sql);
            setParams(psmt,params);
            rs = psmt.executeQuery();

            //通过rs可以获取结果集的元数据
            //元数据：描述结果集数据的数据 , 简单讲，就是这个结果集有哪些列，什么类型等等

            ResultSetMetaData rsmd = rs.getMetaData();
            //获取结果集的列数
            int columnCount = rsmd.getColumnCount();
            //6.解析rs
            if(rs.next()){
                T entity = (T)clazz.newInstance();

                for(int i = 0 ; i<columnCount;i++){
                    String columnName = rsmd.getColumnName(i+1);            //fid   fname   price
                    Object columnValue = rs.getObject(i+1);     //33    苹果      5
                    setValue(entity,columnName,columnValue);
                }
                return entity ;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new DAOException("BaseDAO load出错了");
        }

        return null ;
    }
    //通用的查询操作，返回数据库的多条记录构成的集合
    public List<T> getForList(Connection conn, String sql, Object...args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }

            rs = ps.executeQuery();
            //获取结果集的元数据；ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();
            //插件集合对象
            ArrayList<T> list = new ArrayList<>();
            while(rs.next()){
                T t = clazz.newInstance();
                //处理结果集一行数据中的每一列:给t对象指定的属性赋值
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object columnValue = rs.getObject(i + 1);

                    //获取每个列的列名
                    String columnName = rsmd.getColumnLabel(i + 1);

                    //给fruit对象指定的columnName属性，赋值为value，通过反射
                    Field field = clazz.getDeclaredField(columnName);
                    String typeName = field.getType().getName();
                    //判断如果是自定义类型，则需要调用这个自定义类的带一个参数的构造方法，创建出这个自定义的实例对象，然后
                    if (isMyType(typeName)){
                        //假设typeName是"com.xiaban.qqzone.pojo.UserBasic"
                        Class typeNameClass = Class.forName(typeName);
                        Constructor constructor = typeNameClass.getDeclaredConstructor(java.lang.Integer.class);
                        columnValue = constructor.newInstance(columnValue);
                    }
                    field.setAccessible(true);
                    field.set(t,columnValue);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("DAO getForList 报错了");
        } finally {
            JDBCUtilsQQZoneDb.closeResource(null,ps,rs);
        }
    }

    private static boolean isMyType(String typeName){
        if("java.lang.Integer".equals(typeName)){
            return false;
        }else if("java.lang.String".equals(typeName)){
            return false;
        }else if("java.lang.Date".equals(typeName)){
            return false;
        }else if ("java.util.Date".equals(typeName)){
            return false;
        }else if ("java.time.LocalDate".equals(typeName)){
            return false;
        }else if("java.sql.Date".equals(typeName)){
            return false;
        }

        return true;
    }

    //通用的增删改操作
    public int updateDB(Connection conn , String sql, Object ...args){
        PreparedStatement ps = null;
        try {
            //1.预编译sql语句返回PreparedStatement
            if (conn != null) {
                ps = conn.prepareStatement(sql);
            }
            //2.填充占位符
            for (int i = 0; i < args.length; i++) {
                if (ps != null) {
                    ps.setObject(i+1,args[i]);
                }
            }
            //3.执行操作
            if (ps != null) {
                return ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("DAO UpdateDB 报错了");
        } finally {

            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DAOException("DAO UpdateDB 报错了");
            }
            //4.关闭资源
            JDBCUtilsQQZoneDb.closeResource(null,ps);
        }
        return 0;
    }

    public <E>E getValue(Connection conn,String sql,Object ...args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }

            rs = ps.executeQuery();
            if (rs.next()){
                return (E)rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("DAO getValue 报错了");
        } finally {
            JDBCUtilsQQZoneDb.closeResource(null,ps,rs);
        }

        return null;
    }
    protected Connection getConn(){
        return ConnUtil.getConn();
    }
    //执行复杂查询，返回例如统计结果
    protected Connection conn ;
    protected PreparedStatement psmt ;
    protected ResultSet rs ;


    protected Object[] executeComplexQuery(String sql , Object... params){
        try {
            conn = getConn() ;
            psmt = conn.prepareStatement(sql);
            setParams(psmt,params);
            rs = psmt.executeQuery();

            //通过rs可以获取结果集的元数据
            //元数据：描述结果集数据的数据 , 简单讲，就是这个结果集有哪些列，什么类型等等

            ResultSetMetaData rsmd = rs.getMetaData();
            //获取结果集的列数
            int columnCount = rsmd.getColumnCount();
            Object[] columnValueArr = new Object[columnCount];
            //6.解析rs
            if(rs.next()){
                for(int i = 0 ; i<columnCount;i++){
                    Object columnValue = rs.getObject(i+1);     //33    苹果      5
                    columnValueArr[i]=columnValue;
                }
                return columnValueArr ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("DAO executeComplexQuery 报错了");
        } finally {
            JDBCUtilsQQZoneDb.closeResource(conn,psmt,rs);
        }
        return null ;
    }
    //给预处理命令对象设置参数
    private void setParams(PreparedStatement psmt , Object... params) throws SQLException {
        if(params!=null && params.length>0){
            for (int i = 0; i < params.length; i++) {
                psmt.setObject(i+1,params[i]);
            }
        }
    }
}
