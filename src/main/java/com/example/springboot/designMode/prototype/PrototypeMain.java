package com.example.springboot.designMode.prototype;

/**
 * 原型模式
 * 用原型实例指定创建对象种类，并通过拷贝这些原型创建新的对象
 *
 * 一般在初始化的信息不发生变化的情况下，克隆是最好的办法，既隐藏了对象创建的细节，又对性能的大大的提高
 * 等于不用重新初始化对象，而是动态的获得对象的运行时的状态
 *
 * 浅复制：
 * 被复制对象的所有变量都含有与原来的对象相同的值，而所有的对其他对象的引用都仍然指向原有对象
 * 深复制：
 * 把引用对象的变量指向复制过的新对象，而不是原有的被引用对象
 */
public class PrototypeMain {

    public static void main(String[] strg) throws CloneNotSupportedException {
        //test1();

        test2();
    }

    public static void test1() throws CloneNotSupportedException {
        Resume a = new Resume("大鸟");
        a.setPersionalInfo("男","28");
        a.setWorkExperience("2015-2017","XX公司");

        Resume b = (Resume) a.clone();
        b.setWorkExperience("2016-2018","YY企业");

        Resume c = (Resume) a.clone();
        c.setPersionalInfo("男","26");

        a.display();
        b.display();
        c.display();
    }

    public static void test2() throws CloneNotSupportedException {
        Resume2 a = new Resume2("大鸟");
        a.setPersionalInfo("男","28");
        a.setWorkExperience("2015-2017","XX公司");

        Resume2 b = (Resume2) a.clone();
        b.setWorkExperience("2016-2018","YY企业");

        Resume2 c = (Resume2) a.clone();
        c.setWorkExperience("2017-2018","ZZ企业");

        a.display();
        b.display();
        c.display();
    }
}
