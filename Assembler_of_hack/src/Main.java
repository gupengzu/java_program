import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        //Assembler_with_no_symbols assembler_with_no_symbols=new Assembler_with_no_symbols();
        //assembler_with_no_symbols.Assemble("G:/NandToTetris/official_things/nand2tetris/nand2tetris/projects/a06/max/MaxL.asm");
        Assembler_with_symbols assembler_with_symbols=new Assembler_with_symbols();
        assembler_with_symbols.assemble("G:/NandToTetris/official_things/nand2tetris/nand2tetris/projects/07/MemoryAccess/PointerTest/PointerTest.asm");
    }
}
