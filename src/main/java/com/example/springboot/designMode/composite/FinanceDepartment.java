package com.example.springboot.designMode.composite;

public class FinanceDepartment extends Company {

    public FinanceDepartment(String name) {
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
        System.out.println(name + "  公司财务收支管理");
    }
}
