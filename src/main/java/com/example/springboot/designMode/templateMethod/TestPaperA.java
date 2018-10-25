package com.example.springboot.designMode.templateMethod;

public class TestPaperA extends TestPaper {

    @Override
    public String answer1() {
        return "A";
    }

    @Override
    public String answer2() {
        return "B";
    }

    @Override
    public String answer3() {
        return "C";
    }
}
