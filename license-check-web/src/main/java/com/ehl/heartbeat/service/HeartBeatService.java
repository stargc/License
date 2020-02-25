package com.ehl.heartbeat.service;

import com.ehl.heartbeat.support.CustomKeyStoreParam;
import com.ehl.heartbeat.support.CustomHeartBeatManager;
import com.ehl.heartbeat.support.HeartBeatManagerHolder;
import com.ehl.heartbeat.support.HeartBeatVerifyParam;
import com.ehl.heartbeat.tool.InputStreamUtil;
import de.schlichtherle.license.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.prefs.Preferences;


@Component
@Slf4j
public class HeartBeatService {

    @Value("${license.subject}")
    private String licenseSubject;
    @Value("${license.publicAlias}")
    private String licensePublicAlias;
    @Value("${license.storePass}")
    private String licenseStorePass;

    private final String publicKeystoreFileName = "publicCerts.keystore";

    @Value("${license.path}")
    private String licenseFileName;

    public void doInstall() throws Exception {
        ClassLoader classLoader = HeartBeatService.class.getClassLoader();
        HeartBeatVerifyParam param = new HeartBeatVerifyParam();
        param.setSubject(licenseSubject);
        param.setPublicAlias(licensePublicAlias);
        param.setStorePass(licenseStorePass);

        param.setPublicKeysStoreFile(InputStreamUtil.streamToByte(classLoader.getResourceAsStream(publicKeystoreFileName)));
        InputStream inputStream = new FileInputStream(licenseFileName);
        if (inputStream == null) {
            throw new LicenseContentException("请先获取证书");
        }
        param.setLicenseFile(InputStreamUtil.streamToByte(inputStream));
        CustomHeartBeatManager licenseManager = (CustomHeartBeatManager) HeartBeatManagerHolder.getInstance(initLicenseParam(param));
        licenseManager.uninstall();
        licenseManager.install(param.getLicenseFile());
    }

    public boolean verify() {
        LicenseManager licenseManager = HeartBeatManagerHolder.getInstance(null);
        try {
            licenseManager.verify();
            return true;
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return false;
        }
    }

    public int getAfterDays() throws Exception{
        CustomHeartBeatManager licenseManager = (CustomHeartBeatManager) HeartBeatManagerHolder.getInstance(null);
        int days = licenseManager.getAfterDays();
        Assert.isTrue(days > 0,"证书已过期认证失败");
        return days;
    }

    /**
     * 初始化证书生成参数
     */
    private LicenseParam initLicenseParam(HeartBeatVerifyParam param) {
        Preferences preferences = Preferences.userNodeForPackage(HeartBeatService.class);

        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam publicStoreParam = new CustomKeyStoreParam(HeartBeatService.class
                , param.getPublicKeysStoreFile()
                , param.getPublicAlias()
                , param.getStorePass()
                , null);

        return new DefaultLicenseParam(param.getSubject()
                , preferences
                , publicStoreParam
                , cipherParam);
    }
}
