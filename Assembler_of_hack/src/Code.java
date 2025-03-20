public class Code {
    //将Hack汇编语言助记符翻译为二进制码

    /**
     * 将dest助记符翻译为3bits的二进制码
     * @param dest_of_command
     * @return String形式的二进制码，比如："001";
     * @throws IllegalArgumentException 当输入不是合法的dest时报错
     */
    public static String dest(String dest_of_command){
        switch (dest_of_command){
            case "":
                return "000";
            case "M":
                return "001";
            case "D":
                return "010";
            case "MD":
                return "011";
            case "A":
                return "100";
            case "AM":
                return "101";
            case "AD":
                return "110";
            case "AMD":
                return "111";
            default:
                throw new IllegalArgumentException("Invalid dest mnemonic: " + dest_of_command);

        }
    }
    /**
     * 将comp助记符翻译为7bits的二进制码
     * @param comp_of_command
     * @return String形式的二进制码，比如:"0111111";
     * @throws IllegalArgumentException 当输入不是合法的comp时报错
     */
    public static String comp(String comp_of_command){
        switch (comp_of_command){
            case "0":
                return "0101010";
            case "1":
                return "0111111";
            case "-1":
                return "0111010";
            case "D":
                return "0001100";
            case "A":
                return "0110000";
            case "!D":
                return "0001101";
            case "!A":
                return "0110001";
            case "-D":
                return "0001111";
            case "-A":
                return "0110011";
            case "D+1":
                return "0011111";
            case "A+1":
                return "0110111";
            case "D-1":
                return "0001110";
            case "A-1":
                return "0110010";
            case "D+A":
                return "0000010";
            case "D-A":
                return "0010011";
            case "A-D":
                return "0000111";
            case "D&A":
                return "0000000";
            case "D|A":
                return "0010101";
            case "M":
                return "1110000";
            case "!M":
                return "1110001";
            case "-M":
                return "1110011";
            case "M+1":
                return "1110111";
            case "M-1":
                return "1110010";
            case "D+M":
                return "1000010";
            case "D-M":
                return "1010011";
            case "M-D":
                return "1000111";
            case "D&M":
                return "1000000";
            case "D|M":
                return "1010101";
            default:
                throw new IllegalArgumentException("Invalid comp mnemonic: " + comp_of_command);
        }
    }
    /**
     * 将jump助记符翻译为3bits的二进制码
     * @param jump_of_command
     * @return String形式的二进制码，比如:"010";
     * @throws IllegalArgumentException 当输入不是合法的comp时报错
     */
    public static String jump(String jump_of_command) {
        switch (jump_of_command) {
            case "":
                return "000";
            case "JGT":
                return "001";
            case "JEQ":
                return "010";
            case "JGE":
                return "011";
            case "JLT":
                return "100";
            case "JNE":
                return "101";
            case "JLE":
                return "110";
            case "JMP":
                return "111";
            default:
                throw new IllegalArgumentException("Invalid jump mnemonic: " + jump_of_command);
        }
    }

}
