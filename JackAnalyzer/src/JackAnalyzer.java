import java.io.*;
//
//public class JackAnalyzer {
//    //分析器程序对给定的source进行操作，
//    // source是形如Xxx.jack的文件名称或者包含若干个此类文件的路径名
//    //对于每个源Xxx.jack文件，分析器按照如下逻辑进行处理：
//    //1.从Xxx.jack输入文件创建JackTokenizer;
//    //2.创建名为Xxx.xml的输出文件，准备写文件;
//    //3.使用CompilationEginge来将输入的JackTokenizer编译成输出文件
//    public void analyze(String inputPath) throws IOException {
//        File input=new File(inputPath);
//        if(input.isDirectory()){
//            for(File file:input.listFiles((dir,name)->name.endsWith(".jack"))){
//                processJackFile(file);
//            }
//        } else if (input.isFile()&&input.getName().endsWith(".jack")) {
//            processJackFile(input);
//        }else {
//            System.out.println("Invalid input. Please provide a .jack file or a directory containing .jack files.");
//        }
//    }
//
//    private void processJackFile(File file) throws IOException {
//        String outputFileName=file.getAbsolutePath().replace(".jack",".xml");
//        try (InputStream inputStream=new FileInputStream(file);
//             OutputStream outputStream=new FileOutputStream(outputFileName)){
//                 CompilationEngine engine=new CompilationEngine(inputStream,outputStream);
//                 engine.compileClass();
//                 engine.close();
//        }
//        System.out.println("Processed: " + file.getName());
//    }
//}

import java.io.*;

public class JackAnalyzer {
    /**
     * 分析输入路径（文件或目录），对所有 .jack 文件进行编译。
     *
     * @param inputPath 输入路径，可以是文件或目录
     * @throws IOException 如果文件操作失败
     */
    public void analyze(String inputPath) throws IOException {
        File input = new File(inputPath);

        if (input.isDirectory()) {
            // 如果是目录，遍历目录中的所有 .jack 文件
            for (File file : input.listFiles((dir, name) -> name.endsWith(".jack"))) {
                processFile(file);
            }
        } else if (input.isFile() && input.getName().endsWith(".jack")) {
            // 如果是单个 .jack 文件，直接处理
            processFile(input);
        } else {
            System.out.println("Invalid input. Please provide a .jack file or a directory containing .jack files.");
        }
    }

    /**
     * 处理单个 .jack 文件，调用 CompilationEngine 进行编译。
     *
     * @param file 要处理的 .jack 文件
     * @throws IOException 如果文件操作失败
     */
    private void processFile(File file) throws IOException {
        // 输出文件名，将 .jack 替换为 .xml
        String outputFileName = file.getAbsolutePath().replace(".jack", ".xml");

        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = new FileOutputStream(outputFileName)) {
            // 创建 CompilationEngine 并编译类
            CompilationEngine engine = new CompilationEngine(inputStream, outputStream);
            engine.compileClass();
            engine.close();
        }

        System.out.println("Processed: " + file.getName());
    }
}