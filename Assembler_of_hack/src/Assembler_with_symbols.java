import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Assembler_with_symbols {
    //汇编编译器由四个部分实现：
    //1.语法分析器（Parser）用来对输入的文件进行语法分析
    //2.编码模块（Code）用来提供所有汇编命令所对应的二进制代码
    //3.符号表（Symbol Table）用来处理符号
    //4.主程序（Main）用来驱动整个编译过程

    private SymbolTable symbolTable;
    private int nextAvailableRAMAddress;

    /**
     * 初始化符号表symbolTable，将nextAvailableRMAaddress指向16
     * 因为1-15的地址内都有预定义符号
     */
    public Assembler_with_symbols(){
        symbolTable=new SymbolTable();
        nextAvailableRAMAddress=16;
    }

    //核心函数，调用下方四个函数，处理汇编文件
    //然后调用Assembler_with_no_symbols生成文件
    public void assemble(String inputFilePath) throws IOException {
        List<String>assemblyLines=readAssemblyFile(inputFilePath);
        firstPass(assemblyLines);
        List<String>assembledLines=secondPass(assemblyLines);
        writeHackFile(inputFilePath,assembledLines);
        Assembler_with_no_symbols assembler_with_no_symbols=new Assembler_with_no_symbols();
        assembler_with_no_symbols.Assemble(inputFilePath.replace(".asm",".temp"));
    }

    /**
     * 读取汇编文件，去掉空行、空格和注释，生成List<String>
     * @param inputFilePath 读取的汇编文件路径
     * @return List<String>包含了所有汇编内容
     * @throws IOException
     */
    private List<String> readAssemblyFile(String inputFilePath) throws IOException {
        List<String>lines=new ArrayList<>();
        BufferedReader reader=new BufferedReader(new FileReader(inputFilePath));
        String line;
        while ((line=reader.readLine())!=null){
            line=line.trim();
            if(!line.isEmpty()&&!line.startsWith("//")){
                lines.add(line);
            }
        }
        return lines;
    }
    /**
     * 第一遍读取文件内容，利用数字来记录ROM地址，从0开始
     * 如果遇到A指令或C指令就让ROM自动加1，但是不记录
     * 如果遇到(Xxx)就记录(Xxx,ROM地址)键值对到符号表中
     * @param assemblyLines
     */
    private void firstPass(List<String>assemblyLines){
        int romAddress=0;
        for(String line:assemblyLines){
            if(line.startsWith("(")){
                String label=line.substring(1,line.length()-1);
                symbolTable.addEntry(label,romAddress);
            }else {
                romAddress++;
            }
        }
    }
    /**
     * 第二遍读取，进行符号替换和汇编
     * @param assemblyLines
     * @return List<String>这个里面都是C命令，@+数字
     */
    private List<String> secondPass(List<String>assemblyLines){
        List<String>result=new ArrayList<>();
        for(String line:assemblyLines){
            if(line.startsWith("@"))
            {
                String symbol=line.substring(1);
                if(symbol.matches("\\d+"))//正则表达式，判断是否是纯数字
                {
                    result.add(line);
                }
                else
                {
                    if(symbolTable.contains(symbol))
                    {
                        result.add("@"+symbolTable.GetAddress(symbol));
                    }
                    else
                    {
                        symbolTable.addEntry(symbol,nextAvailableRAMAddress);
                        result.add("@"+nextAvailableRAMAddress);
                        nextAvailableRAMAddress++;
                    }
                }
            }
            else if(!line.startsWith("("))
            {
                result.add(line);
            }
        }
        return result;
    }
    /**
     * 写入temp文件
     * @param inputFilePath
     * @param assembledLines
     * @throws IOException
     */
    private void writeHackFile(String inputFilePath,List<String>assembledLines) throws IOException {
        String outputFilePath=inputFilePath.replace(".asm",".temp");
        try(BufferedWriter writer =new BufferedWriter(new FileWriter(outputFilePath))) {
            for (String line : assembledLines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
}
