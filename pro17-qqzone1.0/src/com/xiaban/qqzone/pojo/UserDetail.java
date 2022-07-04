package com.xiaban.qqzone.pojo;

import java.util.Date;
import java.time.LocalDate;

public class UserDetail {
    private Integer id;
    private String realName;
    private String tel;
    private String email;
    private Date birth;
    //父类：java.util.Date 年月日时分秒
    //子类：java.sql.Date 年月日
    //子类：java.sql.Time 时分秒
    private String star;
    private String loginId;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public UserDetail(String realName, String tel, String email, Date birth, String star, String loginId) {
        this.realName = realName;
        this.tel = tel;
        this.email = email;
        this.birth = birth;
        this.star = star;
        this.loginId = loginId;
    }

    public UserDetail() {
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }
}
