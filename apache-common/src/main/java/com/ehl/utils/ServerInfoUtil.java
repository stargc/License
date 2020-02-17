package com.ehl.utils;

import com.ehl.core.AbstractServerInfos;
import com.ehl.core.LinuxServerInfos;
import com.ehl.core.WindowsServerInfos;

import java.security.MessageDigest;

public class ServerInfoUtil {
    public static String MD5(String sourceStr) {
        try {
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(sourceStr.getBytes());
            byte[] md = mdInst.digest();
            StringBuilder buf = new StringBuilder();
            for (byte t : md) {
                int tmp = t;
                if (tmp < 0)
                    tmp += 256;
                if (tmp < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(tmp));
            }
            return buf.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getServerInfo(){
        String osName = System.getProperty("os.name").toLowerCase();
        AbstractServerInfos abstractServerInfos = null;
        //根据不同操作系统类型选择不同的数据获取方法
        if (osName.startsWith("windows")) {
            abstractServerInfos = new WindowsServerInfos();
        } else if (osName.startsWith("linux")) {
            abstractServerInfos = new LinuxServerInfos();
        }else{//其他服务器类型
            abstractServerInfos = new LinuxServerInfos();
        }
        return MD5(abstractServerInfos.getServerInfos());
    }
}
