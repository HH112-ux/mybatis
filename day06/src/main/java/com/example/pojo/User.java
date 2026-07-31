package com.example.pojo;

import java.util.Date;

public class User {

    private Integer uid;
    private String uname;
    private String pwd;
    private Date lasttime;

    public Integer getUid() { return uid; }
    public void setUid(Integer uid) { this.uid = uid; }

    public String getUname() { return uname; }
    public void setUname(String uname) { this.uname = uname; }

    public String getPwd() { return pwd; }
    public void setPwd(String pwd) { this.pwd = pwd; }

    public Date getLasttime() { return lasttime; }
    public void setLasttime(Date lasttime) { this.lasttime = lasttime; }

    @Override
    public String toString() {
        return "User{uid=" + uid + ", uname='" + uname + "', pwd='" + pwd + "', lasttime=" + lasttime + '}';
    }
}
