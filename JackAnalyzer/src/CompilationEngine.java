import java.io.*;

//public class CompilationEginge {
//    //执行编译输出。
//    //从JackTokenizer中得到输入，然后将分析后的结果放入文件或输出流
//    //输出是通过一系列compilexxx()子程序生成的，
//    //每个子程序对应Jack语法中的一个语法要素xxx（可以是字元或构成语句的一般符号）
//    //这些程序之间约定如下：
//    //每个compilexxx()程序应该从CompilationEginge的输入中读取语法要素xxx，
//    //利用advance()函数取出当前要素xxx的下一个要素，并输出当前要素xxx的分析结果
//    //因此，仅当下一个要素是xxx时，才会调用该要素对应的分析函数compilexxx()
//
//    private JackTokenizer tokenizer;
//    private BufferedWriter writer;
//    private int indentLevel;
//
//    public CompilationEginge(InputStream input,OutputStream output) throws IOException {
//        tokenizer=new JackTokenizer(input);
//        writer=new BufferedWriter(new OutputStreamWriter(output));
//        indentLevel=0;
//    }
//
//    private void write(String content) throws IOException {
//        for(int i=0;i<indentLevel;i++){
//            writer.write("  ");
//        }
//        writer.write(content);
//        writer.newLine();
//    }
//
//    //编译整个类
//    public void compileClass() throws IOException {
//        write("<class>");
//        indentLevel++;
//
//        tokenizer.advance();
//        write("<keyword> " + tokenizer.keyword() + " </keyword>");//'class'
//
//        tokenizer.advance();
//        write("<identifier> " + tokenizer.identifier() + " </identifier>");//className
//
//        tokenizer.advance();
//        write("<symbol> " + tokenizer.symbol() + " </symbol>");//'{'
//
//        while (tokenizer.hasMoreTokens()) {
//            tokenizer.advance();
//            if (tokenizer.tokenType() == JackTokenizer.TokenType.KEYWORD) {
//                if (tokenizer.keyword().equals("static") || tokenizer.keyword().equals("field")) {
//                    compileClassVarDec();
//                } else if (tokenizer.keyword().equals("constructor") || tokenizer.keyword().equals("function") || tokenizer.keyword().equals("method")) {
//                    compileSubroutine();
//                } else {
//                    break;
//                }
//            }
//        }
//
//        write("<symbol> " + tokenizer.symbol() + " </symbol>");//'}'
//        indentLevel--;
//        write("</class>");
//    }
//
//    //编译静态声明或字段声明
//    public void compileClassVarDec() throws IOException {
//        write("<classVarDec>");
//        indentLevel++;
//        write("<keyword> " + tokenizer.keyword() + " </keyword>");//'static'or'field'
//        tokenizer.advance();
//        if(tokenizer.tokenType()==JackTokenizer.TokenType.KEYWORD){
//            write("<keyword> " + tokenizer.keyword() + " </keyword>");//type
//        }else {
//            write("<identifier> " + tokenizer.identifier() + " </identifier>");//className
//        }
//
//        tokenizer.advance();
//        write("<identifier> " + tokenizer.identifier() + " </identifier>");//varName
//
//        while (tokenizer.hasMoreTokens()){
//            tokenizer.advance();
//            if(tokenizer.symbol().equals(",")){
//                write("<symbol> " + tokenizer.symbol() + " </symbol>");//','
//                tokenizer.advance();
//                write("<identifier> " + tokenizer.identifier() + " </identifier>");//varName
//            }else {
//                break;
//            }
//        }
//        write("<symbol> " + tokenizer.symbol() + " </symbol>");//';'
//        indentLevel--;
//        write("</classVarDec>");
//    }
//
//    //编译整个方法、函数和构造函数
//    public void compileSubroutine() throws IOException {
//        write("<subroutineDec");
//        indentLevel++;
//
//        write("<keyword> " + tokenizer.keyword() + " </keyword>");//'constructor'/'function/'method'
//
//        tokenizer.advance();
//        if (tokenizer.tokenType() == JackTokenizer.TokenType.KEYWORD) {
//            write("<keyword> " + tokenizer.keyword() + " </keyword>");//'void'or type
//        } else {
//            write("<identifier> " + tokenizer.identifier() + " </identifier>");//className
//        }
//
//        tokenizer.advance();
//        write("<identifier> " + tokenizer.identifier() + " </identifier>");//subroutineName
//
//        tokenizer.advance();
//        write("<symbol> " + tokenizer.symbol() + " </symbol>");//'('
//
//        compileParameterList();
//
//        tokenizer.advance();
//        write("<symbol> " + tokenizer.symbol() + " </symbol>");//')'
//
//        compileSubroutineBody();
//
//        indentLevel--;
//        write("</subroutineDec>");
//    }
//
//    //编译参数列表（可能为空），不包含括号“()”
//    public void compileParameterList() throws IOException {
//        write("<parameterList>");
//        indentLevel++;
//
//        while (tokenizer.tokenType()!= JackTokenizer.TokenType.SYMBOL||!tokenizer.symbol().equals(")")){
//            if(tokenizer.tokenType()==JackTokenizer.TokenType.KEYWORD){
//                write("<keyword> " + tokenizer.keyword() + " </keyword>");//type
//            } else if (tokenizer.tokenType()==JackTokenizer.TokenType.IDENTIFIER) {
//                write("<identifier> " + tokenizer.identifier() + " </identifier>");//varName
//            } else if (tokenizer.tokenType()==JackTokenizer.TokenType.SYMBOL) {
//                write("<symbol> " + tokenizer.symbol() + " </symbol>");//','
//            }
//            tokenizer.advance();
//        }
//        indentLevel--;
//        write("<parameterList>");
//    }
//
//    public void compileSubroutineBody() throws IOException {
//        write("<subroutineBody>");
//        indentLevel++;
//
//        tokenizer.advance();
//        write("<symbol> " + tokenizer.symbol() + " </symbol>");//'{'
//
//        while (tokenizer.hasMoreTokens()){
//            if(tokenizer.tokenType()==JackTokenizer.TokenType.KEYWORD&&tokenizer.keyword().equals("var")){
//                compileVarDec();
//            }else {
//                break;
//            }
//        }
//
//        compileStatements();
//
//        write("<symbol> " + tokenizer.symbol() + " </symbol>");//'}'
//        indentLevel--;
//        write("<subroutineBody>");
//    }
//
//
//    //编译Var声明
//    public void compileVarDec() throws IOException {
//        write("<varDec>");
//        indentLevel++;
//
//        write("<keyword> " + tokenizer.keyword() + " </keyword>");//'var'
//
//        tokenizer.advance();
//        if(tokenizer.tokenType()==JackTokenizer.TokenType.KEYWORD){
//            write("<keyword> " + tokenizer.keyword() + " </keyword>");//type
//        }else {
//            write("<identifier> " + tokenizer.identifier() + " </identifier>");//className
//        }
//
//        tokenizer.advance();
//        write("<identifier> " + tokenizer.identifier() + " </identifier>");//varName
//
//        while (tokenizer.hasMoreTokens()) {
//            tokenizer.advance();
//            if (tokenizer.symbol().equals(",")) {
//                write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ','
//                tokenizer.advance();
//                write("<identifier> " + tokenizer.identifier() + " </identifier>"); // varName
//            } else {
//                break;
//            }
//        }
//
//        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ';'
//        indentLevel--;
//        write("</varDec>");
//    }
//
//    //编译一系列语句，不包含大括号"{}"
//    public void compileStatements() throws IOException {
//        write("<statements>");
//        indentLevel++;
//
//        while (tokenizer.tokenType()==JackTokenizer.TokenType.KEYWORD){
//            switch (tokenizer.keyword()) {
//                case "let":
//                    compileLet();
//                    break;
//                case "if":
//                    compileIf();
//                    break;
//                case "while":
//                    compileWhile();
//                    break;
//                case "do":
//                    compileDo();
//                    break;
//                case "return":
//                    compileReturn();
//                    break;
//                default:
//                    return;
//            }
//        }
//        indentLevel--;
//        write("</statements>");
//    }
//
//
//    //编译let语句
//    public void compileLet() throws IOException {
//        write("<letStatement>");
//        indentLevel++;
//
//        write("<keyword> " + tokenizer.keyword() + " </keyword>");//'let'
//
//        tokenizer.advance();
//        write("<identifier> " + tokenizer.identifier() + " </identifier>");//varName
//
//        tokenizer.advance();
//        if(tokenizer.symbol().equals("[")){
//            write("<symbol> " + tokenizer.symbol() + " </symbol>");//'['
//            compileExpression();
//            tokenizer.advance();
//            write("<symbol> " + tokenizer.symbol() + " </symbol>");//']'
//            tokenizer.advance();
//        }
//
//        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '='
//        compileExpression();
//
//        tokenizer.advance();
//        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ';'
//
//        indentLevel--;
//        write("</letStatement>");
//    }
//
//
//    //编译if语句，包含可选的else从句
//    public void compileIf() throws IOException {
//        write("<ifStatement>");
//        indentLevel++;
//
//        write("<keyword> \" + tokenizer.keyword() + \" </keyword>");//'if'
//
//        tokenizer.advance();
//        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '('
//        compileExpression();
//        tokenizer.advance();
//        write("<symbol>"+tokenizer.symbol()+"</symbol>");//')'
//
//        tokenizer.advance();
//        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '{'
//        compileStatements();
//        tokenizer.advance();
//        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '}'
//
//        if (tokenizer.hasMoreTokens() && tokenizer.keyword().equals("else")) {
//            tokenizer.advance();
//            write("<keyword> " + tokenizer.keyword() + " </keyword>"); // 'else'
//            tokenizer.advance();
//            write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '{'
//            compileStatements();
//            tokenizer.advance();
//            write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '}'
//        }
//
//        indentLevel--;
//        write("</ifStatement>");
//    }
//
//
//    //编译while语句
//    public void compileWhile() throws IOException {
//        write("<whileStatement>");
//        indentLevel++;
//
//        write("<keyword> " + tokenizer.keyword() + " </keyword>"); // 'while'
//
//        tokenizer.advance();
//        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '('
//        compileExpression();
//        tokenizer.advance();
//        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ')'
//
//        tokenizer.advance();
//        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '{'
//        compileStatements();
//        tokenizer.advance();
//        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '}'
//
//        indentLevel--;
//        write("</whileStatement>");
//    }
//
//
//    //编译do语句
//    public void compileDo() throws IOException {
//        write("<doStatement>");
//        indentLevel++;
//
//        write("<keyword> " + tokenizer.keyword() + " </keyword>"); // 'do'
//        compileSubroutineCall();
//
//        tokenizer.advance();
//        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ';'
//
//        indentLevel--;
//        write("</doStatement>");
//    }
//
//    //编译return语句
//    public void compileReturn() throws IOException {
//        write("<returnStatement>");
//        indentLevel++;
//
//        write("<keyword> " + tokenizer.keyword() + " </keyword>"); // 'return'
//
//        tokenizer.advance();
//        if (!tokenizer.symbol().equals(";")) {
//            compileExpression();
//        }
//
//        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ';'
//
//        indentLevel--;
//        write("</returnStatement>");
//    }
//
//
//    //编译一个表达式
//    public void compileExpression() throws IOException {
//        write("<expression>");
//        indentLevel++;
//
//        compileTerm();
//
//        while (tokenizer.hasMoreTokens()){
//            tokenizer.advance();
//            if(tokenizer.tokenType()==JackTokenizer.TokenType.SYMBOL&&"+-*/&|<>=".contains(tokenizer.symbol())){
//                write("<symbol> " + tokenizer.symbol() + " </symbol>");//op
//                tokenizer.advance();
//                compileTerm();
//            }else {
//                break;
//            }
//        }
//
//        indentLevel--;
//        write("</expression>");
//    }
//
//    //编译一个“term”。本程序在“从多种可能的分析规则中作出决策”的时候会遇到一点难度。
//    //特别是，如果当前字元为标识符，那么本程序就须要区分变量、数组、子程序这三种情况。
//    //通过提前查看下一个字元（可以为“[”、“(”或“.”）,就可以区分这三种可能性了。
//    //后续任何其他字元都不属于这个term，故不需要取用
//    public void compileTerm() throws IOException {
//        write("<term>");
//        indentLevel++;
//
//        if (tokenizer.tokenType() == JackTokenizer.TokenType.INT_CONST) {
//            write("<integerConstant> " + tokenizer.intVal() + " </integerConstant>");
//        } else if (tokenizer.tokenType() == JackTokenizer.TokenType.STRING_CONST) {
//            write("<stringConstant> " + tokenizer.stringVal() + " </stringConstant>");
//        } else if (tokenizer.tokenType() == JackTokenizer.TokenType.KEYWORD) {
//            write("<keyword> " + tokenizer.keyword() + " </keyword>");
//        } else if (tokenizer.tokenType() == JackTokenizer.TokenType.IDENTIFIER) {
//            write("<identifier> " + tokenizer.identifier() + " </identifier>");
//            tokenizer.advance();
//            if (tokenizer.symbol().equals("[")) {
//                write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '['
//                compileExpression();
//                tokenizer.advance();
//                write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ']'
//            } else if (tokenizer.symbol().equals("(") || tokenizer.symbol().equals(".")) {
//                compileSubroutineCall();
//            }
//        } else if (tokenizer.symbol().equals("(")) {
//            write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '('
//            compileExpression();
//            tokenizer.advance();
//            write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ')'
//        } else if ("-~".contains(tokenizer.symbol())) {
//            write("<symbol> " + tokenizer.symbol() + " </symbol>"); // unaryOp
//            tokenizer.advance();
//            compileTerm();
//        }
//
//        indentLevel--;
//        write("</term>");
//    }
//
//    public void compileSubroutineCall() throws IOException {
//        write("<subroutineCall>");
//        indentLevel++;
//
//        write("<identifier> " + tokenizer.identifier() + " </identifier>"); // subroutineName or className/varName
//        tokenizer.advance();
//
//        if(tokenizer.symbol().equals(".")){
//            write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '.'
//            tokenizer.advance();
//            write("<identifier> " + tokenizer.identifier() + " </identifier>"); // subroutineName
//            tokenizer.advance();
//        }
//
//        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '('
//        compileExpressionList();
//        tokenizer.advance();
//        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ')'
//
//        indentLevel--;
//        write("</subroutineCall>");
//    }
//
//    //编译由逗号分隔的表达式列表（可能为空）
//    public void compileExpressionList() throws IOException {
//        write("<expressionList>");
//        indentLevel++;
//
//        if (tokenizer.tokenType() != JackTokenizer.TokenType.SYMBOL || !tokenizer.symbol().equals(")")) {
//            compileExpression();
//            while (tokenizer.hasMoreTokens()) {
//                tokenizer.advance();
//                if (tokenizer.symbol().equals(",")) {
//                    write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ','
//                    tokenizer.advance();
//                    compileExpression();
//                } else {
//                    break;
//                }
//            }
//        }
//
//        indentLevel--;
//        write("</expressionList>");
//    }
//
//
//    public void close() throws IOException {
//        writer.close();
//    }
//}

