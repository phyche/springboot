package com.example.springboot.designMode.flyweight;

/**
 * 享元模式
 * 运用共享技术有效的支持大量细粒度的对象
 *
 * 使用场合：
 * 1.如果一个应用程序使用了大量对象，而大量的这些对象造成了很大的存储开销时就应该使用
 * 2.对象的大多数状态可以外部状态，如果删除对象的外部状态，那么可以用相对较少的共享对象取代很多组合对象
  */
public class FlyWeightMain {

    public static void main(String[] strg) {

        WebsiteFactory f = new WebsiteFactory();

        Website fx = f.getWebsiteCategory("产品展示");
        fx.use(new User("小菜"));

        Website fy = f.getWebsiteCategory("产品展示");
        fy.use(new User("大鸟"));

        Website fz = f.getWebsiteCategory("产品展示");
        fz.use(new User("娇娇"));

        Website fl = f.getWebsiteCategory("博客");
        fl.use(new User("老顽童"));

        Website fm = f.getWebsiteCategory("博客");
        fm.use(new User("桃谷六仙"));

        Website fn = f.getWebsiteCategory("博客");
        fn.use(new User("南海鄂神"));

        System.out.println("得到网站分类总数是：" + f.getWebsiteCount());
    }
}
