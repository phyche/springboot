package com.example.springboot.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author : cylion
 * @Date : 18-5-10 18:08
 * @Description :
 */
public class BasicDataUtils {
    public static Long nullToZero(Long i) {
        return i == null ? 0 : i;
    }

    /***
     * i必须是数字或者空字符串，如果是空字符串，则返回0
     * @param i
     * @return
     */
    public static Integer nullToZero(String i) {
        if (StringUtils.isBlank(i)) {
            return 0;
        }
        return Integer.valueOf(i);
    }

    /***
     * 如果obj是非空并且是字符串true,返回true,否则返回false
     * @param obj
     * @return
     */
    public static boolean objToTrue(Object obj) {
        if (obj != null && obj.toString().equals("true")) {
            return true;
        }
        return false;
    }
}
