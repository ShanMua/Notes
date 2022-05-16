import proxy.StudentProxy;
import service.Person;
import service.impl.Student;

import java.lang.reflect.Proxy;

/**
 * 基于JDK实现动态代理示例
 */
public class JdkApplication {
    public static void main(String[] args) {
        StudentProxy studentProxy = new StudentProxy(new Student("JOJO"));
        Person student = (Person) Proxy.newProxyInstance(studentProxy.getClass().getClassLoader(), new Class[]{Person.class}, studentProxy);
        student.sleeping();
        student.working();
    }
}
