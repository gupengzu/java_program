import java.io.Serializable;



//SErializable等没有抽象方法，标记型接口
// 一旦实现这个接口，杰表示Student类可以被序列化

public class Student implements Serializable {
    private String name;
    private int age;
    private static final long serialVersionUID=1l;

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
