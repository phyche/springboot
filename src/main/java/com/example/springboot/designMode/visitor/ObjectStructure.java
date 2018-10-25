package com.example.springboot.designMode.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * 对象结构
 */
public class ObjectStructure {

    private List<Person> elements = new ArrayList<>();

    public void attach(Person element){
        elements.add(element);
    }

    public void detach(Person element) {
        elements.remove(element);
    }

    public void display(Action visitor) {

        for (Person element : elements) {
            element.accept(visitor);
        }
    }
}
