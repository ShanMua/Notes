package service.impl;

import service.Person;

public class Student implements Person {

    private String name;

    public Student(String name) {
        this.name = name;
    }

    public void sleeping() {
        System.out.println(this.name + "正在睡觉~");
    }

    public void working() {
        System.out.println(this.name + "正在工作~");
    }
}
