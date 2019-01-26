package com.library_manage.view;


import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.library_manage.dao.BookDAO;
import com.library_manage.dao.BookTypeDAO;
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

public class Book_Type_Manager {
    private JPanel BookTypeManager;
    private JScrollPane scrollpane;
    private JTable book_data_table;
    private JTextField BTN_text_field;
    private JButton Query_butt;
    private JLabel BTN_lable;
    private JPanel table_option;
    private JTextField id_textField;
    private JTextField btn_textField;
    private JTextArea desc_text_area;
    private JButton modify_butt;
    private JButton delete_butt;
    private JLabel book_type_name_lable;
    private JLabel desc_label;
    private JLabel ID_label;
    private JFrame frame;
    private DbUtil dbutil = new DbUtil();
    private BookTypeDAO booktypedao = new BookTypeDAO();
    private BookDAO bookdao = new BookDAO();

    public Book_Type_Manager() {
        frame = new JFrame("图书类别维护");
        frame.setContentPane(BookTypeManager);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.fillTable(new BookType());
        //查询按钮 监听器
        Query_butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book_Type_Search_Action_Performed(e);
            }
        });
        //鼠标点击事件，点击表格
        book_data_table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                book_type_name_mouse_press(e);
            }
        });
        //修改 监听器
        modify_butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                book_type_update_action_event(e);
            }
        });
        //删除 监听器
        delete_butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                book_type_delete_action_event(e);
            }
        });
    }

    /**
    * @Description: 填充表单
     * @author      Dong
    * @param        BookType
    * @return       null
    * @exception    null
    * @date         2018/11/28 16:21
    */
    private void fillTable(BookType booktype) {
        String table_head[] = {"编号", "图书类别名称", "图书类别描述"};
        String[][] table_datas = {{"", "", ""}};
        String[] table_datass = {"", "", ""};
        DefaultTableModel dtm = new DefaultTableModel(table_datas, table_head);
        //DefaultTableModel dtm = (DefaultTableModel) book_data_table.getModel();
        dtm.setRowCount(0);             //设置成0行
        Connection con = null;
        try {
            con = dbutil.getCon();
            ResultSet rs = booktypedao.list(con, booktype);
            while (rs.next()) {
                table_datass[0] = rs.getString("id");
                table_datass[1] = rs.getString("book_type_name");
                table_datass[2] = rs.getString("book_type_desc");
                /*Vector v = new Vector();
                v.add(rs.getString("id"));
                v.add(rs.getString("book_type_name"));
                v.add(rs.getString("book_type_desc"));
                System.out.print(v);
                dtm.addRow(v);*/
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
    * @Description: 重置表单
     * @author      Dong
    * @param        null
    * @return       null
    * @exception    null
    * @date         2018/11/28 16:25
    */
    private void Reset_Value() {
        this.id_textField.setText("");
        this.btn_textField.setText("");
        this.desc_text_area.setText("");
    }

    /**
    * @Description: 图书类别搜索事件
     * @author      Dong
    * @param        ActionEvent
    * @return       null
    * @exception    null
    * @date         2018/11/28 16:25
    */
    private void Book_Type_Search_Action_Performed(ActionEvent e) {
        String s_book_type_name = this.BTN_text_field.getText();
        BookType bookType = new BookType();
        bookType.setBookTypeName(s_book_type_name);
        this.fillTable(bookType);
    }
    /**
    * @Description: 表格行点击处理
     * @author      Dong
    * @param        MouseEvent
    * @return       null
    * @exception    null
    * @date         2018/11/28 16:26
    */
    private void book_type_name_mouse_press(MouseEvent e) {
        int row = book_data_table.getSelectedRow();                 //获取选中的行
        id_textField.setText(book_data_table.getValueAt(row, 0).toString());//显示行
        btn_textField.setText(book_data_table.getValueAt(row, 1).toString());
        desc_text_area.setText(book_data_table.getValueAt(row, 2).toString());
    }
    /**
    * @Description: 修改事件
     * @author      Dong
    * @param        ActionEvent
    * @return       null
    * @exception    null
    * @date         2018/11/28 16:26
    */
    private void book_type_update_action_event(ActionEvent e) {
        String id = id_textField.getText();
        String booktypename = btn_textField.getText();
        String booktypedesc = desc_text_area.getText();
        if (StringUtil.isEmpty(id)) {
            JOptionPane.showMessageDialog(null, "请选择要修改的记录!");
            return;
        }
        if (StringUtil.isEmpty(booktypename)) {
            JOptionPane.showMessageDialog(null, "图书类别名称不能为空!");
            return;
        }
        BookType book_type = new BookType(Integer.parseInt(id), booktypename, booktypedesc);
        Connection con = null;
        try {
            con = dbutil.getCon();
            int modify_num = booktypedao.update(con, book_type);
            if (modify_num == 1) {
                JOptionPane.showMessageDialog(null, "修改成功!");
                this.Reset_Value();
                this.fillTable(new BookType());
            } else {
                JOptionPane.showMessageDialog(null, "修改失败!");
            }
        } catch (Exception ee) {
            ee.printStackTrace();
            JOptionPane.showMessageDialog(null, "修改失败!");
        } finally {
            try {
                con.close();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }

    /**
    * @Description: 图书类别删除
     * @author      Dong
    * @param        ActionEvent
    * @return       null
    * @exception    null
    * @date         2018/11/28 16:27
    */
    private void book_type_delete_action_event(ActionEvent e) {
        String id = id_textField.getText();
        if (StringUtil.isEmpty(id)) {
            JOptionPane.showMessageDialog(null, "请选择要删除的记录!");
            return;
        }
        int n = JOptionPane.showConfirmDialog(null, "确定要删除的记录吗?");
        if (n == 0) {
            Connection con = null;
            try {
                con = dbutil.getCon();
                boolean flag = bookdao.existBook_By_BookTypeId(con, id);
                if (flag) {
                    JOptionPane.showMessageDialog(null, "当前图书类别下有图书，不能删除此类别！");
                    return;
                }
                int num = booktypedao.delete(con, id);
                if (num == 1) {
                    JOptionPane.showMessageDialog(null, "删除成功！");
                    this.Reset_Value();
                    this.fillTable(new BookType());
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
        BookTypeManager = new JPanel();
        BookTypeManager.setLayout(new FormLayout("fill:57px:grow,fill:max(d;4px):noGrow,left:4dlu:noGrow,left:83dlu:noGrow,fill:152px:grow,left:4dlu:noGrow,fill:d:grow", "center:52px:noGrow,top:4dlu:noGrow,center:136px:noGrow,top:5dlu:noGrow,center:155px:noGrow,top:4dlu:noGrow,center:66px:noGrow"));
        BookTypeManager.setAutoscrolls(false);
        BookTypeManager.setBackground(new Color(-6317151));
        BookTypeManager.setEnabled(true);
        BookTypeManager.setFocusCycleRoot(false);
        BookTypeManager.setVerifyInputWhenFocusTarget(true);
        BookTypeManager.setVisible(true);
        scrollpane = new JScrollPane();
        scrollpane.setBackground(new Color(-6317151));
        scrollpane.setEnabled(false);
        scrollpane.setForeground(new Color(-855310));
        scrollpane.setHorizontalScrollBarPolicy(30);
        CellConstraints cc = new CellConstraints();
        BookTypeManager.add(scrollpane, cc.xyw(1, 3, 7, CellConstraints.FILL, CellConstraints.FILL));
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
        table_option = new JPanel();
        table_option.setLayout(new FormLayout("fill:103px:noGrow,left:45dlu:noGrow,left:4dlu:noGrow,fill:112px:noGrow,left:91dlu:noGrow,fill:d:grow", "center:d:noGrow,top:4dlu:noGrow,center:168px:grow"));
        table_option.setBackground(new Color(-6317151));
        BookTypeManager.add(table_option, cc.xyw(1, 5, 7, CellConstraints.DEFAULT, CellConstraints.FILL));
        table_option.setBorder(BorderFactory.createTitledBorder(null, "表单操作", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, Font.BOLD, 12, table_option.getFont())));
        ID_label = new JLabel();
        Font ID_labelFont = this.$$$getFont$$$(null, Font.BOLD, 12, ID_label.getFont());
        if (ID_labelFont != null) ID_label.setFont(ID_labelFont);
        ID_label.setText("编号：");
        table_option.add(ID_label, cc.xy(1, 1, CellConstraints.CENTER, CellConstraints.DEFAULT));
        id_textField = new JTextField();
        id_textField.setEditable(false);
        id_textField.setEnabled(true);
        table_option.add(id_textField, cc.xy(2, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
        book_type_name_lable = new JLabel();
        Font book_type_name_lableFont = this.$$$getFont$$$(null, Font.BOLD, 12, book_type_name_lable.getFont());
        if (book_type_name_lableFont != null) book_type_name_lable.setFont(book_type_name_lableFont);
        book_type_name_lable.setText("图书类别名称：");
        table_option.add(book_type_name_lable, cc.xy(4, 1, CellConstraints.CENTER, CellConstraints.DEFAULT));
        btn_textField = new JTextField();
        btn_textField.setEditable(true);
        table_option.add(btn_textField, new CellConstraints(5, 1, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT, new Insets(0, 0, 0, 50)));
        desc_text_area = new JTextArea();
        table_option.add(desc_text_area, new CellConstraints(2, 3, 4, 1, CellConstraints.FILL, CellConstraints.FILL, new Insets(0, 0, 0, 50)));
        desc_label = new JLabel();
        Font desc_labelFont = this.$$$getFont$$$(null, Font.BOLD, 12, desc_label.getFont());
        if (desc_labelFont != null) desc_label.setFont(desc_labelFont);
        desc_label.setText("描述：");
        table_option.add(desc_label, cc.xy(1, 3, CellConstraints.CENTER, CellConstraints.DEFAULT));
        modify_butt = new JButton();
        Font modify_buttFont = this.$$$getFont$$$(null, Font.BOLD, 12, modify_butt.getFont());
        if (modify_buttFont != null) modify_butt.setFont(modify_buttFont);
        modify_butt.setIcon(new ImageIcon(getClass().getResource("/image/Modify_16px.png")));
        modify_butt.setText("修改");
        BookTypeManager.add(modify_butt, cc.xyw(2, 7, 3, CellConstraints.CENTER, CellConstraints.DEFAULT));
        delete_butt = new JButton();
        Font delete_buttFont = this.$$$getFont$$$(null, Font.BOLD, 12, delete_butt.getFont());
        if (delete_buttFont != null) delete_butt.setFont(delete_buttFont);
        delete_butt.setIcon(new ImageIcon(getClass().getResource("/image/delete_remove_16px.png")));
        delete_butt.setText("删除");
        BookTypeManager.add(delete_butt, new CellConstraints(5, 7, 1, 1, CellConstraints.CENTER, CellConstraints.DEFAULT, new Insets(0, 0, 0, 80)));
        BTN_text_field = new JTextField();
        BTN_text_field.setText("");
        BookTypeManager.add(BTN_text_field, new CellConstraints(4, 1, 1, 1, CellConstraints.FILL, CellConstraints.DEFAULT, new Insets(0, 10, 0, 100)));
        Query_butt = new JButton();
        Font Query_buttFont = this.$$$getFont$$$(null, Font.BOLD, 12, Query_butt.getFont());
        if (Query_buttFont != null) Query_butt.setFont(Query_buttFont);
        Query_butt.setText("查询");
        BookTypeManager.add(Query_butt, cc.xy(5, 1, CellConstraints.CENTER, CellConstraints.DEFAULT));
        BTN_lable = new JLabel();
        Font BTN_lableFont = this.$$$getFont$$$(null, Font.BOLD, 12, BTN_lable.getFont());
        if (BTN_lableFont != null) BTN_lable.setFont(BTN_lableFont);
        BTN_lable.setText("图书类别名称:");
        BookTypeManager.add(BTN_lable, cc.xy(1, 1, CellConstraints.CENTER, CellConstraints.DEFAULT));
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
        return BookTypeManager;
    }
}
