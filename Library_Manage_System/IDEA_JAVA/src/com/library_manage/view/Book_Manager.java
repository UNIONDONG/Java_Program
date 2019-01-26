package com.library_manage.view;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.library_manage.dao.BookDAO;
import com.library_manage.dao.BookTypeDAO;
import com.library_manage.model.Book;
import com.library_manage.model.BookType;
import com.library_manage.util.DbUtil;
import com.library_manage.util.StringUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;

public class Book_Manager {
    private JPanel Book_Manager;
    private JPanel BookManager;
    private JScrollPane scrollpane;
    private JTable book_data_table;
    private JLabel BN_lable;
    private JTextField BN_text;
    private JButton Query_butt;
    private JPanel table_option;
    private JLabel ID_label;
    private JTextField id_text;
    private JLabel BN_label2;
    private JTextField BN_text2;
    private JTextArea desc_text_area;
    private JLabel desc_label;
    private JButton modify_butt;
    private JButton delete_butt;
    private JTextField BN_Author_text;
    private JComboBox BN_comboBox;
    private JRadioButton Choose_Boy;
    private JRadioButton Choose_Girl;
    private JTextField Price_text;
    private JTextField BA_text2;
    private JComboBox BT_comboBox2;
    private JLabel BA_label;
    private JLabel BT_label;
    private JLabel AS_label;
    private JLabel Price_label;
    private JLabel BA_label2;
    private JLabel BT_label2;
    private DbUtil dbutil = new DbUtil();
    private BookTypeDAO booktypedao = new BookTypeDAO();
    private BookDAO bookdao = new BookDAO();
    private JFrame frame;

