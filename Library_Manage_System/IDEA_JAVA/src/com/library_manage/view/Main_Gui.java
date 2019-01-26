package com.library_manage.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Description: 主窗口
 * @Author: Dong
 * @CreateDate: 2018/11/22 11:35
 * @UpdateUser: Dong
 * @UpdateDate: 2018/11/22 11:35
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class Main_Gui {
    private JPanel Main_Windows;
    private JFrame main_frame;          //框架
    private JMenuBar menubar;           //菜单条
    private JMenu menu1;                //菜单栏
    private JMenu menu2;
    private JMenu m1_item1;
    private JMenu m1_item2;
    private JMenuItem m1_item3;         //子菜单
    private JMenuItem m2_item1;
    private JMenuItem m1_item11;
    private JMenuItem m1_item12;
    private JMenuItem m1_item21;
    private JMenuItem m1_item22;

    public Main_Gui() {
        main_frame = new JFrame("图书管理系统主界面");
        menubar = new JMenuBar();                       //创建菜单栏
        menu1 = new JMenu("基本数据维护");            //创建菜单
        menu2 = new JMenu("关于我们");
        //子菜单
        m1_item1 = new JMenu("图书类别管理");
        m1_item2 = new JMenu("图书管理");
        m1_item3 = new JMenuItem("安全退出");
        m2_item1 = new JMenuItem("关于Mr.Dong");
        //子子菜单
        m1_item11 = new JMenuItem("图书类别添加");
        m1_item12 = new JMenuItem("图书类别维护");
        m1_item21 = new JMenuItem("图书添加");
        m1_item22 = new JMenuItem("图书维护");

        main_frame.setContentPane(Main_Windows);
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_frame.pack();
        main_frame.setVisible(true);
        main_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);     //最大化
        main_frame.setJMenuBar(menubar);
        //添加菜单
        menubar.add(menu1);
        menubar.add(menu2);
        //添加子菜单
        menu1.add(m1_item1);
        menu1.add(m1_item2);
        menu1.add(m1_item3);
        menu2.add(m2_item1);
        //添加子子菜单
        m1_item1.add(m1_item11);
        m1_item1.add(m1_item12);
        m1_item2.add(m1_item21);
        m1_item2.add(m1_item22);
        //关于 监听器
        m2_item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                About_us_actionPerformed(e);
            }
        });
        //安全退出 监听器
        m1_item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Exit_Safely_actionPerformed(e);
            }
        });
        //图书类别添加 监听器
        m1_item11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book_Type_Add(e);
            }
        });
        //图书类别维护 监听器
        m1_item12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book_Type_Manage(e);
            }
        });
        //图书添加  监听器
        m1_item21.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book_Add(e);
            }
        });
        //图书维护 监听器
        m1_item22.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book_Maintain(e);
            }
        });

    }

    /**
    * @Description: 显示'关于我们'窗口
     * @author      Dong
    * @param        ActionEvent
    * @return       null
    * @exception    null
    * @date         2018/11/28 16:11
    */
    private void About_us_actionPerformed(ActionEvent e) {
        new About_Us();
    }
    /**
    * @Description: 安全退出
     * @author      Dong
    * @param        ActionEvent
    * @return       null
    * @exception    null
    * @date         2018/11/28 16:12
    */
    private void Exit_Safely_actionPerformed(ActionEvent e) {
        int result = JOptionPane.showConfirmDialog(null, "是否安全退出系统");
        if (result == 0) {              //是
            main_frame.dispose();       //销毁
        }
    }
    /**
    * @Description: 图书类别添加
     * @author      Dong
    * @param        ActionEvent
    * @return       null
    * @exception    null
    * @date         2018/11/28 16:14
    */
    private void Book_Type_Add(ActionEvent e) {
        new BookTypeAdd();
    }

    /**
    * @Description: 图书类别维护
     * @author      Dong
    * @param        ActionEvent
    * @return       null
    * @exception    null
    * @date         2018/11/28 16:15
    */
    private void Book_Type_Manage(ActionEvent e) {
        new Book_Type_Manager();
    }

    /**
    * @Description: 图书添加
     * @author      Dong
    * @param        ActionEvent
    * @return       null
    * @exception    null
    * @date         2018/11/28 16:15
    */
    private void Book_Add(ActionEvent e) {
        new BookAdd();
    }

    /**
    * @Description: 图书维护
     * @author      Dong
    * @param        ActionEvent
    * @return       null
    * @exception    null
    * @date         2018/11/28 16:15
    */
    private void Book_Maintain(ActionEvent e) {
        new Book_Manager();
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
        Main_Windows = new JPanel();
        Main_Windows.setLayout(new BorderLayout(0, 0));
        Main_Windows.setBackground(new Color(-6317151));
        Main_Windows.setEnabled(false);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return Main_Windows;
    }
}
