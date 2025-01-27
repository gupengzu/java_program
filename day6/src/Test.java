import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) throws IOException {
        String str="";
        File f1=new File("C:\\Users\\谷鹏祖\\Desktop\\books");
        File file =new File("src\\aaa");
        file.mkdirs();
        File src=new File(file,"a.txt");
        boolean b=src.createNewFile();
        if(b)
        {
            System.out.println("创建成功");
        }
        boolean b2=haveAVI(file);
        System.out.println(b2);
        findAvi(src);


        HashMap<String,Integer>hm=getcount(f1);
        System.out.println(hm);
    }

    public static boolean haveAVI(File file)
    {
        File[] files = file.listFiles();

        for(File f:files)
        {
            if(f.isFile()&&f.getName().endsWith(".avi"))
            {
                return true;
            }
        }
        return false;
    }

    public static void findAvi(File src) {
        //1.进入文件夹
        File[] files = src.listFiles();
        //遍历数组，依次得到src里面的每一个文件或文件夹
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String name = file.getName();
                    if (name.endsWith(".avi")) {
                        System.out.println(file);
                    }

                } else {
                    findAvi(file);
                }
            }
        }
    }


    public static void delete(File src)
    {
        File[]s=src.listFiles();


            for(File file:s)
            {
                if(file.isFile())
                {
                    file.delete();
                }
                else {
                    delete(file);
                }
            }
    }

    public static long getlen(File src)
    {
        long len=0;
        File[] f=src.listFiles();
        for(File file:f)
        {
            if(file.isFile())
            {
                len+=file.length();
            }
            else
            {
                long ls=getlen(file);
                len+=ls;
            }
        }
        return len;
    }
    public static HashMap<String,Integer> getcount(File src)
    {
        File[]f=src.listFiles();
        HashMap<String,Integer>hm=new HashMap<>();
        for(File file:f)
        {
            if(file.isFile())
            {
                String name=file.getName();
                String[]arr=name.split("\\.");
                if(arr.length>=2)
                {
                    String endName=arr[arr.length-1];
                    if(hm.containsKey(endName))
                    {
                        int count=hm.get(endName);
                        count++;
                        hm.put(endName,count);
                    }
                    else
                    {
                        hm.put(endName,1);
                    }
                }
            }
            else
            {
                HashMap<String,Integer>sonMap=getcount(file);
                Set<Map.Entry<String,Integer>>entries=sonMap.entrySet();
                for(Map.Entry<String,Integer>entry:entries)
                {
                    String key=entry.getKey();
                    int value=entry.getValue();
                    if(hm.containsKey(key))
                    {
                        int count=hm.get(key);
                        count++;
                        hm.put(key,count);
                    }
                    else {
                        hm.put(key,value);
                    }
                }
            }

        }
        return hm;
    }
}
