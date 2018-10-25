package com.example.springboot.designMode.flyweight;

import java.util.Hashtable;

public class WebsiteFactory {

    private Hashtable flyweights = new Hashtable();

    //获得网站分类
    public Website getWebsiteCategory(String key) {
        if (!flyweights.contains(key)) {
            flyweights.put(key,new ConcreteWebsite(key));
        }
        return (Website) flyweights.get(key);
    }

    //获得网站分类总数
    public int getWebsiteCount() {
        return flyweights.size();
    }
}
