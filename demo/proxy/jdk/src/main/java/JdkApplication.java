import proxy.StudentProxy;
import service.PersonService;
import service.impl.StudentServiceImpl;

import java.lang.reflect.Proxy;

/**
 * 基于JDK实现动态代理示例
 */
public class JdkApplication {
    public static void main(String[] args) {
        StudentProxy studentProxy = new StudentProxy(new StudentServiceImpl("JOJO"));
        PersonService student = (PersonService) Proxy.newProxyInstance(studentProxy.getClass().getClassLoader(), new Class[]{PersonService.class}, studentProxy);
        student.sleeping();
        student.working();
    }
}
