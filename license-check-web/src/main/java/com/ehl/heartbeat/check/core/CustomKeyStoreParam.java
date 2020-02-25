package com.ehl.heartbeat.check.core;

import de.schlichtherle.license.AbstractKeyStoreParam;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 自定义KeyStoreParam，用于将公私钥存储文件存放到其他磁盘位置而不是项目中
 */
public class CustomKeyStoreParam extends AbstractKeyStoreParam {

    /**
     * 公钥/私钥在磁盘上的存储路径
     */
    private byte[] storeFile;
    private String alias;
    private String storePwd;
    private String keyPwd;

    public CustomKeyStoreParam(Class clazz, byte[] storeFile,String alias,String storePwd,String keyPwd) {
        super(clazz, "");
        this.storeFile = storeFile;
        this.alias = alias;
        this.storePwd = storePwd;
        this.keyPwd = keyPwd;
    }


    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getStorePwd() {
        return storePwd;
    }

    @Override
    public String getKeyPwd() {
        return keyPwd;
    }

    /**
     * 复写de.schlichtherle.license.AbstractKeyStoreParam的getStream()方法<br/>
     * 用于将公私钥存储文件存放到其他磁盘位置而不是项目中
     * @return java.io.InputStream
     */
    @Override
    public InputStream getStream() {
        return new ByteArrayInputStream(storeFile);
    }
}
