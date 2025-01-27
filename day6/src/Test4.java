import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

public class Test4 {
    public static void main(String[] args) throws IOException {
        FileReader fr=new FileReader("src\\aaa\\a.txt");
        StringBuilder sb=new StringBuilder();
        int n;
        while ((n=fr.read())!=-1){
            sb.append((char)n);
        }
        Integer[] s1 = Arrays.stream(sb.toString()
                        .split(" "))
                .map(Integer::parseInt)
                .sorted()
                .toArray(Integer[]::new);


        FileWriter fw=new FileWriter("src\\aaa\\c.txt");
        String s=Arrays.toString(s1).replace(", ","-");
        String result=s.substring(1,s.length()-1);
        fw.write(result);
        fw.close();
    }
}
