package com.ehl.core;

import com.ehl.utils.PidStop;
import de.schlichtherle.license.*;

import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

import static com.ehl.utils.InputStreamUtil.streamToByte;

public class LicenseVerify {
    private static final String configFileName = "license-config.properties";
    private static final String publicKeystoreFileName = "publicCerts.keystore";
    private static final String licenseFileName = "license.lic";
    private static Random random = new Random();
    private static long aLong = 2*24*60*60*1000L+random.nextInt(2*24*60*60*1000);
    public static void init(){
        try {
            ClassLoader classLoader = LicenseVerify.class.getClassLoader();
            Properties properties = new Properties();
            properties.load(classLoader.getResourceAsStream(configFileName));
            LicenseVerifyParam param = new LicenseVerifyParam();
            param.setSubject(properties.getProperty("license.subject"));
            param.setPublicAlias(properties.getProperty("license.publicAlias"));
            param.setStorePass(properties.getProperty("license.storePass"));
            param.setPublicKeysStoreFile(streamToByte(classLoader.getResourceAsStream(publicKeystoreFileName)));
            InputStream inputStream = classLoader.getResourceAsStream(licenseFileName);
            if(inputStream == null){
                throw new LicenseContentException("请先获取证书");
            }
            param.setLicenseFile(streamToByte(inputStream));
            install(param);
        }catch (Throwable e){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        PidStop.stopJvm();
                    } catch (Exception e1) { }
                }
            },aLong);
        }
    }
    /**
     * 安装License证书
     */
    private static synchronized LicenseContent install(LicenseVerifyParam param) throws Exception{
        //1. 安装证书
//        System.out.println("开始安装证书");
        CustomLicenseManager licenseManager = (CustomLicenseManager) LicenseManagerHolder.getInstance(initLicenseParam(param));
        licenseManager.uninstall();
        LicenseContent result = licenseManager.install(param.getLicenseFile());
//        System.out.println("证书安装成功");
        return result;
    }

    /**
     * 校验License证书
     */
    public static boolean verify(){
        LicenseManager licenseManager = LicenseManagerHolder.getInstance(null);
        try {
            licenseManager.verify();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 初始化证书生成参数
     */
    private static LicenseParam initLicenseParam(LicenseVerifyParam param){
        Preferences preferences = Preferences.userNodeForPackage(LicenseVerify.class);

        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam publicStoreParam = new CustomKeyStoreParam(LicenseVerify.class
                ,param.getPublicKeysStoreFile()
                ,param.getPublicAlias()
                ,param.getStorePass()
                ,null);

        return new DefaultLicenseParam(param.getSubject()
                ,preferences
                ,publicStoreParam
                ,cipherParam);
    }
}
