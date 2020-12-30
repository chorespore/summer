package com.chao.summer;

import com.chao.summer.entity.User;
import org.junit.jupiter.api.Test;

public class JVMTest {

    @Test
    public void classTest() {
        User user = new User();
        Class<? extends User> userClass = user.getClass();
        System.out.println(userClass);
        ClassLoader classLoader = userClass.getClassLoader();
        System.out.println(classLoader);
        ClassLoader classLoaderParent = classLoader.getParent();
        System.out.println(classLoaderParent);
        ClassLoader classLoaderGrandParent = classLoaderParent.getParent();
        System.out.println(classLoaderGrandParent);
    }

    private native void start0();

    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
        long max = runtime.maxMemory();
        long free = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();

        System.out.println("Cores: " + runtime.availableProcessors());

        System.out.println(max / 1024.0 / 1024.0);
        System.out.println(free / 1024.0 / 1024.0);
        System.out.println(totalMemory / 1024.0 / 1024.0);

    }

}
