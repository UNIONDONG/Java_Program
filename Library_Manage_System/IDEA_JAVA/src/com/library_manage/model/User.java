package com.library_manage.model;
/**
* @Description:    用户实体
* @Author:         Dong
* @CreateDate:     2018/11/20 10:59
* @UpdateUser:     Dong
* @UpdateDate:     2018/11/20 10:59
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class User {
    private int id;             //编号
    private String username;    //用户名
    private String password;    //密码
    public User() {}
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
