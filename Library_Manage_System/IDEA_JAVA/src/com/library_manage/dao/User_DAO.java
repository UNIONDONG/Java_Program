package com.library_manage.dao;

import com.library_manage.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
* @Description:    登陆验证，DAU:（Database Access Object）用户数据访问对象
* @Author:         Dong
* @CreateDate:     2018/11/20 9:57
* @UpdateUser:     Dong
* @UpdateDate:     2018/11/20 9:57
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class User_DAO {
    public User login(Connection con,User user) throws Exception{
        User resultUser = null;
        String sql = "select * from library_manager_system.db_user where user_name = ? and password = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);    //执行数据库的操作
        pstmt.setString(1,user.getUsername());   //赋值给上面的'？'
        pstmt.setString(2,user.getPassword());
        ResultSet rs = pstmt.executeQuery();    //查找结果

        if(rs.next()){                          //是否有下一条记录
            resultUser = new User();
            resultUser.setId(rs.getInt("id"));
            resultUser.setUsername(rs.getString("user_name"));
            resultUser.setPassword(rs.getString("password"));
        }
        return resultUser;
    }
}
