package com.library_manage.model;

public class Book {
    private int id;                     //编号
    private String bookName;            //书名
    private String author;              //作者
    private String sex;                 //性别
    private float price;                //价格
    private Integer bookTypeId;         //图书类别ID
    private String book_Type_Name;      //图书类别名称
    private String bookDesc;            //图书备注

    public Book() {
    }

    public Book(String bookName, String author, Integer bookTypeId) {
        this.bookName = bookName;
        this.author = author;
        this.bookTypeId = bookTypeId;
    }

    public Book(int id, String bookName, String author, String sex, float price, Integer bookTypeId, String bookDesc) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.sex = sex;
        this.price = price;
        this.bookTypeId = bookTypeId;
        this.bookDesc = bookDesc;
    }

    public Book(String bookName, String author, String sex, float price, Integer bookTypeId, String bookDesc) {
        this.bookName = bookName;
        this.author = author;
        this.sex = sex;
        this.price = price;
        this.bookTypeId = bookTypeId;
        this.bookDesc = bookDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Integer getBookTypeId() {
        return bookTypeId;
    }

    public void setBookTypeId(Integer bookTypeId) {
        this.bookTypeId = bookTypeId;
    }

    public String getBook_Type_Name() {
        return book_Type_Name;
    }

    public void setBook_Type_Name(String book_Type_Name) {
        this.book_Type_Name = book_Type_Name;
    }

    public String getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc;
    }
}
