package com.chao.summer;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class SummerTest {

    @Test
    void timeTest() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp);
    }

    @Test
    void streamTest() {
        List<Pair<String, Double>> pairArrayList = new ArrayList<>(3);
        pairArrayList.add(new Pair<>("version", 6.19));
        pairArrayList.add(new Pair<>("version", 10.24));
        pairArrayList.add(new Pair<>("version", 13.14));
        Map<String, Double> map = pairArrayList.stream().collect(
                // 生成的 map 集合中只有一个键值对：{version=13.14}
                Collectors.toMap(Pair::getKey, Pair::getValue, (v1, v2) -> v1));
        System.out.println(map);

        String[] departments = new String[]{"iERP", "iERP", "EIBU"};
        // 抛出 IllegalStateException 异常
        Map<Integer, String> map2 = Arrays.stream(departments)
                .collect(Collectors.toMap(String::hashCode, str -> str, (v1, v2) -> v1));
        System.out.println(map2);
    }


    @Test
    void asListTest() {
        List<String> list = new ArrayList<>(2);
        list.add("guan");
        list.add("bao");
        String[] array = list.toArray(new String[0]);
        for (String s : array) {
            System.out.println(s);
        }

        String[] str = {"yang", "hao"};
        List list1 = Arrays.asList(str);

        // list1.add("yangguanbao");
        str[0] = "changed";

        System.out.println(list1);
        System.out.println(Arrays.toString(str));
    }

    @Test
    void genericsTest() {
        List<String> generics = null;
        List notGenerics = new ArrayList(10);
        notGenerics.add(new Object());
        notGenerics.add(true);
        notGenerics.add(new Integer(1));
        notGenerics.add(new Double(2.3));
        generics = notGenerics;
        // 此处抛出 ClassCastException 异常
        // String string = generics.get(0);
        notGenerics.forEach(i -> System.out.println(i));


    }

    @Test
    void iteratorTest() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if ("3".equals(item)) {
                iterator.remove();
            }
        }
        System.out.println(list);

        //java.util.ConcurrentModificationException
        for (String item : list) {
            if ("2".equals(item)) {
                list.remove(item);
            }
        }

        System.out.println(list);
    }

    @Test
    void diamondTest() {
        HashMap<String, String> userCache = new HashMap(16);
        HashMap<String, String> userCache1 = new HashMap<>(16);
        ArrayList<String> users = new ArrayList(10);
    }
}
