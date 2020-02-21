package com.ehl.license.check.tool;

import java.lang.management.ManagementFactory;

public class PidStop {
    public static void stopJvm() throws Exception{
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName.contains("windows")){
            Runtime.getRuntime().exec("taskkill /PID "+pid+" /F");
        }else {
            Runtime.getRuntime().exec("kill -9 "+pid);
        }
    }
}
