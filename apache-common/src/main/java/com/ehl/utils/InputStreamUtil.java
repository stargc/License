package com.ehl.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class InputStreamUtil {
    public static byte[] streamToByte(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[100];
            int rc = 0;
            while ((rc = inputStream.read(buffer,0,100)) > 0){
                outputStream.write(buffer,0,rc);
            }
        }catch (Exception e){ }
        return outputStream.toByteArray();
    }
}
