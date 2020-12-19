package com.chao.summer;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalTest {

    @Test
    public void functionTest() {
        Function<Integer, String> numToStr = (num) -> "No: " + num;

        System.out.println(numToStr.apply(12));
    }

    @Test
    public void predicateTest() {
        Predicate<String> isEmpty = (s) -> s == null || s.length() == 0;

        System.out.println(isEmpty.test("e"));
    }

    @Test
    public void consumerTest() {
        Consumer<String> printTrim = (s) -> System.out.println(s.replace("-", ""));

        printTrim.accept(UUID.randomUUID().toString());
    }

    @Test
    public void supplierTest() {
        Supplier<String> uuid = () -> UUID.randomUUID().toString().replace("-", "");

        System.out.println(uuid.get());
    }


}
