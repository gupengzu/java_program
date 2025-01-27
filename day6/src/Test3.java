import java.io.*;

public class Test3 {
    public static void main(String[] args) throws IOException {
        File file1=new File("src\\ccc");
        file1.mkdirs();

        FileInputStream fis=new FileInputStream("src\\ccc\\c.txt");
        FileOutputStream fos=new FileOutputStream("src\\ccc\\c2.txt");

        int b;
        while ((b=fis.read())!=-1)
        {
            fos.write(b^2);
        }
        fos.close();
        fis.close();

    }
}
