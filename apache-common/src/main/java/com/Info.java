package com;

import java.text.MessageFormat;

import static com.ehl.utils.ServerInfoUtil.getServerInfo;

public class Info {
    public static void main(String[] args) {
        System.out.println(MessageFormat.format("证书码:{0}",getServerInfo()));
    }
}
