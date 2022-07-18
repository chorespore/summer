package com.chao.summer;

import com.chao.summer.enums.TopicType;
import org.junit.jupiter.api.Test;


public class EnumTest {
    @Test
    void nameTest() {
        System.out.println(TopicType.ALICE.ordinal());
        System.out.println(TopicType.DAVID.name());
    }
}
