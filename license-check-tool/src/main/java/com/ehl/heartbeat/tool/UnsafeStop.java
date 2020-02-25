package com.ehl.heartbeat.tool;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeStop {
    public static void stopJvm() throws Exception{
        Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        unsafe.putAddress(0,0);
    }
}
