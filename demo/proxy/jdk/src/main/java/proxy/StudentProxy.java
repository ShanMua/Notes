package proxy;

import service.impl.Student;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class StudentProxy implements InvocationHandler {

    private Student student;

    public StudentProxy(Student student){
        this.student = student;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if(methodName.equals("sleeping")){
            System.out.println("现在是晚上11点：");
        }else if(methodName.equals("working")){
            System.out.println("现在是早上9点：");
        }
        return method.invoke(student, args);
    }
}
