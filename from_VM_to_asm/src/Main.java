import java.io.IOException;

//TIP 要<b>运行</b>代码，请按 <shortcut actionId="Run"/> 或
// 点击装订区域中的 <icon src="AllIcons.Actions.Execute"/> 图标。
public class Main {
    public static void main(String[] args) throws IOException {
        From_vm_to_asm fromVmToAsm=new From_vm_to_asm();
        fromVmToAsm.assembler("G:/NandToTetris/official_things/nand2tetris/nand2tetris/projects/08/FunctionCalls/SimpleFunction");
    }
}