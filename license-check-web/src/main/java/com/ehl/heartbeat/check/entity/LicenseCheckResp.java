package com.ehl.heartbeat.check.entity;

import lombok.Data;

/**
 * @author created by guanchen on 2020/2/19 10:14
 */
@Data
public class LicenseCheckResp {
    private int code;
    private String msg;
    //距离不许使用还有多久期满
    private int expireDays;
    //授权是否过期 true：授权失效
    private boolean expireFlag;
    private String serverInfo;
}
