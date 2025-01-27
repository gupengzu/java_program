import java.io.*;
import java.util.*;

public class Test5 {
    public static void main(String[] args) throws IOException {
//        BufferedReader br=new BufferedReader(new FileReader("src\\aaa\\a.txt"));
//        String line;
//        ArrayList<String>list=new ArrayList<>();
//        while ((line=br.readLine())!=null)
//        {
//            list.add(line);
//        }
//        br.close();

//        Collections.sort(list, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//            String[] listo1=o1.split("\\.");
//            String[]listo2=o2.split("\\.");
//            return Integer.parseInt(listo1[0])-Integer.parseInt(listo2[0]);
//            }
//        });

//        BufferedWriter bw=new BufferedWriter(new FileWriter("src\\aaa\\aa.txt"));
//        for(int i=0;i<list.size();i++){
//            bw.write(list.get(i));
//            bw.newLine();
//        }
//        bw.close();


        BufferedReader br=new BufferedReader(new FileReader("src\\aaa\\a.txt"));
        String line;
        TreeMap<Integer,String>tm=new TreeMap<>();
        while ((line=br.readLine())!=null)
        {
            String[]arr=line.split(".");
            tm.put(Integer.parseInt(arr[0]),arr[1]);
        }

        BufferedWriter bw=new BufferedWriter(new FileWriter("src\\aaa\\aa.txt"));
        Set<Map.Entry<Integer,String>>entries=tm.entrySet();
        for(Map.Entry<Integer,String>entry:entries)
        {
            bw.write(entry.getValue());
            bw.newLine();
        }
        bw.close();

    }
}
