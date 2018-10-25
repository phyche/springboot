package com.example.springboot.designMode.visitor;

/**
 * 访问者模式   把处理从数据结构中分离出来
 * 表示一个作用于某对象结构中的各元素操作。它可以使你在不改变各元素类的前提下定义作用于这些元素的新操作
 * 适用于数据结构相对稳定的系统
 *
 * 优点：增加新操作很容易，增加新操作就意味着增加一个新的访问者
 * 缺点：使增加新的数据结构变得困难
 */
public class VisitorMain {

    public static void main(String[] strg) {

        ObjectStructure o = new ObjectStructure();
        o.attach(new Men("男人"));
        o.attach(new Women("女人"));

        Success v1 = new Success("成功");
        o.display(v1);

        Failing v2 = new Failing("失败");
        o.display(v2);

        Amativeness v3 = new Amativeness("恋爱");
        o.display(v3);
    }
}
