package com.library_manage.dao;

import com.library_manage.model.BookType;
import com.library_manage.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
* @Description:    图书类别DAO，增删查改
* @Author:         Dong
* @CreateDate:     2018/11/22 18:26
* @UpdateUser:     Dong
* @UpdateDate:     2018/11/22 18:26
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class BookTypeDAO {
    /**
    * @Description: 图书类别添加
     * @author      Dong
    * @param        null
    * @return       int
    * @exception    throws
    * @date         2018/11/22 18:28
    */
    public int Add(Connection con,BookType booktype)throws Exception{
        String sql = "insert into t_book_type value(null,?,?)";
        PreparedStatement pstmt =con.prepareStatement(sql);                 //预编译
        pstmt.setString(1,booktype.getBookTypeName());
        pstmt.setString(2,booktype.getBookTypeDesc());
        return pstmt.executeUpdate();       //操作记录数
    }

    /**
    * @Description: 图书类别查询
     * @author      Dong
    * @param        null
    * @return       ResultSet
    * @exception    throws
    * @date         2018/11/23 10:44
    */
    public ResultSet list(Connection con,BookType booktype)throws Exception {
        StringBuffer sb = new StringBuffer("select * from library_manager_system.t_book_type");
        if (StringUtil.isEmpty(booktype.getBookTypeName())==false) {    //非空
            sb.append(" and book_type_name like '%"+booktype.getBookTypeName()+"%'");       //合并之后一定要记得空格
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));    //预编译,将and替换为where
        return pstmt.executeQuery();                            //返回结果集
    }
    /**
     * @Description:    删除类别
     * @author          Dong
     * @param           null
     * @return          int,删除几条记录
     * @exception
     * @date            2018/11/26 11:17
     */
    public int delete(Connection con,String id)throws Exception{
        String sql = "delete  from library_manager_system.t_book_type where id = ?";
        PreparedStatement pstmt =con.prepareStatement(sql);                  //预编译
        pstmt.setString(1,id);
        return pstmt.executeUpdate();                                       //更新
    }
    /**
    * @Description: 更改类别信息
    * @author       Dong
    * @param        null
    * @return       int，返回操作数量
    * @exception    throws
    * @date         2018/11/26 11:25
    */
    public int update(Connection con,BookType bookType)throws Exception{
        String sql = "update library_manager_system.t_book_type set book_type_name = ?,book_type_desc = ? where id = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,bookType.getBookTypeName());
        pstmt.setString(2,bookType.getBookTypeDesc());
        pstmt.setInt(3,bookType.getId());
        return pstmt.executeUpdate();
    }
}
