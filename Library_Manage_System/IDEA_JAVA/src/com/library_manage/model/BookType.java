package com.library_manage.model;

/**
* @Description:    图书类别实体
* @Author:         Dong
* @CreateDate:     2018/11/22 18:23
* @UpdateUser:     Dong
* @UpdateDate:     2018/11/22 18:23
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class BookType {
    private int id;                 //编号
    private String bookTypeName;    //图书类别名称
    private String bookTypeDesc;    //备注

    public BookType() {
    }

    public BookType(String bookTypeName, String bookTypeDesc) {
        this.bookTypeName = bookTypeName;
        this.bookTypeDesc = bookTypeDesc;
    }

    public BookType(int id, String bookTypeName, String bookTypeDesc) {
        this.id = id;
        this.bookTypeName = bookTypeName;
        this.bookTypeDesc = bookTypeDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookTypeName() {
        return bookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName = bookTypeName;
    }

    public String getBookTypeDesc() {
        return bookTypeDesc;
    }

    public void setBookTypeDesc(String bookTypeDesc) {
        this.bookTypeDesc = bookTypeDesc;
    }

    @Override
    public String toString() {
        return bookTypeName;
    }
}
