package com.example.springboot.designMode.builder;

public class PersonDirector {

    private PersonBuilder personBuilder;

    public PersonDirector(PersonBuilder personBuilder) {
        this.personBuilder = personBuilder;
    }

    public void createPerson() {
        personBuilder.BuildHead();
        personBuilder.BuildBody();
        personBuilder.BuildArmLeft();
        personBuilder.BuildArmRight();
        personBuilder.BuildLegLeft();
        personBuilder.BuildLegRight();
    }
}
