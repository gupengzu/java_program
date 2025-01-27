import java.io.*;
import java.util.Arrays;

public class Test2 {
    public static void main(String[] args) throws IOException {
        //1.创建对象，表示数据源
        File src=new File("src\\aaa");
        //2.创建对象表示目的地
        File dest=new File("src\\bbb");
        //3.调用方法开始拷贝
        dest.mkdirs();
        //copydir(src,dest);
    }


    private static void copydir(File src,File dest) throws IOException {

        dest.mkdirs();

        File[] files=src.listFiles();
        for(File file:files)
        {
            if(file.isFile())
            {
                FileInputStream fis=new FileInputStream(file);
                FileOutputStream fos=new FileOutputStream(new File(dest,file.getName()));
                byte[]bytes=new byte[1024];
                int len;
                while ((len=fis.read(bytes))!=-1)
                {
                    fos.write(bytes,0,len);
                }
                fos.close();
                fis.close();
            }
            else {
                copydir(file,new File(dest,file.getName()));
            }
        }
    }
}