import java.io.*;

public class CompilationEngine {
    private JackTokenizer tokenizer;
    private BufferedWriter writer;
    private int indentLevel;

    public CompilationEngine(InputStream input, OutputStream output) throws IOException {
        tokenizer = new JackTokenizer(input);
        writer = new BufferedWriter(new OutputStreamWriter(output));
        indentLevel = 0;
    }

    private void write(String content) throws IOException {
        for (int i = 0; i < indentLevel; i++) {
            writer.write("  ");
        }
        writer.write(content);
        writer.newLine();
    }

    public void compileClass() throws IOException {
        write("<class>");
        indentLevel++;

        tokenizer.advance();
        write("<keyword> " + tokenizer.keyword() + " </keyword>"); // 'class'

        tokenizer.advance();
        write("<identifier> " + tokenizer.identifier() + " </identifier>"); // className

        tokenizer.advance();
        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '{'

        while (tokenizer.hasMoreTokens()) {
            tokenizer.advance();
            if (tokenizer.tokenType() == JackTokenizer.TokenType.KEYWORD) {
                String keyword = tokenizer.keyword();
                if (keyword.equals("static") || keyword.equals("field")) {
                    compileClassVarDec();
                } else if (keyword.equals("constructor") || keyword.equals("function") || keyword.equals("method")) {
                    compileSubroutine();
                } else {
                    break; // 如果遇到其他关键字，退出循环
                }
            } else {
                break; // 如果不是关键字，退出循环
            }
        }

        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '}'
        indentLevel--;
        write("</class>");
    }

