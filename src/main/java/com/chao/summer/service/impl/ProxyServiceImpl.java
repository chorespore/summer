package com.chao.summer.service.impl;

import com.chao.summer.service.ProxyService;

public class ProxyServiceImpl implements ProxyService {
    @Override
    public void create() {
        System.out.println("Item created!");
    }

    @Override
    public void delete() {
        System.out.println("Item deleted!");

    }

    @Override
    public void update() {
        System.out.println("Item updated!");

    }

    @Override
    public void find() {
        System.out.println("Item find!");

    }
}
