package com.example.springboot.designMode.templateMethod;

public class TestPaperB extends TestPaper {

    @Override
    public String answer1() {
        return "C";
    }

    @Override
    public String answer2() {
        return "B";
    }

    @Override
    public String answer3() {
        return "A";
    }
}