    public void compileClassVarDec() throws IOException {
        write("<classVarDec>");
        indentLevel++;

        write("<keyword> " + tokenizer.keyword() + " </keyword>"); // 'static' or 'field'

        tokenizer.advance();
        if (tokenizer.tokenType() == JackTokenizer.TokenType.KEYWORD) {
            write("<keyword> " + tokenizer.keyword() + " </keyword>"); // type
        } else {
            write("<identifier> " + tokenizer.identifier() + " </identifier>"); // className
        }

        tokenizer.advance();
        write("<identifier> " + tokenizer.identifier() + " </identifier>"); // varName

        while (tokenizer.hasMoreTokens()) {
            tokenizer.advance();
            if (tokenizer.symbol().equals(",")) {
                write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ','
                tokenizer.advance();
                write("<identifier> " + tokenizer.identifier() + " </identifier>"); // varName
            } else {
                break;
            }
        }

        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ';'
        indentLevel--;
        write("</classVarDec>");
    }

    public void compileSubroutine() throws IOException {
        write("<subroutineDec>");
        indentLevel++;

        write("<keyword> " + tokenizer.keyword() + " </keyword>"); // 'constructor'/'function'/'method'

        tokenizer.advance();
        if (tokenizer.tokenType() == JackTokenizer.TokenType.KEYWORD) {
            write("<keyword> " + tokenizer.keyword() + " </keyword>"); // 'void' or type
        } else {
            write("<identifier> " + tokenizer.identifier() + " </identifier>"); // className
        }

        tokenizer.advance();
        write("<identifier> " + tokenizer.identifier() + " </identifier>"); // subroutineName

        tokenizer.advance();
        if(!tokenizer.symbol().equals("(")) {
            throw new IllegalArgumentException("compileSubroutine报错，未读取到左括号");
        }
            write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '('

        tokenizer.advance();
        compileParameterList();

        // 确保在解析完参数列表后，正确读取右括号
        if (tokenizer.tokenType() != JackTokenizer.TokenType.SYMBOL || !tokenizer.symbol().equals(")")) {
            tokenizer.advance(); // 再次调用 advance() 以确保读取到右括号
        }
        if (!tokenizer.symbol().equals(")")) {
            throw new IllegalArgumentException("compileSubroutine报错，未读取到右括号");
        }
        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ')'

        compileSubroutineBody();

        indentLevel--;
        write("</subroutineDec>");
    }

