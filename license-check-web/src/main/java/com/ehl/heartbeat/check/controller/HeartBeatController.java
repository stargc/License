package com.ehl.heartbeat.check.controller;

import com.ehl.heartbeat.check.core.LicenseVerify;
import com.ehl.heartbeat.check.entity.LicenseCheckResp;
import com.ehl.heartbeat.check.tool.LicenseStore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author created by guanchen on 2020/2/18 16:16
 */
@RestController
@Slf4j
@CrossOrigin
public class HeartBeatController {

    @Autowired
    private LicenseVerify licenseVerify;

    @GetMapping("checkLicense")
    public LicenseCheckResp checkLicense() {
        try {
            System.out.println(licenseVerify.getAfterDays());
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
        }
        return LicenseStore.getInstance().getCheckResp();
    }

    @GetMapping("heartbeat")
    public LicenseCheckResp heartbeat() {
        //TODO: 添加缓存
        LicenseCheckResp resp = new LicenseCheckResp();
        resp.setCode(0);
        try {
            licenseVerify.doInstall();
            resp.setExpireFlag(false);
            resp.setExpireDays(licenseVerify.getAfterDays());
        } catch (Exception e) {
            log.error("证书认证失败：" + e.getMessage());
            resp.setExpireFlag(true);
            return resp;
        }
        return resp;
    }

    @GetMapping("setLicense")
    public String setLicense(@RequestParam boolean expireFlag, @RequestParam int expireDays) {
        LicenseStore.getInstance().setCheckResp(expireFlag, expireDays);
        return "SUCCESS";
    }
}
