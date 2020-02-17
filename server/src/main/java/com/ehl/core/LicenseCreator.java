package com.ehl.core;

import com.ehl.utils.InputStreamUtil;
import de.schlichtherle.license.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.text.MessageFormat;
import java.util.prefs.Preferences;

@Component
public class LicenseCreator {
    @Value("${license.subject}")
    private String subject;
    @Value("${license.publicAlias}")
    private String publicAlias;
    @Value("${license.storePass}")
    private String storePass;
    @Value("${license.privateAlias}")
    private String privateAlias;
    @Value("${license.keyPass}")
    private String keyPass;
    @Value("${license.path}")
    private String path;

    private static Logger logger = LogManager.getLogger(LicenseCreator.class);
    private final static X500Principal DEFAULT_HOLDER_AND_ISSUER = new X500Principal("CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN");

    /**
     * 生成License证书
     */
    public boolean generateLicense(LicenseCreatorParam param){
        try {
            LicenseManager licenseManager = new CustomLicenseManager(initLicenseParam());
            LicenseContent licenseContent = initLicenseContent(param);

            licenseManager.store(licenseContent,new File(path));

            return true;
        }catch (Exception e){
            logger.error(MessageFormat.format("证书生成失败：{0}",param),e);
            return false;
        }
    }
    public String generateLicenseForPath(LicenseCreatorParam param){
        if(generateLicense(param)){
            return path;
        }
        return "";
    }

    /**
     * 初始化证书生成参数
     */
    private LicenseParam initLicenseParam(){
        ClassLoader classLoader = this.getClass().getClassLoader();
        Preferences preferences = Preferences.userNodeForPackage(LicenseCreator.class);

        //设置对证书内容加密的秘钥
        CipherParam cipherParam = new DefaultCipherParam(storePass);

        KeyStoreParam privateStoreParam = new CustomKeyStoreParam(LicenseCreator.class
                ,InputStreamUtil.streamToByte(classLoader.getResourceAsStream("privateKeys.keystore"))
                ,privateAlias,storePass,keyPass);

        return new DefaultLicenseParam(subject
                ,preferences
                ,privateStoreParam
                ,cipherParam);
    }

    /**
     * 设置证书生成正文信息
     */
    private LicenseContent initLicenseContent(LicenseCreatorParam param){
        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setHolder(DEFAULT_HOLDER_AND_ISSUER);
        licenseContent.setIssuer(DEFAULT_HOLDER_AND_ISSUER);

        licenseContent.setSubject(subject);
        licenseContent.setIssued(param.getIssuedTime());
        licenseContent.setNotBefore(param.getIssuedTime());
        licenseContent.setNotAfter(param.getExpiryTime());
        licenseContent.setConsumerType(param.getConsumerType());
        licenseContent.setConsumerAmount(param.getConsumerAmount());
        licenseContent.setInfo(param.getDescription());

        //扩展校验服务器硬件信息
        licenseContent.setExtra(param.getCode());

        return licenseContent;
    }

}
