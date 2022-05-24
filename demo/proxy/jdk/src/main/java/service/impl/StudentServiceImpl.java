package service.impl;

import service.PersonService;

/**
 * Student作为被代理类，必须实现接口
 */
public class StudentServiceImpl implements PersonService {

    private String name;

    public StudentServiceImpl(String name) {
        this.name = name;
    }

    public void sleeping() {
        System.out.println(this.name + "正在睡觉~");
    }

    public void working() {
        System.out.println(this.name + "正在工作~");
    }
}
