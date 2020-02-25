package com.ehl.heartbeat.check.core;

import com.ehl.heartbeat.check.tool.InputStreamUtil;
import de.schlichtherle.license.*;
import lombok.extern.log4j.Log4j2;
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
public class LicenseVerify {

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
        ClassLoader classLoader = LicenseVerify.class.getClassLoader();
        LicenseVerifyParam param = new LicenseVerifyParam();
        param.setSubject(licenseSubject);
        param.setPublicAlias(licensePublicAlias);
        param.setStorePass(licenseStorePass);

        param.setPublicKeysStoreFile(InputStreamUtil.streamToByte(classLoader.getResourceAsStream(publicKeystoreFileName)));
        InputStream inputStream = new FileInputStream(licenseFileName);
        if (inputStream == null) {
            throw new LicenseContentException("请先获取证书");
        }
        param.setLicenseFile(InputStreamUtil.streamToByte(inputStream));
        CustomLicenseManager licenseManager = (CustomLicenseManager) LicenseManagerHolder.getInstance(initLicenseParam(param));
        licenseManager.uninstall();
        licenseManager.install(param.getLicenseFile());
    }

    /**
     * 校验License证书
     */
    public boolean verify() {
        LicenseManager licenseManager = LicenseManagerHolder.getInstance(null);
        try {
            licenseManager.verify();
            return true;
        } catch (Exception e) {
            log.error(ExceptionUtils.getStackTrace(e));
            return false;
        }
    }

    public int getAfterDays() throws Exception{
        CustomLicenseManager licenseManager = (CustomLicenseManager) LicenseManagerHolder.getInstance(null);
        int days = licenseManager.getAfterDays();
        Assert.isTrue(days > 0,"证书已过期认证失败");
        return days;
    }

    /**
     * 初始化证书生成参数
     */
    private LicenseParam initLicenseParam(LicenseVerifyParam param) {
        Preferences preferences = Preferences.userNodeForPackage(LicenseVerify.class);

        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam publicStoreParam = new CustomKeyStoreParam(LicenseVerify.class
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