    public void compileParameterList() throws IOException {
        write("<parameterList>");
        indentLevel++;

        while (tokenizer.tokenType() != JackTokenizer.TokenType.SYMBOL || !tokenizer.symbol().equals(")")) {
            if (tokenizer.tokenType() == JackTokenizer.TokenType.KEYWORD) {
                write("<keyword> " + tokenizer.keyword() + " </keyword>"); // type
            } else if (tokenizer.tokenType() == JackTokenizer.TokenType.IDENTIFIER) {
                write("<identifier> " + tokenizer.identifier() + " </identifier>"); // varName
            } else if (tokenizer.tokenType() == JackTokenizer.TokenType.SYMBOL) {
                write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ','
            }
            tokenizer.advance();
        }

        indentLevel--;
        write("</parameterList>");
    }

    public void compileSubroutineBody() throws IOException {
        this.write("<subroutineBody>");
        ++this.indentLevel;
        this.tokenizer.advance();
        this.write("<symbol> " + this.tokenizer.symbol() + " </symbol>"); // {

        while (this.tokenizer.hasMoreTokens()) {
            this.tokenizer.advance();
            if (this.tokenizer.tokenType() == JackTokenizer.TokenType.KEYWORD && this.tokenizer.keyword().equals("var")) {
                this.compileVarDec(); // 修复 var 声明解析
            } else {
                break;
            }
        }

        this.compileStatements();
        this.write("<symbol> " + this.tokenizer.symbol() + " </symbol>"); // }
        --this.indentLevel;
        this.write("</subroutineBody>");
    }

