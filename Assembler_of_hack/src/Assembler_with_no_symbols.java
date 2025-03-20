import java.io.*;

public class Assembler_with_no_symbols {
    //汇编编译器由四个部分实现：
    //1.语法分析器（Parser）用来对输入的文件进行语法分析
    //2.编码模块（Code）用来提供所有汇编命令所对应的二进制代码
    //3.符号表（Symbol Table）用来处理符号,但这个是无符号的汇编编译器，不用这一步
    //4.主程序（Main）用来驱动整个编译过程
    public static final String INPUT_FILE_SUFFIX=".asm";
    public static final String TEMP_FILE_SUFFIX=".temp";
    public static final String OUTPUT_FILE_SUFFIX=".hack";

    public Assembler_with_no_symbols() {
    }

    public void Assemble(String inputFilePath) {
        File inputFile=new File(inputFilePath);
        String fileNameWithoutExtension = inputFile.getName();
        //如果是Assembler_with_symbol生成的文件，就是以hack为结尾，故调用else if
        if (fileNameWithoutExtension.endsWith(INPUT_FILE_SUFFIX)) {
            fileNameWithoutExtension = fileNameWithoutExtension.replace(INPUT_FILE_SUFFIX, "");
        } else if (fileNameWithoutExtension.endsWith(TEMP_FILE_SUFFIX)) {
            fileNameWithoutExtension = fileNameWithoutExtension.replace(TEMP_FILE_SUFFIX, "");
        }
        String parentDirectory=inputFile.getParent();
        String outputFilePath=parentDirectory+File.separator+fileNameWithoutExtension+OUTPUT_FILE_SUFFIX;
        assemble_with_no_symbols(inputFilePath,outputFilePath);
    }

    private static void assemble_with_no_symbols(String inputFilePath,String outputFilePath){
        try (BufferedReader reader=new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer=new BufferedWriter(new FileWriter(outputFilePath))){
            Parser parser=new Parser(inputFilePath);
            Code code=new Code();
            while (parser.hasMoreCommands()){
                parser.advanced();
                String commandType= parser.commandType(parser.getCurrentCommand());
                if("A_COMMAND".equals(commandType)){
                    String symbol=parser.symbol(parser.getCurrentCommand());
                    System.out.println("A 指令符号: " + symbol);
                    try {
                        int decimalValue=Integer.parseInt(symbol);
                        String binaryRepresentation=Integer.toBinaryString(decimalValue);
                        //补全到16位
                        while (binaryRepresentation.length()<16){
                            binaryRepresentation="0"+binaryRepresentation;
                        }
                        writer.write(binaryRepresentation+"\n");
                    } catch (NumberFormatException e) {
                        System.err.println("A 指令符号转换错误: " + e.getMessage());
                    }
                } else if ("C_COMMAND".equals(commandType)) {
                    String dest=parser.dest(parser.getCurrentCommand());
                    String comp=parser.comp(parser.getCurrentCommand());
                    String jump=parser.jump(parser.getCurrentCommand());

                    String destBinary=code.dest(dest);
                    String compBinary=code.comp(comp);
                    String jumpBinary=code.jump(jump);

                    String binaryCode="111"+compBinary+destBinary+jumpBinary;
                    writer.write(binaryCode+"\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
