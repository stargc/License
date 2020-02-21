package com.ehl.license.check.controller;

import com.ehl.license.check.core.LicenseVerify;
import com.ehl.license.check.entity.LicenseCheckResp;
import com.ehl.license.check.tool.LicenseStore;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author created by guanchen on 2020/2/18 16:16
 */
@RestController
@Log4j2
@CrossOrigin
public class LicenseController {

    @Autowired
    private LicenseVerify licenseVerify;

    @GetMapping("checkLicense")
    public LicenseCheckResp checkLicense() {
        try {
            System.out.println(licenseVerify.getAfterDays());
        } catch (Exception e) {
            log.error(e);
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
