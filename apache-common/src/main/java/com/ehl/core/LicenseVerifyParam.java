package com.ehl.core;

import java.io.InputStream;

import static com.ehl.utils.InputStreamUtil.streamToByte;

/**
 * License校验类需要的参数
 *
 */
public class LicenseVerifyParam {

    /**
     * 证书subject
     */
    private String subject;

    /**
     * 公钥别称
     */
    private String publicAlias;

    /**
     * 访问公钥库的密码
     */
    private String storePass;

    /**
     * 证书生成路径
     */
    private byte[] licenseFile;

    /**
     * 密钥库存储路径
     */
    private byte[] publicKeysStoreFile;

    public LicenseVerifyParam() {

    }

    public LicenseVerifyParam(String subject, String publicAlias, String storePass, InputStream licenseFile, InputStream publicKeysStoreFile) {
        this.subject = subject;
        this.publicAlias = publicAlias;
        this.storePass = storePass;
        this.licenseFile = streamToByte(licenseFile);
        this.publicKeysStoreFile = streamToByte(publicKeysStoreFile);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPublicAlias() {
        return publicAlias;
    }

    public void setPublicAlias(String publicAlias) {
        this.publicAlias = publicAlias;
    }

    public String getStorePass() {
        return storePass;
    }

    public void setStorePass(String storePass) {
        this.storePass = storePass;
    }

    public byte[] getLicenseFile() {
        return licenseFile;
    }

    public void setLicenseFile(byte[] licenseFile) {
        this.licenseFile = licenseFile;
    }

    public byte[] getPublicKeysStoreFile() {
        return publicKeysStoreFile;
    }

    public void setPublicKeysStoreFile(byte[] publicKeysStoreFile) {
        this.publicKeysStoreFile = publicKeysStoreFile;
    }

}
