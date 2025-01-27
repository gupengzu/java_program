import java.io.*;
import java.nio.charset.Charset;

public class Test6 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Student stu=new Student(12,"zhangsan");
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("src\\aaa\\a.txt"));
        oos.writeObject(stu);
        oos.close();

        ObjectInputStream ois=new ObjectInputStream(new FileInputStream("src\\aaa\\a.txt"));
        Object o=ois.readObject();
        Student sd=(Student)o;
        System.out.println(sd);
        ois.close();
    }
}