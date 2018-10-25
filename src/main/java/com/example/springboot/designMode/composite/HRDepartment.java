package com.example.springboot.designMode.composite;

public class HRDepartment extends Company {

    public HRDepartment(String name) {
        super(name);
    }

    @Override
    public void add(Company company) {

    }

    @Override
    public void remove(Company company) {

    }

    @Override
    public void display(int depth) {
        String a = "";
        for (int i = 0; i<depth; i++) {
            a += "-";
        }
        System.out.println(a + name);
    }

    @Override
    public void lineOfDuty() {
        System.out.println(name + "  员工招聘培训管理");
    }
}
