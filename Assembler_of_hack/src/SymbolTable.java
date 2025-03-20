import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final Map<String,Integer> symbolMap;

    //构造函数，创建符号表，添加预定义符号
    public SymbolTable() {
        this.symbolMap = new HashMap<>() ;
        //预先添加预定义符号
        addPredefindSymbols();
    }

    /**
     * 向符号表中添加"SCREEN"、"KBD"等预定义的符号
     */
    private void addPredefindSymbols() {
        for(int i=0;i<16;i++){
            symbolMap.put("R"+i,i);
        }
        symbolMap.put("SCREEN",16384);
        symbolMap.put("KBD",24576);
        symbolMap.put("SP",0);
        symbolMap.put("LCL",1);
        symbolMap.put("ARG",2);
        symbolMap.put("THIS",3);
        symbolMap.put("THAT",4);
    }

    /**
     * 将(symbol,address)配对加入符号表
     * @param symbol
     * @param address
     */
    public void addEntry(String symbol,int address){
        symbolMap.put(symbol,address);
    }

    /**
     * 判断符号表是否包含了指定的symbol
     * @param symbol
     * @return Boolean
     */
    public Boolean contains(String symbol){
        return symbolMap.containsKey(symbol);
    }

    /**
     * 返回与symbol关联的地址
     * @param symbol
     * @return address
     */
    public int GetAddress(String symbol){
        return symbolMap.get(symbol);
    }
}
