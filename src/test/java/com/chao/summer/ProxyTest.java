package com.chao.summer;

import com.chao.summer.config.ProxyInvocationHandler;
import com.chao.summer.service.ProxyService;
import com.chao.summer.service.impl.ProxyServiceImpl;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;

public class ProxyTest {
    public static void main(String[] args) {
        ProxyService orgService = new ProxyServiceImpl();
        ProxyInvocationHandler proxyInvocationHandler = new ProxyInvocationHandler(orgService);
        ProxyService proxyService1 = (ProxyService) proxyInvocationHandler.getProxy();

        ProxyService proxyService2 = (ProxyService) Proxy.newProxyInstance(
                orgService.getClass().getClassLoader(), orgService.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                        System.out.println("Haha");
                        return method.invoke(orgService, objects);
                    }
                });
        orgService.create();
        proxyService1.create();
        proxyService2.delete();
    }
}
