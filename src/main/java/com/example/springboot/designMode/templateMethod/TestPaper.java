package com.example.springboot.designMode.templateMethod;

public class TestPaper {

    public void testQuestion1() {
        System.out.println("测试题1:A.1，B.2，C.3");
        System.out.println("答案：" + answer1());
    }

    public String answer1() {
        return "";
    }

    public void testQuestion2() {
        System.out.println("测试题2:A.4，B.5，C.6");
        System.out.println("答案：" + answer2());
    }

    public String answer2() {
        return "";
    }

    public void testQuestion3() {
        System.out.println("测试题3:A.7，B.8，C.9");
        System.out.println("答案：" + answer3());
    }

    public String answer3() {
        return "";
    }

}
