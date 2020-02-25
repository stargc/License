package com.ehl.heartbeat.support;

import com.ehl.heartbeat.core.CustomLicenseManager;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

/**
 * de.schlichtherle.license.LicenseManager的单例
 */
public class HeartBeatManagerHolder {

    private static volatile LicenseManager LICENSE_MANAGER;

    public static LicenseManager getInstance(LicenseParam param){
        if(LICENSE_MANAGER == null){
            synchronized (HeartBeatManagerHolder.class){
                if(LICENSE_MANAGER == null){
                    LICENSE_MANAGER = new CustomLicenseManager(param);
                }
            }
        }

        return LICENSE_MANAGER;
    }

}
