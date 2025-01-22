
import java.util.ArrayList;
import java.util.Random;

public class test {
    public static void main(String[] args) {
        System.out.println(getCode());
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
