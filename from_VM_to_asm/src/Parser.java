import java.io.*;

public class Parser {
    //读取.vm文件，解析命令，去除所有空格和注释
    private BufferedReader vmReader;
    private String currentCommand;

    //构造函数，开始让vmReader读取file（一个.vm文件）的内容
    public Parser(String file) throws FileNotFoundException {
        vmReader= new BufferedReader(new FileReader(file));
    }

    /**
     * 判断是否还有更多的命令，
     * 如果有，就让currentCommand读取下一条命令，返回true
     * 如果没有，返回false
     * @throws IOException
     */
    public Boolean hasMoreCommands() throws IOException {
        String line;
        while ((line=vmReader.readLine())!=null){
            line.trim();
            if(!line.isEmpty()&&!line.startsWith("//")) {
                currentCommand = line;
                return true;
            }
        }
        return false;
    }
    //其实hasMoreCommands命令已经让currentCommand读取了命令，
    // advance作用是去掉命令中的注释和空格
    public void advance(){
        int index=currentCommand.indexOf("//");
        if(index!=-1){
            currentCommand=currentCommand.substring(0,index);
        }
        currentCommand.trim();
    }

    /**
     * 判断当前命令是VM命令的哪一种
     * 九种命令：
     * C_ARITHMETIC,C_PUSH,C_POP,C_LABEL,
     * C_GOTO,C_IF,C_FUNCTION,C_RETURN,C_CALL
     * @return如果正确就返回正确命令
     * @throw 如果不是合法命令就报错
     */
    public String commandType(){
        String[] parts=currentCommand.split("\\s+");
        String command_0=parts[0];
        switch (command_0){
            case "add":
            case "sub":
            case "neg":
            case "eq":
            case "gt":
            case "lt":
            case "and":
            case "or":
            case "not":
                return "C_ARITHMETIC";
            case "push":
                return "C_PUSH";
            case "pop":
                return "C_POP";
            case "label":
                return "C_LABEL";
            case "goto":
                return "C_GOTO";
            case "if-goto":
                return "C_IF";
            case "function":
                return "C_FUNCTION";
            case "return":
                return "C_RETURN";
            case "call":
                return "C_CALL";
            default:throw new IllegalArgumentException("Parser的commandType命令出错，没接收到形如：push constant 0的正确的命令");
        }
    }

    /**
     * 返回当前命令的第一个参数,即内存段。
     * 内存段包括：argument（参数段）,local（局部变量段）,static（静态变量段）,
     * constant（常数段）,this（通用段）,that（通用段）,pointer（指针段）,temp（临时变量段）
     * 如果当前命令类型为C_ARITHMETIC,则返回命令本身(如：add sub等).
     * 当前命令是C_RETURN时不应该调用该命令
     * @return 内存段
     * @throw 当输入C_RETURN时报错
     */
    public String arg1(){
        String commandType=commandType();
        String[]parts=currentCommand.split("\\s+");
        switch (commandType){
            case "C_RETURN":
                throw new IllegalArgumentException("Parser的arg1报错，arg1接收到了C_RETURN命令");
            case "C_ARITHMETIC":
                return parts[0];
            default:
                return parts[1];
        }
    }

    /**
     * 返回当前命令的第二个参数。
     * 仅当当前命令类型为C_PUSH,C_POP,C_FUNCTION,C_CALL时，才可调用
     * @return
     */
    public int arg2() {
        String commandType = commandType();
        String[] parts = currentCommand.split("\\s+");
        switch (commandType) {
            case "C_PUSH":
            case "C_POP":
            case "C_FUNCTION":
            case "C_CALL":
                return Integer.parseInt(parts[2]);
            default:
                throw new IllegalArgumentException("Parser的arg2报错，arg2接收到了非C_PUSH、C_POP、C_FUNCTION、C_CALL类型的命令，当前命令类型为: " + commandType);
        }
    }

    public void close() throws IOException {
        vmReader.close();
    }
}