    /**
     * @param null
     * @return null
     * @throws null
     * @Description: 构造函数
     * @author Dong
     * @date 2018/11/28 16:37
     */
    public Book_Manager() {
        frame = new JFrame("图书维护");
        frame.setContentPane(Book_Manager);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        fillTable(new Book());
        this.Fill_Book_Type("search");
        this.Fill_Book_Type("modify");

        //查询事件
        Query_butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book_Search_Act_Pfm(e);
            }
        });
        //鼠标点击表格 事件
        book_data_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Book_Table_Click(e);
            }
        });
        //修改 监听器
        modify_butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book_Update_Act_Pfm(e);
            }
        });
        //删除 监听器
        delete_butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book_Delete_Act_Pfm(e);
            }
        });
    }

    /**
     * @param Book
     * @return null
     * @throws null
     * @Description: 填充表格
     * @author Dong
     * @date 2018/11/28 16:38
     */
    private void fillTable(Book book) {
        String table_head[] = {"编号", "图书名称", "图书作者", "作者性别", "图书价格", "图书描述", "图书类别"};
        String[][] table_datas = {{"", "", "", "", "", "", ""}};
        String[] table_datass = {"", "", "", "", "", "", ""};
        DefaultTableModel dtm = new DefaultTableModel(table_datas, table_head);
        dtm.setRowCount(0);             //设置成0行
        Connection con = null;
        try {
            con = dbutil.getCon();
            ResultSet rs = bookdao.list(con, book);
            while (rs.next()) {
                table_datass[0] = rs.getString("id");
                table_datass[1] = rs.getString("book_name");
                table_datass[2] = rs.getString("author");
                table_datass[3] = rs.getString("sex");
                table_datass[4] = rs.getString("price");
                table_datass[5] = rs.getString("book_type_desc");
                table_datass[6] = rs.getString("book_type_name");
                dtm.addRow(table_datass);
            }
            book_data_table.setModel(dtm);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbutil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param String
     * @return null
     * @throws null
     * @Description: 填充下拉框
     * @author Dong
     * @date 2018/11/28 16:39
     */
    private void Fill_Book_Type(String type) {
        Connection con = null;
        try {
            con = dbutil.getCon();
            BookType booktype = null;
            ResultSet rs = booktypedao.list(con, new BookType());
            if ("search".equals(type)) {
                booktype = new BookType();
                booktype.setBookTypeName("请选择...");
                booktype.setId(-1);
                this.BN_comboBox.addItem(booktype);
            }
            while (rs.next()) {
                booktype = new BookType();
                booktype.setBookTypeName(rs.getString("book_type_name"));
                booktype.setId(rs.getInt("id"));
                if ("search".equals(type)) {
                    this.BN_comboBox.addItem(booktype);
                } else if ("modify".equals(type)) {
                    this.BT_comboBox2.addItem(booktype);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbutil.closeCon(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param ActionEvent
     * @return null
     * @throws null
     * @Description: 图书查询
     * @author Dong
     * @date 2018/11/28 16:40
     */
    private void Book_Search_Act_Pfm(ActionEvent e) {
        String bookname = this.BN_text.getText();
        String bookauthor = this.BN_Author_text.getText();
        BookType booktype = (BookType) this.BN_comboBox.getSelectedItem();
        int boodid = booktype.getId();
        Book book = new Book(bookname, bookauthor, boodid);
        this.fillTable(book);
    }

    /**
     * @param MouseEvent
     * @return null
     * @throws null
     * @Description: 鼠标点击事件
     * @author Dong
     * @date 2018/11/28 16:41
     */
    private void Book_Table_Click(MouseEvent e) {
        int select_row = this.book_data_table.getSelectedRow();     //获取选中的行
        this.id_text.setText((String) book_data_table.getValueAt(select_row, 0));
        this.BN_text2.setText((String) book_data_table.getValueAt(select_row, 1));
        this.BA_text2.setText((String) book_data_table.getValueAt(select_row, 2));
        String sex = (String) book_data_table.getValueAt(select_row, 3);
        if (sex.equals("男")) {
            this.Choose_Boy.setSelected(true);
        } else if (sex.equals("女")) {
            this.Choose_Girl.setSelected(true);
        }
        this.Price_text.setText((String) book_data_table.getValueAt(select_row, 4) + "");
        this.desc_text_area.setText((String) book_data_table.getValueAt(select_row, 5));
        String booktypename = (String) book_data_table.getValueAt(select_row, 6);
        int n = this.BT_comboBox2.getItemCount();           //得到多少个项
        for (int i = 0; i < n; i++) {
            BookType item = (BookType) this.BT_comboBox2.getItemAt(i);
            if (item.getBookTypeName().equals(booktypename)) {
                this.BT_comboBox2.setSelectedIndex(i);
            }
        }
    }

    /**
     * @param ActionEvent
     * @return null
     * @throws null
     * @Description: 图书修改信息
     * @author Dong
     * @date 2018/11/28 16:42
     */
    private void Book_Update_Act_Pfm(ActionEvent e) {
        String id = this.id_text.getText();
        if (StringUtil.isEmpty(id)) {
            JOptionPane.showMessageDialog(null, "请选择要修改的记录!");
            return;
        }
        String bookname = this.BN_text2.getText();
        String bookauthor = this.BA_text2.getText();
        String bookprice = this.Price_text.getText();
        String bookdesc = this.desc_text_area.getText();
        if (StringUtil.isEmpty(bookname)) {
            JOptionPane.showMessageDialog(null, "图书名称不能为空!");
            return;
        } else if (StringUtil.isEmpty(bookauthor)) {
            JOptionPane.showMessageDialog(null, "图书作者不能为空!");
            return;
        } else if (StringUtil.isEmpty(bookprice)) {
            JOptionPane.showMessageDialog(null, "图书价格不能为空!");
            return;
        }
        String author_sex = "";
        if (Choose_Boy.isSelected()) {
            author_sex = "男";
        } else if (Choose_Girl.isSelected()) {
            author_sex = "女";
        }
        BookType booktype = (BookType) BT_comboBox2.getSelectedItem();        //获取下拉框
        int booktypeid = booktype.getId();
        Book book = new Book(Integer.parseInt(id), bookname, bookauthor, author_sex, Float.parseFloat(bookprice), booktypeid, bookdesc);
        Connection con = null;
        try {
            con = dbutil.getCon();
            int updatenum = bookdao.update(con, book);
            if (updatenum == 1) {
                JOptionPane.showMessageDialog(null, "图书修改成功");
                reset_value();
                this.fillTable(new Book());
            } else {
                JOptionPane.showMessageDialog(null, "图书修改失败");

            }
        } catch (Exception ee) {
            ee.printStackTrace();
            JOptionPane.showMessageDialog(null, "图书修改失败");
        } finally {
            try {
                dbutil.closeCon(con);
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    /**
     * @param ActionEvent
     * @return null
     * @throws null
     * @Description: 删除图书信息
     * @author Dong
     * @date 2018/11/28 16:42
     */
    private void Book_Delete_Act_Pfm(ActionEvent e) {
        String id = id_text.getText();
        if (StringUtil.isEmpty(id)) {
            JOptionPane.showMessageDialog(null, "请选择要删除的记录!");
            return;
        }
        int n = JOptionPane.showConfirmDialog(null, "确定要删除的记录吗?");
        if (n == 0) {
            Connection con = null;
            try {
                con = dbutil.getCon();
                int deletenum = bookdao.delete(con, id);
                if (deletenum == 1) {
                    JOptionPane.showMessageDialog(null, "删除成功！");
                    this.reset_value();
                    this.fillTable(new Book());
                } else {
                    JOptionPane.showMessageDialog(null, "删除失败！");
                }
            } catch (Exception ee) {
                ee.printStackTrace();
                JOptionPane.showMessageDialog(null, "删除失败！");
            } finally {
                try {
                    con.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
    }

    /**
     * @param null
     * @return null
     * @throws null
     * @Description: 重置
     * @author Dong
     * @date 2018/11/28 16:43
     */
    private void reset_value() {
        this.id_text.setText("");
        this.BN_text2.setText("");
        this.Price_text.setText("");
        this.BA_text2.setText("");
        this.Choose_Boy.setSelected(true);
        this.desc_text_area.setText("");
        if (this.BT_comboBox2.getItemCount() > 0) {
            this.BT_comboBox2.setSelectedIndex(0);
        }

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        Book_Manager = new JPanel();
        Book_Manager.setLayout(new com.jgoodies.forms.layout.FormLayout("fill:d:grow", "center:max(d;4px):noGrow"));
        Book_Manager.setEnabled(false);
        Book_Manager.setBorder(BorderFactory.createTitledBorder(null, "搜索查询", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, Font.BOLD, 12, Book_Manager.getFont())));
        BookManager = new JPanel();
        BookManager.setLayout(new com.jgoodies.forms.layout.FormLayout("fill:14px:grow,left:40dlu:noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,fill:19px:grow,left:4dlu:noGrow,fill:43px:grow,left:4dlu:noGrow,fill:61px:noGrow,left:4dlu:noGrow,fill:87px:noGrow,left:4dlu:noGrow,fill:d:grow", "center:52px:noGrow,top:4dlu:noGrow,center:136px:noGrow,top:5dlu:noGrow,center:155px:noGrow,top:4dlu:noGrow,center:66px:noGrow"));
        BookManager.setAutoscrolls(false);
        BookManager.setBackground(new Color(-6317151));
        BookManager.setEnabled(true);
        BookManager.setFocusCycleRoot(false);
        BookManager.setVerifyInputWhenFocusTarget(true);
        BookManager.setVisible(true);
        com.jgoodies.forms.layout.CellConstraints cc = new com.jgoodies.forms.layout.CellConstraints();
        Book_Manager.add(BookManager, cc.xy(1, 1));
        scrollpane = new JScrollPane();
        scrollpane.setBackground(new Color(-16056313));
        scrollpane.setEnabled(false);
        scrollpane.setForeground(new Color(-6317151));
        BookManager.add(scrollpane, cc.xyw(1, 3, 17, com.jgoodies.forms.layout.CellConstraints.FILL, com.jgoodies.forms.layout.CellConstraints.FILL));
        scrollpane.setBorder(BorderFactory.createTitledBorder(""));
        book_data_table = new JTable();
        book_data_table.setAutoCreateRowSorter(true);
        book_data_table.setAutoResizeMode(1);
        book_data_table.setBackground(new Color(-855310));
        book_data_table.setDropMode(DropMode.USE_SELECTION);
        book_data_table.setEnabled(true);
        book_data_table.setFillsViewportHeight(false);
        book_data_table.setForeground(new Color(-16056313));
        book_data_table.setGridColor(new Color(-7388963));
        book_data_table.setInheritsPopupMenu(true);
        book_data_table.setSelectionBackground(new Color(-15076847));
        book_data_table.setShowHorizontalLines(true);
        book_data_table.setVisible(true);
        book_data_table.putClientProperty("JTable.autoStartsEdit", Boolean.TRUE);
        scrollpane.setViewportView(book_data_table);
        BN_lable = new JLabel();
        Font BN_lableFont = this.$$$getFont$$$(null, Font.BOLD, 12, BN_lable.getFont());
        if (BN_lableFont != null) BN_lable.setFont(BN_lableFont);
        BN_lable.setText("图书名称:");
        BookManager.add(BN_lable, cc.xy(1, 1, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        Query_butt = new JButton();
        Font Query_buttFont = this.$$$getFont$$$(null, Font.BOLD, 12, Query_butt.getFont());
        if (Query_buttFont != null) Query_butt.setFont(Query_buttFont);
        Query_butt.setText("查询");
        BookManager.add(Query_butt, cc.xy(17, 1, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        table_option = new JPanel();
        table_option.setLayout(new com.jgoodies.forms.layout.FormLayout("fill:64px:noGrow,left:45dlu:noGrow,fill:112px:noGrow,left:50dlu:noGrow,left:4dlu:noGrow,fill:63px:noGrow,left:4dlu:noGrow,fill:55px:noGrow,left:4dlu:noGrow,fill:max(d;4px):noGrow,left:4dlu:noGrow,fill:d:grow", "center:36px:noGrow,top:4dlu:noGrow,center:46px:noGrow,top:4dlu:noGrow,center:45px:noGrow"));
        table_option.setBackground(new Color(-6317151));
        BookManager.add(table_option, cc.xywh(1, 5, 17, 2, com.jgoodies.forms.layout.CellConstraints.DEFAULT, com.jgoodies.forms.layout.CellConstraints.FILL));
        table_option.setBorder(BorderFactory.createTitledBorder(null, "表单操作", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, Font.BOLD, 12, table_option.getFont())));
        ID_label = new JLabel();
        Font ID_labelFont = this.$$$getFont$$$(null, Font.BOLD, 12, ID_label.getFont());
        if (ID_labelFont != null) ID_label.setFont(ID_labelFont);
        ID_label.setText("编号：");
        table_option.add(ID_label, cc.xy(1, 1, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        id_text = new JTextField();
        id_text.setEditable(false);
        id_text.setEnabled(true);
        table_option.add(id_text, cc.xy(2, 1, com.jgoodies.forms.layout.CellConstraints.FILL, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        BN_label2 = new JLabel();
        Font BN_label2Font = this.$$$getFont$$$(null, Font.BOLD, 12, BN_label2.getFont());
        if (BN_label2Font != null) BN_label2.setFont(BN_label2Font);
        BN_label2.setText("图书名称：");
        table_option.add(BN_label2, cc.xy(3, 1, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        AS_label = new JLabel();
        Font AS_labelFont = this.$$$getFont$$$(null, Font.BOLD, 12, AS_label.getFont());
        if (AS_labelFont != null) AS_label.setFont(AS_labelFont);
        AS_label.setText("作者性别:");
        table_option.add(AS_label, cc.xy(6, 1, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        Choose_Boy = new JRadioButton();
        Choose_Boy.setBackground(new Color(-6317151));
        Font Choose_BoyFont = this.$$$getFont$$$(null, Font.BOLD, 12, Choose_Boy.getFont());
        if (Choose_BoyFont != null) Choose_Boy.setFont(Choose_BoyFont);
        Choose_Boy.setSelected(true);
        Choose_Boy.setText("男");
        table_option.add(Choose_Boy, cc.xy(8, 1, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        Choose_Girl = new JRadioButton();
        Choose_Girl.setBackground(new Color(-6317151));
        Font Choose_GirlFont = this.$$$getFont$$$(null, Font.BOLD, 12, Choose_Girl.getFont());
        if (Choose_GirlFont != null) Choose_Girl.setFont(Choose_GirlFont);
        Choose_Girl.setText("女");
        table_option.add(Choose_Girl, cc.xy(12, 1, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        Price_label = new JLabel();
        Font Price_labelFont = this.$$$getFont$$$(null, Font.BOLD, 12, Price_label.getFont());
        if (Price_labelFont != null) Price_label.setFont(Price_labelFont);
        Price_label.setText("价格:");
        table_option.add(Price_label, cc.xy(1, 3, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        desc_label = new JLabel();
        Font desc_labelFont = this.$$$getFont$$$(null, Font.BOLD, 12, desc_label.getFont());
        if (desc_labelFont != null) desc_label.setFont(desc_labelFont);
        desc_label.setText("图书描述：");
        table_option.add(desc_label, cc.xy(1, 5, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        desc_text_area = new JTextArea();
        table_option.add(desc_text_area, new com.jgoodies.forms.layout.CellConstraints(2, 4, 10, 2, com.jgoodies.forms.layout.CellConstraints.FILL, com.jgoodies.forms.layout.CellConstraints.FILL, new Insets(0, 0, 0, 50)));
        Price_text = new JTextField();
        table_option.add(Price_text, cc.xy(2, 3, com.jgoodies.forms.layout.CellConstraints.FILL, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        BA_label2 = new JLabel();
        Font BA_label2Font = this.$$$getFont$$$(null, Font.BOLD, 12, BA_label2.getFont());
        if (BA_label2Font != null) BA_label2.setFont(BA_label2Font);
        BA_label2.setText("图书作者:");
        table_option.add(BA_label2, cc.xy(3, 3, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        BA_text2 = new JTextField();
        table_option.add(BA_text2, cc.xy(4, 3, com.jgoodies.forms.layout.CellConstraints.FILL, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        BT_label2 = new JLabel();
        Font BT_label2Font = this.$$$getFont$$$(null, Font.BOLD, 12, BT_label2.getFont());
        if (BT_label2Font != null) BT_label2.setFont(BT_label2Font);
        BT_label2.setText("图书类别:");
        table_option.add(BT_label2, cc.xy(6, 3));
        BT_comboBox2 = new JComboBox();
        table_option.add(BT_comboBox2, cc.xyw(8, 3, 4));
        BN_text2 = new JTextField();
        BN_text2.setEditable(true);
        table_option.add(BN_text2, new com.jgoodies.forms.layout.CellConstraints(4, 1, 1, 1, com.jgoodies.forms.layout.CellConstraints.FILL, com.jgoodies.forms.layout.CellConstraints.CENTER, new Insets(0, 0, 0, 50)));
        BN_text = new JTextField();
        BookManager.add(BN_text, cc.xy(2, 1, com.jgoodies.forms.layout.CellConstraints.FILL, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        BA_label = new JLabel();
        Font BA_labelFont = this.$$$getFont$$$(null, Font.BOLD, 12, BA_label.getFont());
        if (BA_labelFont != null) BA_label.setFont(BA_labelFont);
        BA_label.setText("图书作者:");
        BookManager.add(BA_label, cc.xy(9, 1, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        BT_label = new JLabel();
        Font BT_labelFont = this.$$$getFont$$$(null, Font.BOLD, 12, BT_label.getFont());
        if (BT_labelFont != null) BT_label.setFont(BT_labelFont);
        BT_label.setText("图书类别:");
        BookManager.add(BT_label, cc.xy(13, 1, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        BN_comboBox = new JComboBox();
        BookManager.add(BN_comboBox, cc.xy(15, 1));
        BN_Author_text = new JTextField();
        BookManager.add(BN_Author_text, cc.xy(11, 1, com.jgoodies.forms.layout.CellConstraints.FILL, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        delete_butt = new JButton();
        Font delete_buttFont = this.$$$getFont$$$(null, Font.BOLD, 12, delete_butt.getFont());
        if (delete_buttFont != null) delete_butt.setFont(delete_buttFont);
        delete_butt.setIcon(new ImageIcon(getClass().getResource("/image/delete_remove_16px.png")));
        delete_butt.setText("删除");
        BookManager.add(delete_butt, new com.jgoodies.forms.layout.CellConstraints(13, 7, 3, 1, com.jgoodies.forms.layout.CellConstraints.RIGHT, com.jgoodies.forms.layout.CellConstraints.CENTER, new Insets(0, 0, 0, 80)));
        modify_butt = new JButton();
        Font modify_buttFont = this.$$$getFont$$$(null, Font.BOLD, 12, modify_butt.getFont());
        if (modify_buttFont != null) modify_butt.setFont(modify_buttFont);
        modify_butt.setIcon(new ImageIcon(getClass().getResource("/image/Modify_16px.png")));
        modify_butt.setText("修改");
        BookManager.add(modify_butt, cc.xyw(4, 7, 3, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(Choose_Boy);
        buttonGroup.add(Choose_Girl);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return Book_Manager;
    }

}