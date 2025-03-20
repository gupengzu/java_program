import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Parser {
    //功能包括：
    //读取汇编语言命令并对其进行解析
    //提供“方便访问汇编命令成分（域和符号）”的方案
    //去掉所有的空格和注释
    private BufferedReader asmReader;
    private String currentCommand;
     //构造函数，打开asm文件，为语法解析作准备
    //令amsReader读取file中的内容
    public Parser(String file) throws FileNotFoundException {
        asmReader=new BufferedReader(new FileReader(file));
    }



    /**
     *用asmReader读取下一行，检查当前文件中是否还有未解析的命令
     * 如果有，就让currentCommand获得，返回true
     * 如果没有，返回false
     * @param
     * @return Boolean 是否读完
     *
     */
    public Boolean hasMoreCommands() throws IOException {
        String line;
        while ((line=asmReader.readLine())!=null){
            //trim函数返回一个新字符串，是原字符串去掉首尾空白字符后的结果
            line=line.trim();
            //跳过空白行和注释行
            if(!line.isEmpty()&&!line.startsWith("//")){
                currentCommand=line;
                return true;
            }
        }
        return false;
    }
    /**
     * 从输入中读取下一条命令，将其当作“当前命令”。
     * 仅当hasMoreCommands()为真时，才能调用本程序。
     * 最初始的时候，没有“当前命令”
     */
    public void advanced() throws IOException {
            //去掉命令中的注释
            int commentIndex=currentCommand.indexOf("//");
            if(commentIndex!=-1) {
                currentCommand = currentCommand.substring(0, commentIndex);
            }
            currentCommand.trim();
    }
    /**
     * 获取当前命令
     *
     * @return 当前命令
     */
    public String getCurrentCommand() {
        return currentCommand;
    }



    //这里插入一段内容来判断当前行汇编代码是否是合法的A命令，合法的L命令，合法的C命令
    /**
     * 判断是否是合法的A命令
     * @param line 当前行代码
     * @return boolean
     */
    public static boolean isValidACommand(String line) {
        // 去除字符串首尾的空白字符
        String input = line.trim();

        // 检查是否以 @ 开头
        if (!input.startsWith("@")) {
            return false;
        }

        // 提取 @ 后面的部分
        String valuePart = input.substring(1);

        // 检查是否为纯数字
        if (isNumeric(valuePart)) {
            try {
                int number = Integer.parseInt(valuePart);
                return number >= 0 && number <= 32767;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        // 检查是否为仅含字母的字符串
        return isAlphabetic(valuePart);
    }
    private static boolean isNumeric(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    private static boolean isAlphabetic(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }
    /**
     * 同上方，判断是否为L命令
     */
    public static boolean isValidLCommand(String line) {
        // 去除字符串首尾的空格
        String command = line.trim();

        // 检查字符串是否以 '(' 开头且以 ')' 结尾
        if (!command.startsWith("(") || !command.endsWith(")")) {
            return false;
        }

        // 提取括号内的标签名
        String label = command.substring(1, command.length() - 1);

        // 检查标签名是否为空
        if (label.isEmpty()) {
            return false;
        }

        // 检查标签名的第一个字符是否为数字
        if (Character.isDigit(label.charAt(0))) {
            return false;
        }

        // 检查标签名是否只包含合法字符
        for (char c : label.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && c != '_' && c != '.' && c != '$' && c != ':') {
                return false;
            }
        }

        return true;
    }
    /**
     * 同上方，判断是否为C命令
     */
    // 定义合法的 dest 取值
    private static final List<String> VALID_DESTS = Arrays.asList("", "M", "D", "MD", "A", "AM", "AD", "AMD");
    // 定义合法的 comp 取值
    private static final List<String> VALID_COMPS = Arrays.asList("0", "1", "-1", "D", "A", "!D", "!A", "-D", "-A",
            "D+1", "A+1", "D-1", "A-1", "D+A", "D-A", "A-D", "D&A", "D|A", "M", "!M", "-M", "M+1", "M-1", "D+M", "D-M", "M-D", "D&M", "D|M");
    // 定义合法的 jump 取值
    private static final List<String> VALID_JUMPS = Arrays.asList("", "JGT", "JEQ", "JGE", "JLT", "JNE", "JLE", "JMP");
    public static boolean isValidCCommand(String line) {
        // 移除首尾空格
        String command = line.trim();

        String dest = "";
        String comp = "";
        String jump = "";

        // 先处理 dest 和 comp 的分割
        if (command.contains("=")) {
            String[] parts = command.split("=", 2);
            dest = parts[0];
            command = parts[1];
        }

        // 再处理 comp 和 jump 的分割
        if (command.contains(";")) {
            String[] parts = command.split(";", 2);
            comp = parts[0];
            jump = parts[1];
        } else {
            comp = command;
        }

        // 检查 dest、comp 和 jump 是否合法
        return VALID_DESTS.contains(dest) && VALID_COMPS.contains(comp) && VALID_JUMPS.contains(jump);
    }



    /**
     * 判断命令类型:A_COMMAND,C_COMMAND,L_COMMAND
     * 当@Xxx中的Xxx是符号或者十进制数字时为A_COMMAND
     * D_COMMAND用于dest=comp;jump
     * L_COMMAND实际上是伪命令，当(Xxx)中的Xxx是符号时
     *
     * @param line 当前行的汇编代码
     * @return 是A，D，L中的哪种命令
     */
    public String commandType(String line){
        System.out.println("Processing line: " + line);
        if(line.startsWith("@")){
            //System.out.println("调用了Parser的commandType的A_COMMAND");
            return "A_COMMAND";
        } else if (isValidLCommand(line)) {
            return "L_COMMAND";
        } else if (isValidCCommand(line)) {
            //System.out.println("调用了Parser的commandType的C_COMMAND");
            return "C_COMMAND";
        }else {
            throw new IllegalArgumentException("Parser的commandType报错，这不是合法的A、L、C命令");
        }
    }
    /**
     * 仅当commandType()返回值是A_COMMAND或者是L_COMMAND时才可调用
     * 返回形如@Xxx或(Xxx)的当前命令的符号或者十进制（就是要去掉@和()）
     * @param line 当前行的汇编代码
     * @return String 符号或者十进制的内容
     * @throw 当不是A或L指令时抛出问题
     */
    public String symbol(String line){
        String is_A_or_L=commandType(line);
        if (is_A_or_L.startsWith("A")){
            return line.substring(1,line.length());
        }else if(is_A_or_L.startsWith("L")) {
            return line.substring(1,line.length()-1);
        }else {
            throw new IllegalArgumentException("Parser的symbol接收的不是A或L命令");
        }
    }



    /**
     * 返回当前C指令的dest助记符（具有8中可能的形式）
     * 且仅当是C_COMMAND时才调用
     * @param command 当前行的汇编代码
     * @return String 助记符
     * @throw 当不是C命令时抛出问题
     */
    public String dest(String command){
        if(isValidCCommand(command))
        {
            if(command.contains("="))
            {
                int index=command.indexOf("=");
                return command.substring(0,index).trim();
            }
            return "";
        }
        else
        {
            throw new IllegalArgumentException("Parser的dest函数报错，输入的不是C命令");
        }
    }
    /**
     * 返回当前C指令的comp助记符（具有28中可能的形式）
     * 仅当commandType()是C_COMMAND时才能调用
     * @param command
     * @return comp助记符
     * @throws IllegalArgumentException 当不是C指令时报错
     */
    public String comp(String command){
        if(isValidCCommand(command)) {
            int index_of_start = 0;
            int index_of_end = command.length();
            if(command.contains("=")){
                index_of_start=command.indexOf("=")+1;
            }
            if(command.contains(";")){
                index_of_end=command.indexOf(";");
            }
            return command.substring(index_of_start,index_of_end).trim();
        }else {
            throw new IllegalArgumentException("Parser的comp有问题，输入的不是C命令");
        }
    }
    /**
     * 返回当前C指令的jump助记符（具有8中可能的形式）
     * 仅当commandType()是C_COMMAND时才能调用
     * @param command
     * @return jump助记符
     *  @throws IllegalArgumentException 当不是C指令时报错
     */
    public String jump(String command){
        if(isValidCCommand(command)){
            if(command.contains(";")){
                int index_of_start=command.indexOf(";")+1;
                return command.substring(index_of_start,command.length());
            }else {
                return "";
            }
        }else {
            throw new IllegalArgumentException("Parser的jump报错，输入的不是C命令");
        }
    }

}
