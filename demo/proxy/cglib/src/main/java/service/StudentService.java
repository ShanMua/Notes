package service;

import lombok.Data;

/**
 * 基于CGLIB实现动态代理，该被代理类不需要实现接口
 */
@Data
public class StudentService {

    private String name;

    public void sleeping() {
        System.out.println(this.name + "正在睡觉~");
    }

    public void working() {
        System.out.println(this.name + "正在工作~");
    }
}
