package edu.calculator.utils;

import edu.calculator.entity.NumberType;
import edu.calculator.entity.impl.FractionalNumber;
import edu.calculator.entity.impl.IntegralNumber;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileOperator {
    private final static String EXERCISES_PATH = "Exercises.txt";
    private final static String ANSWERS_PATH = "Answers.txt";
    private final static String GRADE_PATH = "Grade.txt";

    public static void writeFile(List<String> exercisesList, List<String> answersList) {
        File efile = new File(EXERCISES_PATH);
        File afile = new File(ANSWERS_PATH);

        BufferedWriter ewriter = null, awriter = null;
        try {
            ewriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(efile), StandardCharsets.UTF_8));
            awriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afile), StandardCharsets.UTF_8));

            for(int i = 1; i <= exercisesList.size(); i++) {
                ewriter.write(i + ". " + exercisesList.get(i - 1) + " = " + "\n");
            }
            ewriter.flush();
            for(int i = 1; i <= answersList.size(); i++) {
                awriter.write(i + ". " + answersList.get(i - 1) + "\n");
            }
            awriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ewriter != null)
                    ewriter.close();
                if(awriter != null)
                    awriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("生成的题目已存入文件：" + efile.getAbsolutePath());
        System.out.println("题目的答案已存入文件：" + afile.getAbsolutePath());
    }
    public static void writeGrade(List<Integer> correctList, List<Integer> wrongList) {
        if(correctList == null || wrongList == null) {
            return;
        }
        File f = new File(GRADE_PATH);
        BufferedWriter bwrite = null;
        try {
            bwrite = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8));
            bwrite.write("Correct: " + correctList.size() + " (");
            for(int i = 0; i < correctList.size() - 1; i++) {
                bwrite.write(correctList.get(i) + ", ");
            }
            if(correctList.size() != 0) {
                bwrite.write("" + correctList.get(correctList.size() - 1));
            }
            bwrite.write(")\n");
            bwrite.write("Wrong: " + wrongList.size() + " (");
            for(int i = 0; i < wrongList.size() - 1; i++) {
                bwrite.write(wrongList.get(i) + ", ");
            }
            if(wrongList.size() != 0) {
                bwrite.write("" + wrongList.get(wrongList.size() - 1));
            }
            bwrite.write(")\n");
            bwrite.flush();
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bwrite != null)
                    bwrite.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("答案正误的比较结果已存入文件：" + f.getAbsolutePath());
    }
    public static List<NumberType> readAnswers(String answersFilePath) {
        List<NumberType> ansList = new ArrayList<>();
        File f = new File(answersFilePath);
        //判断文件是否存在
        if(!f.exists()) {
            System.out.println("文件 " + answersFilePath + " 不存在");
            return null;
        }
        BufferedReader bread = null;
        String str = null;
        String regex = "[\n~!@#$%^&*()+=|{}:;,\\[\\]<>?~！@#￥%……&*（）+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        String[] strSplit;
        try {
            bread = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));
            //逐行读取文件
            while(null != (str = bread.readLine())) {
                matcher = pattern.matcher(str);
                strSplit = matcher.replaceAll("").split("\\s+");
                ansList.add(getNumber(strSplit[1]));
            }
        } catch (IOException e) {
            System.out.println("读取文件 " + answersFilePath + " 时出现异常");
        } finally {
            if(null != bread) {
                try {
                    bread.close();
                } catch (IOException e) {
                    System.out.println("读文件缓冲流关闭异常");
                }
            }
        }
        return ansList;
    }
    //返回计算得到的答案列表
    public static List<NumberType> readExercises(String exercisesFilePath) {
        List<NumberType> ansList = new ArrayList<>();
        String regex = "[ ()（）\\[\\]【】{}]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        File f = new File(exercisesFilePath);
        //判断文件是否存在
        if(!f.exists()) {
            System.out.println("文件 " + exercisesFilePath + " 不存在");
            return null;
        }
        BufferedReader bread = null;
        String str = null;
        String[] strSplit;
        int bracketsPos;
        try {
            bread = new BufferedReader(new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8));
            //逐行读取文件
            while(null != (str = bread.readLine())) {
                strSplit = str.split("\\s+");
                if(strSplit.length == 5) { //两个操作数，不含括号
                    ansList.add(Calculator.calculate(getNumber(strSplit[1]), getNumber(strSplit[3]), null,
                            getSymbol(strSplit[2]), null, 0));
                } else if(strSplit.length == 7 && !str.contains("(")) { //三个操作数，不含括号
                    ansList.add(Calculator.calculate(getNumber(strSplit[1]), getNumber(strSplit[3]), getNumber(strSplit[5]),
                            getSymbol(strSplit[2]), getSymbol(strSplit[4]), 0));
                } else if(strSplit.length == 7 && str.contains("(")) { //三个操作数，含括号
                    bracketsPos = strSplit[1].contains("(") ? 1 : 2;
                    for(int i = 0; i < strSplit.length; i++) {
                        matcher = pattern.matcher(strSplit[i]);
                        strSplit[i] = matcher.replaceAll("");
                    }
                    ansList.add(Calculator.calculate(getNumber(strSplit[1]), getNumber(strSplit[3]), getNumber(strSplit[5]),
                            getSymbol(strSplit[2]), getSymbol(strSplit[4]), bracketsPos));
                } else { //括号冗余、操作数大于 3 、表达式不完整、格式有误或运算符数量不符
                    ansList.add(null);
                }
            }
        } catch (IOException e) {
            System.out.println("读取文件 " + exercisesFilePath + " 时出现异常");
        } finally {
            if(null != bread) {
                try {
                    bread.close();
                } catch (IOException e) {
                    System.out.println("读文件缓冲流关闭异常");
                }
            }
        }
        return ansList;
    }
    public static NumberType getNumber(String numStr) {
        if(numStr == null || numStr.trim() == "") {
            return null;
        }
        String regex = "[ ()（）\\[\\]【】{}'`/_——。；、:;]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numStr);
        String[] numStrSplit = matcher.replaceAll(" ").split("\\s+");
        if(numStr.contains("'") || numStr.contains("`")) {
            return new FractionalNumber(Integer.parseInt(numStrSplit[1]), Integer.parseInt(numStrSplit[2]), Integer.parseInt(numStrSplit[0]));
        } else if(numStr.contains("/")) {
            return new FractionalNumber(Integer.parseInt(numStrSplit[0]), Integer.parseInt(numStrSplit[1]), 0);
        } else {
            try {
                return new IntegralNumber(Integer.parseInt(numStrSplit[0]));
            } catch(NumberFormatException e) {
                return null;
            }
        }
    }
    private static Generator.SymbolType getSymbol(String symbol) {
        if(symbol.contains("+")) {
            return Generator.SymbolType.ADD;
        } else if(symbol.contains("-")) {
            return Generator.SymbolType.SUB;
        } else if(symbol.contains("×") || symbol.contains("*")) {
            return Generator.SymbolType.MUL;
        } else if(symbol.contains("÷") || symbol.contains("/")) {
            return Generator.SymbolType.DIV;
        }
        return null;
    }

}
