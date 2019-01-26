package com.library_manage.dao;

import com.library_manage.model.Book;
import com.library_manage.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
* @Description:    图书信息类,增删查改
* @Author:         Dong
* @CreateDate:     2018/11/28 14:51
* @UpdateUser:     Dong
* @UpdateDate:     2018/11/28 14:51
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class BookDAO {
    /**
    * @Description: 添加图书
     * @author      Dong
    * @param        null
    * @return       int
    * @exception    throw
    * @date         2018/11/27 15:29
    */
    public int add(Connection con, Book book)throws Exception{
        String sql = "insert into library_manager_system.t_book value(null,?,?,?,?,?,?)";   //前面写成空，因为数据库中表格设置为自增，不设置为自增的话，该处报错
        PreparedStatement pstm = con.prepareStatement(sql);             //预处理
        pstm.setString(1,book.getBookName());
        pstm.setString(2,book.getAuthor());
        pstm.setString(3,book.getSex());
        pstm.setFloat(4,book.getPrice());
        pstm.setInt(5,book.getBookTypeId());
        pstm.setString(6,book.getBookDesc());
        return pstm.executeUpdate();
    }
    /**
    * @Description: 查询图书
     * @author      Dong
    * @param        null
    * @return       ResultSet
    * @exception    throws
    * @date         2018/11/27 15:30
    */
    public ResultSet list(Connection con,Book book)throws Exception{
        StringBuffer sb = new StringBuffer("select * from library_manager_system.t_book b,library_manager_system.t_book_type bt where b.book_type_id = bt.id");
        if(StringUtil.isEmpty(book.getBookName()) == false){
            sb.append(" and b.bookname like '%"+book.getBookName() +"%'");          //%%内部为查询是否有相关字符
        }
        if(StringUtil.isEmpty(book.getAuthor()) == false){
            sb.append(" and b.author like '%"+book.getAuthor() +"%'");
        }
        if(book.getBookTypeId() != null && book.getBookTypeId() != -1){
            sb.append(" and b.book_type_id like '%"+book.getBookTypeId() +"%'");
        }
        //这是及时处理SQL语句
        Statement stm = con.createStatement();                                      //返回结果集
        return stm.executeQuery(sb.toString());
        //这是预处理SQL 语句
        /*PreparedStatement pstmt = con.prepareStatement(sb.toString());
        return pstmt.executeQuery();                                                //返回结果集*/
    }
    /**
    * @Description: 删除图书
    * @author       Dong
    * @param        null
    * @return       int
    * @exception    throws
    * @date         2018/11/27 17:27
    */
    public int delete(Connection con,String id)throws Exception{
        //这是另一种处理方法，与上面类似，上面是存在了StringBuffer中，这里是存在了PreparedStatement中
        String sql = "delete from library_manager_system.t_book where id =? ";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,id);
        return pstmt.executeUpdate();
    }
    /**
    * @Description: 图书信息修改
     * @author      Dong
    * @param        null
    * @return       int
    * @exception    throws
    * @date         2018/11/27 17:29
    */
    public int update(Connection con,Book book)throws Exception{
        String sql = "update library_manager_system.t_book set book_name = ?,author = ?,sex = ?,price = ?,book_type_id = ?,book_type_desc = ? where id = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,book.getBookName());
        pstmt.setString(2,book.getAuthor());
        pstmt.setString(3,book.getSex());
        pstmt.setFloat(4,book.getPrice());
        pstmt.setInt(5,book.getBookTypeId());
        pstmt.setString(6,book.getBookDesc());
        pstmt.setInt(7,book.getId());
        return pstmt.executeUpdate();
    }
    /**
    * @Description: 判断图书类别下是否有图书
     * @author      Dong
    * @param        null
    * @return       boolean
    * @exception    throws
    * @date         2018/11/27 19:42
    */
    public boolean existBook_By_BookTypeId(Connection con,String booktypeid)throws Exception{
        String sql = "select * from t_book where book_type_id = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,booktypeid);
        ResultSet rs = pstmt.executeQuery();
        return rs.next();   //有为 true ，没有为false
    }
}














