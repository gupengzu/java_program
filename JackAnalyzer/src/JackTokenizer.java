import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//
//public class JackTokenizer {
//    //从输入流中删除所有的注释和空格，
//    // 并根据Jack语法的规则将输入流分解为Jack语言的字元（终结符）
//
//    public enum TokenType{ KEYWORD, SYMBOL, IDENTIFIER, INT_CONST, STRING_CONST }
//
//    private static final Set<String> KEYWORDS=Set.of(
//            "class", "constructor", "function", "method", "field", "static", "var",
//            "int", "char", "boolean", "void", "true", "false", "null", "this",
//            "let", "do", "if", "else", "while", "return"
//    );
//
//    private static final Set<Character>SYMBOLS= Set.of(
//            '{', '}', '(', ')', '[', ']', '.', ',', ';', '+', '-', '*', '/', '&', '|', '<', '>', '=', '~'
//    );
//
//    private BufferedReader reader;
//    private TokenType tokenType;
//    private String currentToken;
//
//    //构造函数，打开输入文件/输入流，准备进行字元转换操作
//    public JackTokenizer(InputStream input) throws IOException {
//        reader=new BufferedReader(new InputStreamReader(input));
//        currentToken=null;
//    }
//
//
//
//
//
//    public boolean hasMoreTokens() throws IOException {
//        return reader.ready();
//    }
//
//
//    public void advance() throws IOException {
//        currentToken=getNextToken();
//        if(currentToken==null)return;
//        if(KEYWORDS.contains(currentToken)){
//            tokenType=TokenType.KEYWORD;
//        }else if (SYMBOLS.contains(currentToken.charAt(0))){
//            tokenType=TokenType.SYMBOL;
//        } else if (currentToken.matches("\\d+")) {
//            tokenType=TokenType.INT_CONST;
//        } else if (currentToken.startsWith("\"")) {
//            tokenType=TokenType.STRING_CONST;
//        }else {
//            tokenType=TokenType.IDENTIFIER;
//        }
//    }
//
//    public TokenType tokenType(){
//        return tokenType;
//    }
//
//    public String keyword() {
//        return currentToken;
//    }
//
//    public String symbol() {
//        return currentToken;
//    }
//
//    public String identifier() {
//        return currentToken;
//    }
//
//    public int intVal() {
//        return Integer.parseInt(currentToken);
//    }
//
//    public String stringVal() {
//        return currentToken.substring(1, currentToken.length() - 1);
//    }
//
//
//
//    private String getNextToken() throws IOException {
//        StringBuilder token=new StringBuilder();
//        int c;
//        while ((c=reader.read())!=-1){
//            char ch=(char) c;
//            if(Character.isWhitespace(ch)){
//                if(token.length()>0)break;
//                continue;
//            }
//            if (SYMBOLS.contains(ch)){
//                if(token.length()==0)token.append(ch);
//                break;
//            }
//            if (ch=='"'){
//                if(token.length()==0){
//                    token.append(ch);
//                    while ((c=reader.read())!=-1&&(char)c!='"'){
//                        token.append((char)c);
//                    }
//                    token.append('"');
//                }
//                break;
//
//            }
//            break;
//        }
//        return token.length()>0?token.toString():null;
//    }
//
//}

import java.io.*;
import java.util.*;

public class JackTokenizer {
    public enum TokenType { KEYWORD, SYMBOL, IDENTIFIER, INT_CONST, STRING_CONST }

    private BufferedReader reader;
    private String currentToken;
    private TokenType tokenType;
    private static final Set<String> KEYWORDS = Set.of(
            "class", "constructor", "function", "method", "field", "static", "var",
            "int", "char", "boolean", "void", "true", "false", "null", "this",
            "let", "do", "if", "else", "while", "return"
    );
    private static final Set<Character> SYMBOLS = Set.of(
            '{', '}', '(', ')', '[', ']', '.', ',', ';', '+', '-', '*', '/', '&', '|', '<', '>', '=', '~'
    );

    private String nextLine;
    private int currentIndex;

    public JackTokenizer(InputStream input) throws IOException {
        reader = new BufferedReader(new InputStreamReader(input));
        nextLine = null;
        currentIndex = 0;
        advanceLine();
    }

    private void advanceLine() throws IOException {
        while ((nextLine = reader.readLine()) != null) {
            nextLine = nextLine.trim();
            // 忽略空行和以 // 或 /* 开头的注释
            if (nextLine.isEmpty() || nextLine.startsWith("//") || nextLine.startsWith("/*")) {
                continue;
            }
            // 处理行内注释
            int commentIndex = nextLine.indexOf("//");
            if (commentIndex != -1) {
                nextLine = nextLine.substring(0, commentIndex).trim();
            }
            if (!nextLine.isEmpty()) {
                break;
            }
        }
        currentIndex = 0;
    }

    public boolean hasMoreTokens() throws IOException {
        return nextLine != null;
    }

    public void advance() throws IOException {
        currentToken = getNextToken();
        if (currentToken == null) return;

        if (KEYWORDS.contains(currentToken)) {
            tokenType = TokenType.KEYWORD;
        } else if (SYMBOLS.contains(currentToken.charAt(0))) {
            tokenType = TokenType.SYMBOL;
        } else if (currentToken.matches("\\d+")) {
            tokenType = TokenType.INT_CONST;
        } else if (currentToken.startsWith("\"")) {
            tokenType = TokenType.STRING_CONST;
        } else {
            tokenType = TokenType.IDENTIFIER;
        }

        // 调试日志
        System.out.println("Token: " + currentToken + ", Type: " + tokenType);
    }
    public TokenType tokenType() {
        return tokenType;
    }

    public String keyword() {
        return currentToken;
    }

    public String symbol() {
        return currentToken;
    }

    public String identifier() {
        return currentToken;
    }

    public int intVal() {
        return Integer.parseInt(currentToken);
    }

    public String stringVal() {
        return currentToken.substring(1, currentToken.length() - 1);
    }

    private String getNextToken() throws IOException {
        StringBuilder token = new StringBuilder();
        while (nextLine != null) {
            if (currentIndex >= nextLine.length()) {
                advanceLine();
                continue;
            }

            char ch = nextLine.charAt(currentIndex++);

            // Skip whitespace
            if (Character.isWhitespace(ch)) {
                if (token.length() > 0) break;
                continue;
            }

            // Handle symbols
            if (SYMBOLS.contains(ch)) {
                if (token.length() == 0) {
                    token.append(ch); // 单独处理符号
                } else {
                    currentIndex--; // 回退一个字符，保留当前符号供下次解析
                }
                break;
            }

            // Handle string constants
            if (ch == '"') {
                if (token.length() == 0) {
                    token.append(ch);
                    while (currentIndex < nextLine.length() && nextLine.charAt(currentIndex) != '"') {
                        token.append(nextLine.charAt(currentIndex++));
                    }
                    if (currentIndex < nextLine.length()) {
                        token.append(nextLine.charAt(currentIndex++));
                    }
                }
                break;
            }

            // Append character to token
            token.append(ch);
        }
        return token.length() > 0 ? token.toString() : null;
    }
}