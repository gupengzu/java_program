import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;



public class APP
{
    public static void main(String[] args)
    {
        ArrayList<User>list=new ArrayList<>();
        System.out.println("欢迎来到学生管理系统");
        System.out.println("请选择1.登录，2.注册，3.忘记密码,4.退出");
        Scanner sc=new Scanner(System.in);
        loop:while (true)
        {
            String choose=sc.next();
            switch (choose)
            {
                case"1"->login(list);
                case"2"->register(list);
                case"3"->forgetPassword(list);
                case"4"->
                        {
                            System.out.println("退出");
                            System.exit(0);
                        }
                default ->System.out.println("没有这个选项");
            }
        }

    }

    private static void forgetPassword(ArrayList<User>list)
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("forgetPassword");
        System.out.println("请输入用户名");
        String username=sc.next();

        boolean flag=contains(list,username);
        if(!flag)
        {
            System.out.println("当前用户"+username+"未注册，请先注册");
            return;
        }

        //键盘录入身份证号码和手机号码
        System.out.println("请输入身份证号码");
        String personID=sc.next();
        System.out.println("请输入手机号码");
        String phoneNumber=sc.next();

        int index=findIndex(list,username);
        User user=list.get(index);
        if(!(user.getPersonID().equals(personID)&&user.getPhoneNumber().equals(phoneNumber)))
        {
            System.out.println("身份证号码或者手机号码有误，不能修改密码");
            return;
        }
        String password;
        while (true) {
            System.out.println("请输入新的密码");
            password = sc.next();
            System.out.println("请再次输入新的密码");
            String againPassword = sc.next();
            if (password.equals(againPassword)) {
                System.out.println("两次密码输入一致");
                break;
            } else {
                System.out.println("两次密码输入不一致，请重新输入");
                continue;
            }
        }
        user.setPassword(password);
        System.out.println("密码修改成功");
    }

    private static int findIndex(ArrayList<User> list, String username) {
        for(int i=0;i<list.size();i++)
        {
            User user=list.get(i);
            if(user.getUsername().equals(username))
            {
                return i;
            }
        }
        return -1;
    }

    private static void register(ArrayList<User>list)
    {
        //键盘录入用户名
        Scanner sc = new Scanner(System.in);
        String username;
        while (true) {
            System.out.println("请输入用户名");

            username = sc.next();
            boolean flag1 = checkUsername(username);
            if (!flag1)
            {
                System.out.println("用户名不符合要求，请重新输入");
                continue;
            }
            else
            {
                //判断用户名是否唯一
                boolean flag2=contains(list,username);
                if(flag2)
                {
                    System.out.println("用户名"+username+"已存在，请重新输入");
                }
                else
                {
                    System.out.println("用户名"+username+"可用");
                    break;
                }
            }
        }
        //键盘录入密码
        String password;
        while (true) {
            System.out.println("请输入要注册的密码");
            password = sc.next();
            System.out.println("请重新输入要注册的密码");
            String password2 = sc.next();
            if (!password2.equals(password))
            {
                System.out.println("两次密码不一样,请重新输入");
                continue;
            }
            else
            {
                break;
            }
        }
        //键盘录入身份证号码
        String personID;
        while (true)
        {
            System.out.println("请输入身份证号码");
            personID = sc.next();
            boolean flag = checkUserID(personID);
            if (flag) {
                System.out.println("满足要求");
                break;
            } else {
                System.out.println("身份证号码有误，请重新输入");
            }
        }
        //4.键盘录入手机号码
        String phoneNumber;
        while (true)
        {
            System.out.println("请输入手机号码,长度必须为11位，不能以0为开头，必须是数字");
            phoneNumber=sc.next();
            boolean flag4=checkPhoneNumber(phoneNumber);
            if(flag4)
            {
                System.out.println("手机号码格式正确");
                break;
            }
            else
            {
                System.out.println("手机号码格式错误");
                continue;
            }
        }
        //用户名，密码，身份证号码，手机号码
        User user1=new User(username,password,personID,phoneNumber);

        list.add(user1);
        System.out.println("注册成功");

        //遍历集合
        printlist(list);
    }

    private static void printlist(ArrayList<User> list) {
        for(int i=0;i<list.size();i++)
        {
            User user=list.get(i);
            System.out.println(user.getUsername()+","+user.getPassword()+","+user.getPersonID()+","+user.getPhoneNumber());
        }
    }

    private static boolean checkPhoneNumber(String phoneNumber) {
        //长度必须为11位
        if(phoneNumber.length()!=11)
        {
            return false;
        }
        //不能以0为开头
        if(phoneNumber.startsWith("0"))
        {
            return false;
        }
        //必须是数字
        for(int i=0;i<phoneNumber.length();i++)
        {
            char c=phoneNumber.charAt(i);
            if(!(c>='0'&&c<='9'))
            {
                return false;
            }
        }
        return true;
    }

    private static boolean checkUserID(String personID) {
        //长度为18位
        int len = personID.length();
        if (len != 18) {
            return false;
        }
        //不能以0为开头
        boolean is_0 = personID.startsWith("0");
        if (is_0) {
            return false;
        }
        //前17位必须是数字
        for (int i = 0; i < len - 1; i++) {
            char c = personID.charAt(i);
            if (!(c >= '0' && c <= '9')) {
                return false;
            }
        }
        //最后一位是数字，也可以是大写X或小写x
        char endChar = personID.charAt(len - 1);
        if ((endChar >= '0' && endChar <= '9') || endChar == 'x' || endChar == 'X')
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private static boolean contains(ArrayList<User> list, String username)
    {
        for(int i=0;i<list.size();i++)
        {
            User user1=list.get(i);
            String user1name=user1.getUsername();
            if(user1name.equals(username))
            {
                return true;
            }
        }
        return false;
    }

    private static boolean checkUsername(String username)
    {
        int len=username.length();
        if(len<3||len>15)
        {
            return false;
        }
        for(int i=0;i<len;i++)
        {
            char c=username.charAt(i);
            if(!((c>='a'&&c<='z')||(c>='A'&&c<='Z')||(c>='0'&&c<='9')))
            {
                return false;
            }
        }
        int count=0;
        for(int i=0;i<len;i++)
        {
            char c=username.charAt(i);
            if((c>='a'&&c<='z')||(c>='A'&&c<='Z'))
            {
                count++;
                break;
            }
        }

        return count>0;
    }

    private static void login(ArrayList<User>list)
    {
        Scanner sc=new Scanner(System.in);
        for (int i=0;i<3;i++)
        {
            System.out.println("输入用户名");
            String username=sc.next();
            boolean flag=contains(list,username);
            if(!flag)
            {
                System.out.println("用户名"+username+"未注册，请先注册再登录");
                return;
            }

            System.out.println("请输入密码");
            String password=sc.next();
            while (true) {
                String rightcode = getCode();
                System.out.println("当前正确的验证码是："+rightcode);
                System.out.println("请输入验证码");
                String code=sc.next();

                if(code.equalsIgnoreCase(rightcode))
                {
                    System.out.println("验证码正确");
                    break;
                }
                else
                {
                    System.out.println("验证码错误");
                    continue;
                }
            }

            //验证用户名和密码是否正确
            User userInfo=new User(username,password,null,null);
            boolean result=checkUserInfo(list,userInfo);
            if(result)
            {
                System.out.println("登录成功");
                StudentSystem st=new StudentSystem();
                st.StartStudentSystem();
                break;
            }
            else
            {
                System.out.println("登录失败，请重新登录");
                if(i==2)
                {
                    System.out.println("当前账号"+username+"被锁定，请联系客服：17692267492");
                    return;
                }
            }
        }
    }

    private static boolean checkUserInfo(ArrayList<User>list,User userInfo)
    {
        for (int i = 0; i < list.size(); i++) {
            User user1=list.get(i);
            if(user1.getUsername().equals(userInfo.getUsername())&&user1.getPassword().equals(userInfo.getPassword()))
            {
                return true;
            }
        }
        return false;
    }


    private static String getCode(){
        //1.创建一根集合，添加所有的大写和小写字母
        ArrayList<Character> list=new ArrayList<>();
        for(int i=0;i<26;i++) {
            list.add((char)('a'+i));
            list.add((char)('A'+i));
        }

        StringBuilder sb=new StringBuilder();

        Random r=new Random();
        for(int i=0;i<4;i++)
        {
            int index=r.nextInt(list.size());
            char c=list.get(index);
            sb.append(c);
        }

        int number=r.nextInt(10);
        sb.append(number);
        char[]arr=sb.toString().toCharArray();

        int randomIndex=r.nextInt(4);
        char temp=arr[randomIndex];
        arr[randomIndex]=arr[4];
        arr[4]=temp;

        return new String(arr);
    }


}
