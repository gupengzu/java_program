import java.util.ArrayList;
import java.util.Scanner;

public class StudentSystem
{
    public static void StartStudentSystem()
    {
        ArrayList<Student>list=new ArrayList<>();

        Scanner sc=new Scanner(System.in);
        loop:while(true)
        {
            System.out.println("--------学生管理系统----------");
            System.out.println("1.增加学生");
            System.out.println("2.删除学生");
            System.out.println("3.修改学生");
            System.out.println("4.查询学生");
            System.out.println("5.退出系统");

            switch (sc.next())
            {
                case "1":
                   addStudent(list);
                    break;
                case "2":
                    deleteStudent(list);
                    break;
                case "3":
                    updateStudent(list);
                    break;
                case "4":
                    queryStudent(list);
                    break;
                case "5":
                    System.out.println("退出");
                    break loop;
                default:
                    System.out.println("没有这个选项");
                    break;
            }
        }
    }

    //添加学生
    public static void addStudent(ArrayList<Student>list)
    {
        System.out.println("添加学生");
        Student s=new Student();
        Scanner sc=new Scanner(System.in);
        System.out.println("id");
        while (true)
        {
            String id = sc.next();
            boolean issame = isSame(list, id);
            if (issame)
            {
                System.out.println("id重复");

            }
            else
            {
                s.setId(id);
                break;
            }
        }
        System.out.println("name");
        String name=sc.next();
        s.setName(name);

        System.out.println("age");
        int age=sc.nextInt();
        s.setAge(age);

        System.out.println("address");
        String address=sc.next();
        s.setAddress(address);

        list.add(s);
    }

    //删除学生
    public static void deleteStudent(ArrayList<Student>list)
    {
        System.out.println("删除学生");
        Scanner sc=new Scanner(System.in);
        String id=sc.next();
        int index=getIndex(list,id);
        if(index>=0)
        {
            list.remove(index);
            System.out.println("id为:"+id+"的学生删除成功");
        }
        else
        {
            System.out.println("id不存在，删除失败");
        }
    }

    //修改学生
    public static void updateStudent(ArrayList<Student>list)
    {
        System.out.println("修改学生");
        Scanner sc=new Scanner(System.in);
        String id=sc.next();
        int index=getIndex(list,id);

        if(index==-1)
        {
            System.out.println("nothing");
            return;
        }

        else
        {
            Student stu=list.get(index);
            System.out.println("new name?");
            String newname=sc.next();
            stu.setName(newname);

            System.out.println("new age?");
            int newage=sc.nextInt();
            stu.setAge(newage);

            System.out.println("new address?");
            String newaddress=sc.next();
            stu.setAddress(newaddress);

            System.out.println("学生信息修改成功");
        }
    }

    //查询学生
    public static void queryStudent(ArrayList<Student>list)
    {
        System.out.println("查询学生");
        if(list.size()==0)
        {
            System.out.println("无学生信息");
            return;
        }

        for (int i=0;i<list.size();i++)
        {
            Student stu=list.get(i);
            System.out.println(stu.getId()+"\t"+stu.getName()+"\t"+stu.getAge()+"\t" +stu.getAddress());
        }
    }

    //判断id是否在集合中存在
    public static boolean isSame(ArrayList<Student>list,String id)
    {
        for(int i=0;i<list.size();i++)
        {
            Student stu=list.get(i);
            String sid=stu.getId();
            if(sid.equals(id))
            {
                return true;
            }
        }
        return false;
    }

    //通过id获取索引的方法
    public static int getIndex(ArrayList<Student>list,String id)
    {
        for(int i=0;i<list.size();i++)
        {
            Student stu=list.get(i);
            String sid=stu.getId();
            if(sid.equals(id))
            {
                return i;
            }
        }
        return -1;
    }
}
