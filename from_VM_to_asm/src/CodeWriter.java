import java.io.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.IOException;

public class CodeWriter {
    private BufferedWriter writer;
    private String fileName;
    private int labelCount = 0;

    public CodeWriter(BufferedWriter writer) throws IOException {
        this.writer = writer;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void writeArithmetic(String command) throws IOException {
        switch (command) {
            case "add":
                writeAdd();
                break;
            case "sub":
                writeSub();
                break;
            case "neg":
                writeNeg();
                break;
            case "eq":
                writeEq();
                break;
            case "gt":
                writeGt();
                break;
            case "lt":
                writeLt();
                break;
            case "and":
                writeAnd();
                break;
            case "or":
                writeOr();
                break;
            case "not":
                writeNot();
                break;
            default:
                throw new IllegalArgumentException("CodeWriter的writeArithmetic报错，接收到的命令不是算术命令");
        }
    }

    private void writeAdd() throws IOException {
        writer.write("@SP\nM=M-1\nA=M\nD=M\nA=A-1\nM=D+M\n");
    }

    private void writeSub() throws IOException {
        writer.write("@SP\nM=M-1\nA=M\nD=M\nA=A-1\nM=M-D\n");
    }

    private void writeNeg() throws IOException {
        writer.write("@SP\nA=M-1\nM=-M\n");
    }

    private void writeEq() throws IOException {
        String eqLabel = "EQ_" + labelCount++;
        writer.write("@SP\nM=M-1\nA=M\nD=M\nA=A-1\nD=M-D\n");
        writer.write("@" + eqLabel + "_TRUE\n");
        writer.write("D;JEQ\n");
        writer.write("@SP\nA=M-1\nM=0\n");
        writer.write("@" + eqLabel + "_END\n");
        writer.write("0;JMP\n");
        writer.write("(" + eqLabel + "_TRUE)\n");
        writer.write("@SP\nA=M-1\nM=-1\n");
        writer.write("(" + eqLabel + "_END)\n");
    }

    private void writeGt() throws IOException {
        String gtLabel = "GT_" + labelCount++;
        writer.write("@SP\nM=M-1\nA=M\nD=M\nA=A-1\nD=M-D\n");
        writer.write("@" + gtLabel + "_TRUE\n");
        writer.write("D;JGT\n");
        writer.write("@SP\nA=M-1\nM=0\n");
        writer.write("@" + gtLabel + "_END\n");
        writer.write("0;JMP\n");
        writer.write("(" + gtLabel + "_TRUE)\n");
        writer.write("@SP\nA=M-1\nM=-1\n");
        writer.write("(" + gtLabel + "_END)\n");
    }

    private void writeLt() throws IOException {
        String ltLabel = "LT_" + labelCount++;
        writer.write("@SP\nM=M-1\nA=M\nD=M\nA=A-1\nD=M-D\n");
        writer.write("@" + ltLabel + "_TRUE\n");
        writer.write("D;JLT\n");
        writer.write("@SP\nA=M-1\nM=0\n");
        writer.write("@" + ltLabel + "_END\n");
        writer.write("0;JMP\n");
        writer.write("(" + ltLabel + "_TRUE)\n");
        writer.write("@SP\nA=M-1\nM=-1\n");
        writer.write("(" + ltLabel + "_END)\n");
    }

    private void writeAnd() throws IOException {
        writer.write("@SP\nM=M-1\nA=M\nD=M\nA=A-1\nM=D&M\n");
    }

    private void writeOr() throws IOException {
        writer.write("@SP\nM=M-1\nA=M\nD=M\nA=A-1\nM=D|M\n");
    }

    private void writeNot() throws IOException {
        writer.write("@SP\nA=M-1\nM=!M\n");
    }

    public void writePushPop(String command, String segment, int index) throws IOException {
        if (command.equals("C_PUSH")) {
            switch (segment) {
                case "constant":
                    writePushConstant(index);
                    break;
                case "local":
                    writePushLocal(index);
                    break;
                case "argument":
                    writePushArgument(index);
                    break;
                case "this":
                    writePushThis(index);
                    break;
                case "that":
                    writePushThat(index);
                    break;
                case "temp":
                    writePushTemp(index);
                    break;
                case "pointer":
                    writePushPointer(index);
                    break;
                case "static":
                    if (fileName == null) {
                        throw new IllegalStateException("未设置文件名，无法处理static段");
                    }
                    writePushStatic(index);
                    break;
            }
        } else if (command.equals("C_POP")) {
            switch (segment) {
                case "local":
                    writePopLocal(index);
                    break;
                case "argument":
                    writePopArgument(index);
                    break;
                case "this":
                    writePopThis(index);
                    break;
                case "that":
                    writePopThat(index);
                    break;
                case "temp":
                    writePopTemp(index);
                    break;
                case "pointer":
                    writePopPointer(index);
                    break;
                case "static":
                    if (fileName == null) {
                        throw new IllegalStateException("未设置文件名，无法处理static段");
                    }
                    writePopStatic(index);
                    break;
            }
        } else {
            throw new IllegalArgumentException("CodeWriter的writePushPop报错，函数接收的不是C_PUSH或C_POP命令");
        }
    }

    private void writePushConstant(int index) throws IOException {
        writer.write("@" + index + "\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
    }

    private void writePushLocal(int index) throws IOException {
        writer.write("@LCL\nD=M\n@" + index + "\nA=D+A\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
    }

    private void writePushArgument(int index) throws IOException {
        writer.write("@ARG\nD=M\n@" + index + "\nA=D+A\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
    }

    private void writePushThis(int index) throws IOException {
        writer.write("@THIS\nD=M\n@" + index + "\nA=D+A\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
    }

    private void writePushThat(int index) throws IOException {
        writer.write("@THAT\nD=M\n@" + index + "\nA=D+A\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
    }

    private void writePushTemp(int index) throws IOException {
        int tempAddress = 5 + index;
        writer.write("@" + tempAddress + "\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
    }

    private void writePushPointer(int index) throws IOException {
        if (index == 0) {
            writer.write("@THIS\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
        } else {
            writer.write("@THAT\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
        }
    }

    private void writePushStatic(int index) throws IOException {
        writer.write("@" + fileName + "." + index + "\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
    }

    private void writePopLocal(int index) throws IOException {
        writer.write("@LCL\nD=M\n@" + index + "\nD=D+A\n@R13\nM=D\n@SP\nM=M-1\nA=M\nD=M\n@R13\nA=M\nM=D\n");
    }

    private void writePopArgument(int index) throws IOException {
        writer.write("@ARG\nD=M\n@" + index + "\nD=D+A\n@R13\nM=D\n@SP\nM=M-1\nA=M\nD=M\n@R13\nA=M\nM=D\n");
    }

    private void writePopThis(int index) throws IOException {
        writer.write("@THIS\nD=M\n@" + index + "\nD=D+A\n@R13\nM=D\n@SP\nM=M-1\nA=M\nD=M\n@R13\nA=M\nM=D\n");
    }

    private void writePopThat(int index) throws IOException {
        writer.write("@THAT\nD=M\n@" + index + "\nD=D+A\n@R13\nM=D\n@SP\nM=M-1\nA=M\nD=M\n@R13\nA=M\nM=D\n");
    }

    private void writePopTemp(int index) throws IOException {
        int tempAddress = 5 + index;
        writer.write("@SP\nM=M-1\nA=M\nD=M\n@" + tempAddress + "\nM=D\n");
    }

    private void writePopPointer(int index) throws IOException {
        if (index == 0) {
            writer.write("@SP\nM=M-1\nA=M\nD=M\n@THIS\nM=D\n");
        } else {
            writer.write("@SP\nM=M-1\nA=M\nD=M\n@THAT\nM=D\n");
        }
    }

    private void writePopStatic(int index) throws IOException {
        writer.write("@SP\nM=M-1\nA=M\nD=M\n@" + fileName + "." + index + "\nM=D\n");
    }

    public void writeInit() throws IOException {
        writer.write("@256\nD=A\n@SP\nM=D\n");
        writeCall("Sys.init", 0);
    }

    public void writeLabel(String label) throws IOException {
        writer.write("(" + label + ")\n");
    }

    public void writeGoto(String label) throws IOException {
        writer.write("@" + label + "\n0;JMP\n");
    }

    public void writeIf(String label) throws IOException {
        writer.write("@SP\nAM=M-1\nD=M\n@" + label + "\nD;JNE\n");
    }

    public void writeCall(String functionName, int numArgs) throws IOException {
        String returnAddress = "returnAddress_" + labelCount++;
        writer.write("@" + returnAddress + "\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
        writer.write("@LCL\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
        writer.write("@ARG\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
        writer.write("@THIS\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
        writer.write("@THAT\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n");
        writer.write("@SP\nD=M\n@5\nD=D-A\n@" + numArgs + "\nD=D-A\n@ARG\nM=D\n");
        writer.write("@SP\nD=M\n@LCL\nM=D\n");
        writer.write("@" + functionName + "\n0;JMP\n");
        writer.write("(" + returnAddress + ")\n");
    }

    public void writeReturn() throws IOException {
        writer.write("@LCL\nD=M\n@FRAME\nM=D\n");
        writer.write("@5\nD=D-A\nA=D\nD=M\n@RET\nM=D\n");
        writer.write("@SP\nM=M-1\nA=M\nD=M\n@ARG\nA=M\nM=D\n");
        writer.write("@ARG\nD=M+1\n@SP\nM=D\n");
        writer.write("@FRAME\nD=M\n@1\nD=D-A\nA=D\nD=M\n@THAT\nM=D\n");
        writer.write("@FRAME\nD=M\n@2\nD=D-A\nA=D\nD=M\n@THIS\nM=D\n");
        writer.write("@FRAME\nD=M\n@3\nD=D-A\nA=D\nD=M\n@ARG\nM=D\n");
        writer.write("@FRAME\nD=M\n@4\nD=D-A\nA=D\nD=M\n@LCL\nM=D\n");
        writer.write("@RET\nA=M\n0;JMP\n");
    }

    public void writeFunction(String functionName, int numLocals) throws IOException {
        writer.write("(" + functionName + ")\n");
        for (int i = 0; i < numLocals; i++) {
            writer.write("@SP\nA=M\nM=0\n@SP\nM=M+1\n");
        }
    }

    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}