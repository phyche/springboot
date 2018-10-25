package com.example.springboot.designMode.composite;

import java.util.ArrayList;
import java.util.List;

public class ConcreteCompany extends Company{

    private List<Company> children = new ArrayList<>();

    public ConcreteCompany(String name) {
        super(name);
    }

    @Override
    public void add(Company company) {
        children.add(company);
    }

    @Override
    public void remove(Company company) {
        children.remove(company);
    }

    @Override
    public void display(int depth) {
        String a = "";
        for (int i = 0; i<depth; i++) {
            a += "-";
        }
        System.out.println(a + name);
        for (Company company : children) {
            company.display(depth + 2);
        }
    }

    @Override
    public void lineOfDuty() {
        for (Company company : children) {
            company.lineOfDuty();
        }
    }
}
