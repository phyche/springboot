package com.example.springboot.designMode.templateMethod;

/**
 * 模板方法模式
 * 定义一个操作中算法的骨架，而将一些步骤延迟到子类
 * 通过把不变的行为搬移到超类，去除子类中的重复代码来体现它的优势，提供了一个很好的代码复用平台
 *
 * 使用场景：
 * 当我们要完成在某一细节层次一致的一个过程或一系列步骤，但其个别步骤在更详细的层次上的实现可能不同时
 */
public class TemplateMethodMain {

    public static void main(String[] strg) {

        System.out.println("学生A抄的试卷：");
        TestPaper studentA = new TestPaperA();
        studentA.testQuestion1();
        studentA.testQuestion2();
        studentA.testQuestion3();

        System.out.println("学生B抄的试卷：");
        TestPaper studentB = new TestPaperB();
        studentB.testQuestion1();
        studentB.testQuestion2();
        studentB.testQuestion3();
    }
}
