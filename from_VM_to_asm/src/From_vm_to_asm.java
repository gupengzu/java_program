import java.io.*;
import java.io.*;
import java.io.*;

public class From_vm_to_asm {
    // Parser用于读取VM文件并解析命令，去掉空格和注释
    // CodeWriter将VM命令翻译为Hack汇编代码

    public static final String INPUT_FILE_SUFFIX = ".vm";
    public static final String OUTPUT_FILE_SUFFIX = ".asm";

    public From_vm_to_asm() {
    }

    public void assembler(String inputPath) throws IOException {
        File input = new File(inputPath);
        String outputFileName;
        if (input.isDirectory()) {
            outputFileName = input.getName() + OUTPUT_FILE_SUFFIX;
        } else {
            if (!input.getName().endsWith(INPUT_FILE_SUFFIX)) {
                throw new IllegalArgumentException("from_vm_to_asm的assembler报错，输入文件不是以 .vm 结尾");
            }
            outputFileName = input.getName().replace(INPUT_FILE_SUFFIX, OUTPUT_FILE_SUFFIX);
        }
        String outputFilePath = input.getParent() + File.separator + outputFileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            CodeWriter codeWriter = new CodeWriter(writer);

            codeWriter.writeInit();

            processFile(input, codeWriter);
        }
    }

    public void processFile(File input, CodeWriter codeWriter) throws IOException {
        if (input.isDirectory()) {
            File[] files = input.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(INPUT_FILE_SUFFIX)) {
                        assemble(file.getAbsolutePath(), codeWriter);
                    } else if (file.isDirectory()) {
                        processFile(file, codeWriter);
                    }
                }
            }
        } else if (input.isFile() && input.getName().endsWith(INPUT_FILE_SUFFIX)) {
            assemble(input.getAbsolutePath(), codeWriter);
        }
    }

    private static void assemble(String inputFilePath, CodeWriter codeWriter) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            Parser parser = new Parser(inputFilePath);

            // 设置当前文件名
            codeWriter.setFileName(new File(inputFilePath).getName());

            // 遍历所有vm命令
            while (parser.hasMoreCommands()) {
                parser.advance();
                String commandType = parser.commandType();
                switch (commandType) {
                    case "C_ARITHMETIC":
                        String arg1 = parser.arg1();
                        codeWriter.writeArithmetic(arg1);
                        break;
                    case "C_PUSH":
                    case "C_POP":
                        String segment = parser.arg1();
                        int index = parser.arg2();
                        codeWriter.writePushPop(commandType, segment, index);
                        break;
                    case "C_LABEL":
                        String label = parser.arg1();
                        codeWriter.writeLabel(label);
                        break;
                    case "C_GOTO":
                        label = parser.arg1();
                        codeWriter.writeGoto(label);
                        break;
                    case "C_IF":
                        label = parser.arg1();
                        codeWriter.writeIf(label);
                        break;
                    case "C_FUNCTION":
                        String functionName = parser.arg1();
                        int numLocals = parser.arg2();
                        codeWriter.writeFunction(functionName, numLocals);
                        break;
                    case "C_RETURN":
                        codeWriter.writeReturn();
                        break;
                    case "C_CALL":
                        functionName = parser.arg1();
                        int numArgs = parser.arg2();
                        codeWriter.writeCall(functionName, numArgs);
                        break;
                    default:
                        throw new IllegalArgumentException("from_vm_to_asm的assemble函数错误");
                }
            }
            parser.close();
        }
    }
}