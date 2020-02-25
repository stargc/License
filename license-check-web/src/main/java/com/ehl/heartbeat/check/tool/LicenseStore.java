package com.ehl.heartbeat.check.tool;

import com.ehl.heartbeat.check.entity.LicenseCheckResp;

/**
 * @author created by guanchen on 2020/2/20 14:54
 */
public class LicenseStore {
    private static LicenseStore mInstance;

    private LicenseCheckResp resp;

    private LicenseStore(){}

    public static LicenseStore getInstance(){
        if (mInstance == null){
            mInstance = new LicenseStore();
        }
        return mInstance;
    }

    public LicenseCheckResp getCheckResp(){
        if (resp == null){
            resp = new LicenseCheckResp();
            resp.setCode(0);
            resp.setMsg("SUCCESS");
            resp.setExpireFlag(false);
            resp.setExpireDays(27);
        }
        resp.setServerInfo(ServerInfoUtil.getServerInfo());
        return resp;
    }

    public void setCheckResp(boolean expireFlag, int expireDays){
        resp = new LicenseCheckResp();
        resp.setCode(0);
        resp.setMsg("SUCCESS");
        resp.setExpireFlag(expireFlag);
        resp.setExpireDays(expireDays);
    }

}
