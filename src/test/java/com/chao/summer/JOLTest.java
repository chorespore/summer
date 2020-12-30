package com.chao.summer;

import org.openjdk.jol.info.ClassLayout;

public class JOLTest {
    public static void main(String[] args) {
        Object obj = new Object();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());

        synchronized (obj) {
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }

        String s = "ABCDE12345678";
        System.out.println(ClassLayout.parseInstance(s).toPrintable());

        int i = 17;
        System.out.println(ClassLayout.parseInstance(i).toPrintable());

        int[] arr = {2,4};
        System.out.println(ClassLayout.parseInstance(arr).toPrintable());

        class User {
            boolean name;
            int age;
            boolean vip;
        }

        User user = new User();
        System.out.println(ClassLayout.parseInstance(user).toPrintable());

    }


}
