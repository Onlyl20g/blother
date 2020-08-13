package com.jinke.common.constant;

/**
 * 通用常量信息
 *
 * @author jinke
 */
public class Constants {
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 自动去除表前缀
     */
    public static final String AUTO_REOMVE_PRE = "true";

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    // 系统标识, 调用外部系统时使用
    public static final String PLATFORM = "IMAGE_SYS";
    public static final String FLAG_SUCCESS = "success";

    // SSO登录目标标识
    public static final String SSO_TARGET_APPLY_ATTACH = "applyAttach";

    // 创建单据id目标标识
    public static final String SSO_TARGET_CREATE_BILLID = "createBillId";

}
