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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;

public class BookAdd {
    private JPanel BookAdd;
    private JTextField book_name_text;
    private JTextField book_author_text;
    private JRadioButton author_sex_boy;
    private JRadioButton author_sex_girl;
    private JTextField book_price_text;
    private JComboBox book_type_comboBox;
    private JTextArea book_desc_text;
    private JButton add_button;
    private JButton reset_button;
    private JLabel book_name_label;
    private JLabel book_author_label;
    private JLabel author_sex_label;
    private JLabel book_price_label;
    private JLabel book_type_label;
    private JLabel book_desc_label;
    private DbUtil dbutil = new DbUtil();
    private BookTypeDAO booktypedao = new BookTypeDAO();
    private BookDAO bookdao = new BookDAO();
    private JFrame frame;

    public BookAdd() {
        frame = new JFrame("图书添加");
        frame.setContentPane(BookAdd);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        fillBookType();
        //添加 监听器
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book_Add(e);
            }
        });
        //重置 监听器
        reset_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book_reset_value(e);
            }
        });
    }

    /**
     * @param null
     * @return null
     * @throws null
     * @Description: 填充图书类别下拉框
     * @author Dong
     * @date 2018/11/28 16:28
     */
    private void fillBookType() {
        Connection con = null;
        BookType booktype = null;
        try {
            con = dbutil.getCon();
            ResultSet rs = booktypedao.list(con, new BookType());   //获取值
            while (rs.next()) {
                booktype = new BookType();
                booktype.setId(rs.getInt("id"));
                booktype.setBookTypeName(rs.getString("book_type_name"));
                this.book_type_comboBox.addItem(booktype);          //将ID，NAME都填写进去了，只显示了NAME
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    /**
     * @param ActionEvent
     * @return null
     * @throws null
     * @Description: 添加图书
     * @author Dong
     * @date 2018/11/28 16:32
     */
    private void Book_Add(ActionEvent e) {
        String bookname = this.book_name_text.getText();
        String bookauthor = this.book_author_text.getText();
        String bookprice = this.book_price_text.getText();
        String bookdesc = this.book_desc_text.getText();
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
        if (author_sex_boy.isSelected()) {
            author_sex = "男";
        } else if (author_sex_girl.isSelected()) {
            author_sex = "女";
        }
        BookType booktype = (BookType) book_type_comboBox.getSelectedItem();
        int booktypeid = booktype.getId();
        Book book = new Book(bookname, bookauthor, author_sex, Float.parseFloat(bookprice), booktypeid, bookdesc);
        Connection con = null;
        try {
            con = dbutil.getCon();
            int addnum = bookdao.add(con, book);
            if (addnum == 1) {
                JOptionPane.showMessageDialog(null, "图书添加成功");
                reset_value();
            } else {
                JOptionPane.showMessageDialog(null, "图书添加失败");
            }
        } catch (Exception ee) {
            ee.printStackTrace();
            JOptionPane.showMessageDialog(null, "图书添加失败");
        } finally {
            try {
                dbutil.closeCon(con);
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    /**
     * @param null
     * @return null
     * @throws null
     * @Description: 重置
     * @author Dong
     * @date 2018/11/28 16:35
     */
    private void Book_reset_value(ActionEvent e) {
        reset_value();
    }

    /**
     * @param null
     * @return null
     * @throws null
     * @Description: 重置
     * @author Dong
     * @date 2018/11/28 16:35
     */
    private void reset_value() {
        this.book_name_text.setText("");
        this.book_author_text.setText("");
        this.book_price_text.setText("");
        this.book_desc_text.setText("");
        this.author_sex_boy.setSelected(true);
        if (book_type_comboBox.getItemCount() > 0) {
            this.book_type_comboBox.setSelectedIndex(0);        //设置选中的项
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
        BookAdd = new JPanel();
        BookAdd.setLayout(new com.jgoodies.forms.layout.FormLayout("fill:128px:noGrow,left:41dlu:noGrow,left:35dlu:noGrow,fill:27px:noGrow,left:4dlu:noGrow,fill:84px:noGrow,left:51dlu:noGrow,fill:d:grow", "center:108px:noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:101px:noGrow,top:4dlu:noGrow,center:125px:noGrow,top:4dlu:noGrow,center:76px:noGrow"));
        BookAdd.setBackground(new Color(-6317151));
        BookAdd.setEnabled(true);
        book_name_label = new JLabel();
        Font book_name_labelFont = this.$$$getFont$$$(null, Font.BOLD, 12, book_name_label.getFont());
        if (book_name_labelFont != null) book_name_label.setFont(book_name_labelFont);
        book_name_label.setText("图书名称:");
        com.jgoodies.forms.layout.CellConstraints cc = new com.jgoodies.forms.layout.CellConstraints();
        BookAdd.add(book_name_label, cc.xy(1, 1, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        book_author_label = new JLabel();
        Font book_author_labelFont = this.$$$getFont$$$(null, Font.BOLD, 12, book_author_label.getFont());
        if (book_author_labelFont != null) book_author_label.setFont(book_author_labelFont);
        book_author_label.setText("图书作者:");
        BookAdd.add(book_author_label, cc.xy(6, 1, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        author_sex_label = new JLabel();
        Font author_sex_labelFont = this.$$$getFont$$$(null, Font.BOLD, 12, author_sex_label.getFont());
        if (author_sex_labelFont != null) author_sex_label.setFont(author_sex_labelFont);
        author_sex_label.setText("作者性别:");
        BookAdd.add(author_sex_label, cc.xy(1, 3, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        author_sex_boy = new JRadioButton();
        author_sex_boy.setBackground(new Color(-6317151));
        Font author_sex_boyFont = this.$$$getFont$$$(null, Font.BOLD, 12, author_sex_boy.getFont());
        if (author_sex_boyFont != null) author_sex_boy.setFont(author_sex_boyFont);
        author_sex_boy.setSelected(true);
        author_sex_boy.setText("男");
        BookAdd.add(author_sex_boy, cc.xy(2, 3, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        book_price_label = new JLabel();
        Font book_price_labelFont = this.$$$getFont$$$(null, Font.BOLD, 12, book_price_label.getFont());
        if (book_price_labelFont != null) book_price_label.setFont(book_price_labelFont);
        book_price_label.setText("图书价格:");
        BookAdd.add(book_price_label, cc.xy(6, 3, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        book_type_label = new JLabel();
        Font book_type_labelFont = this.$$$getFont$$$(null, Font.BOLD, 12, book_type_label.getFont());
        if (book_type_labelFont != null) book_type_label.setFont(book_type_labelFont);
        book_type_label.setText("图书类别:");
        BookAdd.add(book_type_label, cc.xy(1, 5, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        book_type_comboBox = new JComboBox();
        book_type_comboBox.setToolTipText("123;\n456;\n789");
        BookAdd.add(book_type_comboBox, cc.xy(2, 5, com.jgoodies.forms.layout.CellConstraints.FILL, com.jgoodies.forms.layout.CellConstraints.CENTER));
        book_desc_label = new JLabel();
        Font book_desc_labelFont = this.$$$getFont$$$(null, Font.BOLD, 12, book_desc_label.getFont());
        if (book_desc_labelFont != null) book_desc_label.setFont(book_desc_labelFont);
        book_desc_label.setText("图书描述:");
        BookAdd.add(book_desc_label, cc.xy(1, 7, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.CENTER));
        book_desc_text = new JTextArea();
        BookAdd.add(book_desc_text, new com.jgoodies.forms.layout.CellConstraints(2, 7, 6, 1, com.jgoodies.forms.layout.CellConstraints.FILL, com.jgoodies.forms.layout.CellConstraints.FILL, new Insets(0, 0, 0, 30)));
        book_author_text = new JTextField();
        BookAdd.add(book_author_text, new com.jgoodies.forms.layout.CellConstraints(7, 1, 1, 1, com.jgoodies.forms.layout.CellConstraints.FILL, com.jgoodies.forms.layout.CellConstraints.DEFAULT, new Insets(0, 0, 0, 30)));
        book_price_text = new JTextField();
        BookAdd.add(book_price_text, new com.jgoodies.forms.layout.CellConstraints(7, 3, 1, 1, com.jgoodies.forms.layout.CellConstraints.FILL, com.jgoodies.forms.layout.CellConstraints.DEFAULT, new Insets(0, 0, 0, 30)));
        author_sex_girl = new JRadioButton();
        author_sex_girl.setBackground(new Color(-6317151));
        Font author_sex_girlFont = this.$$$getFont$$$(null, Font.BOLD, 12, author_sex_girl.getFont());
        if (author_sex_girlFont != null) author_sex_girl.setFont(author_sex_girlFont);
        author_sex_girl.setText("女");
        BookAdd.add(author_sex_girl, cc.xy(3, 3, com.jgoodies.forms.layout.CellConstraints.CENTER, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        book_name_text = new JTextField();
        book_name_text.setBackground(new Color(-855310));
        book_name_text.setText("");
        BookAdd.add(book_name_text, cc.xyw(2, 1, 2, com.jgoodies.forms.layout.CellConstraints.FILL, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        add_button = new JButton();
        add_button.setIcon(new ImageIcon(getClass().getResource("/image/add_16px.png")));
        add_button.setText("添加");
        BookAdd.add(add_button, cc.xy(2, 9, com.jgoodies.forms.layout.CellConstraints.FILL, com.jgoodies.forms.layout.CellConstraints.DEFAULT));
        reset_button = new JButton();
        reset_button.setIcon(new ImageIcon(getClass().getResource("/image/Restart.png")));
        reset_button.setText("重置");
        BookAdd.add(reset_button, cc.xy(6, 9));
        ButtonGroup buttonGroup;
        buttonGroup = new ButtonGroup();
        buttonGroup.add(author_sex_boy);
        buttonGroup.add(author_sex_girl);
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
        return BookAdd;
    }

}
