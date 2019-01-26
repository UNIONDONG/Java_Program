package com.library_manage.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
* @Description:    数据库管理类
* @Author:         Dong
* @CreateDate:     2018/11/20 11:01
* @UpdateUser:     Dong
* @UpdateDate:     2018/11/20 11:01
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class DbUtil {
    private String dbUrl = "jdbc:mysql://localhost:3306/library_manager_system?serverTimezone=GMT%2B8";  //数据库连接地址，URL要访问的数据库名
    private String dbUserName = "root";                             //账户
    private String dbPassword = "upre";                             //密码
    private String jdbc_driver = "com.mysql.cj.jdbc.Driver";        //驱动名

    /**
    * @Description: 连接数据库
    * @author       Dong
    * @param        null
    * @return       Connection
    * @exception    throws
    * @date         2018/11/20 11:05
    */
    public Connection getCon() throws Exception{                    //在这里，直接抛出异常，不做处理
        Class.forName(jdbc_driver);                               //显示加载JDBC驱动程序
        Connection con = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);  //connection创建对象保存了所要连接数据库的相关信息
        return con;                                                 //getConnection，加载驱动程序，建立数据库的连接
    }
    /**
    * @Description: 关闭数据库
    * @author       Dong
    * @param        null
    * @return       void
    * @exception    throws
    * @date         2018/11/20 11:06
    */
    public void closeCon(Connection con) throws Exception{
        if(con != null)
            con.close();
    }
}

