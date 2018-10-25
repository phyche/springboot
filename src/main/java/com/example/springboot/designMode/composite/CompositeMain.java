package com.example.springboot.designMode.composite;

/**
 *组合模式
 *将对象组合成树形结构以表示“部分--整体”的层次结构。组合模式使得用户对单个对象和组合对象是使用具有一致性
 *
 * 使用场合：
 * 需求中是体现部分与整体层次结构时，以及希望以后可以忽略组合对象与单个对象的不同，统一的使用组合结构中的所有对象时
 *
 */
public class CompositeMain {

    public static void main(String[] strg) {

        ConcreteCompany root = new ConcreteCompany("北京总公司");
        root.add(new HRDepartment("总公司人力资源部"));
        root.add(new FinanceDepartment("总公司财务部"));

        ConcreteCompany comp = new ConcreteCompany("上海华东分公司");
        comp.add(new HRDepartment("华东分公司人力资源部"));
        comp.add(new FinanceDepartment("华东分公司财务部"));
        root.add(comp);

        ConcreteCompany comp1 = new ConcreteCompany("南京办事处");
        comp1.add(new HRDepartment("南京办事处人力资源部"));
        comp1.add(new FinanceDepartment("南京办事处财务部"));
        comp.add(comp1);

        ConcreteCompany comp2 = new ConcreteCompany("杭州办事处");
        comp2.add(new HRDepartment("杭州办事处人力资源部"));
        comp2.add(new FinanceDepartment("杭州办事处财务部"));
        comp.add(comp2);

        root.display(1);
        root.lineOfDuty();
    }
}