    public void compileVarDec() throws IOException {
        write("<varDec>");
        indentLevel++;

        write("<keyword> " + tokenizer.keyword() + " </keyword>"); // 'var'

        tokenizer.advance();
        if (tokenizer.tokenType() == JackTokenizer.TokenType.KEYWORD) {
            write("<keyword> " + tokenizer.keyword() + " </keyword>"); // type
        } else {
            write("<identifier> " + tokenizer.identifier() + " </identifier>"); // className
        }

        tokenizer.advance();
        write("<identifier> " + tokenizer.identifier() + " </identifier>"); // varName

        while (tokenizer.hasMoreTokens()) {
            tokenizer.advance();
            if (tokenizer.symbol().equals(",")) {
                write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ','
                tokenizer.advance();
                write("<identifier> " + tokenizer.identifier() + " </identifier>"); // varName
            } else {
                break;
            }
        }

        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ';'
        indentLevel--;
        write("</varDec>");
    }

    public void compileStatements() throws IOException {
        write("<statements>");
        indentLevel++;

        while (tokenizer.tokenType() == JackTokenizer.TokenType.KEYWORD) {
            switch (tokenizer.keyword()) {
                case "let":
                    compileLet();
                    break;
                case "if":
                    compileIf();
                    break;
                case "while":
                    compileWhile();
                    break;
                case "do":
                    compileDo();
                    break;
                case "return":
                    compileReturn();
                    break;
                default:
                    return;
            }
        }

        indentLevel--;
        write("</statements>");
    }

