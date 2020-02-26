package com.ehl.heartbeat.support;

import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

/**
 * de.schlichtherle.license.LicenseManager的单例
 */
public class HeartBeatManagerHolder {

    private static volatile LicenseManager LICENSE_MANAGER;

    public static LicenseManager getInstance(LicenseParam param){
        if(LICENSE_MANAGER == null){
            synchronized (CustomHeartBeatManager.class){
                if(LICENSE_MANAGER == null){
                    LICENSE_MANAGER = new CustomHeartBeatManager(param);
                }
            }
        }

        return LICENSE_MANAGER;
    }

}
