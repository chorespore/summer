package com.chao.summer;

import org.junit.jupiter.api.Test;

public class NumberTest {

    @Test
    public void integerTest() {
        int i1 = 3124;
        Integer i2 = 3124;
        Integer i3 = 3124;
        Integer i4 = new Integer(3124);
        Integer i5 = 6;
        Integer i6 = 6;

        System.out.println(i1 == i2);
        System.out.println(i1 == i4);
        System.out.println(i2 == i3);
        System.out.println(i3 == i4);
        System.out.println(i5 == i6);
        System.out.println("--------------------------");
    }

}
