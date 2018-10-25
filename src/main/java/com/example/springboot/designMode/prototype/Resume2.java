package com.example.springboot.designMode.prototype;

public class Resume2 implements Cloneable{

    private String name;
    private String sex;
    private String age;
    private WorkExperience workExperience;

    public Resume2(String name) {
        this.name = name;
        workExperience = new WorkExperience();
    }

    //设置个人信息
    public void setPersionalInfo(String sex,String age) {
        this.sex = sex;
        this.age = age;
    }

    //设置工作简历
    public void setWorkExperience(String timeArea, String company) {
        workExperience.setTimeArea(timeArea);
        workExperience.setCompany(company);
    }

    public void display() {
        System.out.println(name + " " + sex + " " + age);
        System.out.println("工作经历" + workExperience.getTimeArea() + " " + workExperience.getCompany());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        //浅复制
        //return super.clone();
        Resume2 resume2 = new Resume2(this.workExperience);
        resume2.name = this.name;
        resume2.sex = this.sex;
        resume2.age = this.age;
        return resume2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public WorkExperience getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(WorkExperience workExperience) {
        this.workExperience = workExperience;
    }

    /**
     * 深复制的时候需要将引用对象也复制
     * @param workExperience
     * @throws CloneNotSupportedException
     */
    public Resume2(WorkExperience workExperience) throws CloneNotSupportedException {
        this.workExperience = (WorkExperience) workExperience.clone();
    }
}