    public void compileLet() throws IOException {
        write("<letStatement>");
        indentLevel++;

        write("<keyword> " + tokenizer.keyword() + " </keyword>"); // 'let'

        tokenizer.advance();
        write("<identifier> " + tokenizer.identifier() + " </identifier>"); // varName

        tokenizer.advance();
        if (tokenizer.symbol().equals("[")) {
            write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '['
            compileExpression();
            tokenizer.advance();
            write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ']'
            tokenizer.advance();
        }

        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '='
        compileExpression();

        tokenizer.advance();
        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ';'

        indentLevel--;
        write("</letStatement>");
    }

    public void compileIf() throws IOException {
        write("<ifStatement>");
        indentLevel++;

        write("<keyword> " + tokenizer.keyword() + " </keyword>"); // 'if'

        tokenizer.advance();
        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '('
        compileExpression();
        tokenizer.advance();
        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ')'

        tokenizer.advance();
        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '{'
        compileStatements();
        tokenizer.advance();
        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '}'

        if (tokenizer.hasMoreTokens() && tokenizer.keyword().equals("else")) {
            tokenizer.advance();
            write("<keyword> " + tokenizer.keyword() + " </keyword>"); // 'else'
            tokenizer.advance();
            write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '{'
            compileStatements();
            tokenizer.advance();
            write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '}'
        }

        indentLevel--;
        write("</ifStatement>");
    }

    public void compileWhile() throws IOException {
        write("<whileStatement>");
        indentLevel++;

        write("<keyword> " + tokenizer.keyword() + " </keyword>"); // 'while'

        tokenizer.advance();
        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '('
        compileExpression();
        tokenizer.advance();
        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ')'

        tokenizer.advance();
        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '{'
        compileStatements();
        tokenizer.advance();
        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '}'

        indentLevel--;
        write("</whileStatement>");
    }

    public void compileDo() throws IOException {
        write("<doStatement>");
        indentLevel++;

        write("<keyword> " + tokenizer.keyword() + " </keyword>"); // 'do'
        tokenizer.advance();
        compileSubroutineCall();

        tokenizer.advance();
        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ';'

        indentLevel--;
        write("</doStatement>");
    }

