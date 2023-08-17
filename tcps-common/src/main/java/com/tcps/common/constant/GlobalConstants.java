package com.tcps.common.constant;

/**
 * 全局常量
 */
public interface GlobalConstants {


    String GLOBAL_REDIS_KEY = "tcps:";

    /**
     * 登录账户密码错误次数 redis key
     */
    String PWD_ERR_CNT_KEY = GLOBAL_REDIS_KEY + "pwd_err_cnt:";
}
