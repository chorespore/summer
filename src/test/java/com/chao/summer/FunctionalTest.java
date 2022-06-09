package com.chao.summer;

import com.chao.summer.domain.entity.User;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

    @Test
    public void streamTest() {
        User user1 = new User(1L, "Alice", 12, "alice@chao.com");
        User user2 = new User(2L, "Bob", 15, "alice@chao.com");
        User user3 = new User(3L, "Candy", 17, "alice@chao.com");
        User user4 = new User(4L, "Dad", 18, "alice@chao.com");
        User user5 = new User(5L, "Elle", 19, "alice@chao.com");
        User user6 = new User(6L, "Fox", 20, "alice@chao.com");

        List<User> list = Arrays.asList(user1, user2, user3, user4, user5, user6);

        List<String> res = list.stream().filter((u) -> u.getId() % 2 == 0)
                .filter((u) -> u.getAge() > 10)
                .map(u -> u.getName().toUpperCase())
                .sorted(Comparator.reverseOrder())
                .limit(2).collect(Collectors.toList());

        System.out.println(res);
    }


}