    public void compileReturn() throws IOException {
        write("<returnStatement>");
        indentLevel++;

        write("<keyword> " + tokenizer.keyword() + " </keyword>"); // 'return'

        tokenizer.advance();
        if (!tokenizer.symbol().equals(";")) {
            compileExpression();
        }

        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ';'

        indentLevel--;
        write("</returnStatement>");
    }

    public void compileExpression() throws IOException {
        write("<expression>");
        indentLevel++;

        compileTerm();

        while (tokenizer.hasMoreTokens()) {
            tokenizer.advance();
            if (tokenizer.tokenType() == JackTokenizer.TokenType.SYMBOL && "+-*/&|<>=".contains(tokenizer.symbol())) {
                write("<symbol> " + tokenizer.symbol() + " </symbol>"); // op
                tokenizer.advance();
                compileTerm();
            } else {
                break;
            }
        }

        indentLevel--;
        write("</expression>");
    }

    public void compileTerm() throws IOException {
        write("<term>");
        indentLevel++;

        if (tokenizer.tokenType() == JackTokenizer.TokenType.INT_CONST) {
            write("<integerConstant> " + tokenizer.intVal() + " </integerConstant>");
        } else if (tokenizer.tokenType() == JackTokenizer.TokenType.STRING_CONST) {
            write("<stringConstant> " + tokenizer.stringVal() + " </stringConstant>");
        } else if (tokenizer.tokenType() == JackTokenizer.TokenType.KEYWORD) {
            write("<keyword> " + tokenizer.keyword() + " </keyword>");
        } else if (tokenizer.tokenType() == JackTokenizer.TokenType.IDENTIFIER) {
            // 处理标识符
            write("<identifier> " + tokenizer.identifier() + " </identifier>");
            tokenizer.advance();

            if (tokenizer.tokenType() == JackTokenizer.TokenType.SYMBOL) {
                if (tokenizer.symbol().equals("[")) {
                    // 数组访问
                    write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '['
                    compileExpression();
                    tokenizer.advance();
                    write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ']'
                } else if (tokenizer.symbol().equals("(") || tokenizer.symbol().equals(".")) {
                    // 子程序调用
                    compileSubroutineCall();
                }
            }
        } else if (tokenizer.symbol().equals("(")) {
            // 括号中的表达式
            write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '('
            compileExpression();
            tokenizer.advance();
            write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ')'
        } else if ("-~".contains(tokenizer.symbol())) {
            // 一元运算符
            write("<symbol> " + tokenizer.symbol() + " </symbol>"); // unaryOp
            tokenizer.advance();
            compileTerm();
        }

        indentLevel--;
        write("</term>");
    }

    public void compileSubroutineCall() throws IOException {
        // 子程序调用的起始部分
        write("<identifier> " + tokenizer.identifier() + " </identifier>"); // subroutineName or className/varName
        tokenizer.advance();

        if (tokenizer.symbol().equals(".")) {
            // 处理类名或变量名后接的子程序调用
            write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '.'
            tokenizer.advance();
            write("<identifier> " + tokenizer.identifier() + " </identifier>"); // subroutineName
            tokenizer.advance();
        }

        // 处理参数列表
        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // '('
        compileExpressionList();
        tokenizer.advance();
        write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ')'
    }

    public void compileExpressionList() throws IOException {
        write("<expressionList>");
        indentLevel++;

        if (tokenizer.tokenType() != JackTokenizer.TokenType.SYMBOL || !tokenizer.symbol().equals(")")) {
            compileExpression();
            while (tokenizer.hasMoreTokens()) {
                tokenizer.advance();
                if (tokenizer.symbol().equals(",")) {
                    write("<symbol> " + tokenizer.symbol() + " </symbol>"); // ','
                    tokenizer.advance();
                    compileExpression();
                } else {
                    break;
                }
            }
        }

        indentLevel--;
        write("</expressionList>");
    }

    public void close() throws IOException {
        writer.close();
    }
}