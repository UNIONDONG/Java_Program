package com.library_manage.util;
/**
* @Description:    字符串操作类
* @Author:         Dong
* @CreateDate:     2018/11/20 11:06
* @UpdateUser:     Dong
* @UpdateDate:     2018/11/20 11:06
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class StringUtil {
    /**
    * @Description: 判断字符串是否为空
     * @author      Dong
    * @param        null
    * @return       boolean
    * @exception    
    * @date         2018/11/20 11:07
    */
    public static boolean isEmpty(String str){
        if(str==null || "".equals(str.trim()))  //去掉前后空格
            return true;
        else
            return false;
    }
}
