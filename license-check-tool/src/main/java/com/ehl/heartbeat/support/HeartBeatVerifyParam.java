package com.ehl.heartbeat.support;

import com.ehl.heartbeat.tool.InputStreamUtil;

import java.io.InputStream;

/**
 * License校验类需要的参数
 *
 */
public class HeartBeatVerifyParam {

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

    public HeartBeatVerifyParam() {

    }

    public HeartBeatVerifyParam(String subject, String publicAlias, String storePass, InputStream licenseFile, InputStream publicKeysStoreFile) {
        this.subject = subject;
        this.publicAlias = publicAlias;
        this.storePass = storePass;
        this.licenseFile = InputStreamUtil.streamToByte(licenseFile);
        this.publicKeysStoreFile = InputStreamUtil.streamToByte(publicKeysStoreFile);
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
