import proxy.StudentServiceProxy;
import service.StudentService;

public class CGLIBApplication {
    public static void main(String[] args) {
        StudentService studentServiceProxy = (StudentService)(new StudentServiceProxy().getProxy(StudentService.class));
        studentServiceProxy.setName("JOJO");
        studentServiceProxy.working();
        studentServiceProxy.sleeping();
    }
}
