package edu.calculator.utils;

import edu.calculator.entity.NumberType;
import edu.calculator.entity.impl.FractionalNumber;
import edu.calculator.entity.impl.IntegralNumber;

import java.io.*;
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
        System.out.println("=====================================");

        BufferedWriter ewriter = null, awriter = null;
        try {
            ewriter = new BufferedWriter(new FileWriter(efile));
            awriter = new BufferedWriter(new FileWriter(afile));

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
    }
    public static void writeGrade(List<Integer> correctList, List<Integer> wrongList) {
        if(correctList == null || wrongList == null) {
            System.err.println("???????????????????????");
            return;
        }
        File f = new File(GRADE_PATH);
        BufferedWriter bwrite = null;
        try {
            bwrite = new BufferedWriter(new FileWriter(f));
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
            bread = new BufferedReader(new FileReader(f));
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
            bread = new BufferedReader(new FileReader(f));
            //逐行读取文件
            while(null != (str = bread.readLine())) {
//                System.out.println("-------test--------------  " + str);
                strSplit = str.split("\\s+");
                if(strSplit.length == 5) { //两个操作数，不含括号
                    NumberType tmp = Calculator.calculate(getNumber(strSplit[1]), getNumber(strSplit[3]), null,
                            getSymbol(strSplit[2]), null, 0);
                    ansList.add(tmp);
//                    ansList.add(Calculator.calculate(getNumber(strSplit[1]), getNumber(strSplit[3]), null,
//                            getSymbol(strSplit[2]), null, 0));
                    System.out.println("2            " + ansList.get(ansList.size() - 1));

//                } else if(strSplit.length == 7 && (-1 != (bracketsPos = str.indexOf("(")))) { //两个操作数，含括号
                } else if(strSplit.length == 7 && !str.contains("(")) { //三个操作数，不含括号
                    ansList.add(Calculator.calculate(getNumber(strSplit[1]), getNumber(strSplit[3]), getNumber(strSplit[5]),
                            getSymbol(strSplit[2]), getSymbol(strSplit[4]), 0));
                    System.out.println("3         " + ansList.get(ansList.size() - 1));

                } else if(strSplit.length == 7 && str.contains("(")) { //三个操作数，含括号
                    bracketsPos = strSplit[1].contains("(") ? 1 : 2;
                    for(int i = 0; i < strSplit.length; i++) {
                        matcher = pattern.matcher(strSplit[i]);
                        strSplit[i] = matcher.replaceAll("");
                    }
                    ansList.add(Calculator.calculate(getNumber(strSplit[1]), getNumber(strSplit[3]), getNumber(strSplit[5]),
                            getSymbol(strSplit[2]), getSymbol(strSplit[4]), bracketsPos));
                    System.out.println("4          " + ansList.get(ansList.size() - 1));

                } else { //括号冗余、操作数或运算符数量不符
                    //TODO
                    System.out.println("********  " + str);
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
//    private static NumberType getNumber(String numStr) {
    public static NumberType getNumber(String numStr) {
        String regex = "[ ()（）\\[\\]【】{}'`/]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numStr);
        String[] numStrSplit = matcher.replaceAll(" ").split("\\s+"); //numStrSplit[0]为空
//        System.out.println("single exercise length: " + numStr + " : " + numStrSplit.length);
        if(numStr.contains("'") || numStr.contains("`")) {
            return new FractionalNumber(Integer.parseInt(numStrSplit[1]), Integer.parseInt(numStrSplit[2]), Integer.parseInt(numStrSplit[0]));
//            return new FractionalNumber(Integer.parseInt(numStrSplit[2]), Integer.parseInt(numStrSplit[3]), Integer.parseInt(numStrSplit[1]));
        } else if(numStr.contains("/")) {
            return new FractionalNumber(Integer.parseInt(numStrSplit[0]), Integer.parseInt(numStrSplit[1]), 0);
//            return new FractionalNumber(Integer.parseInt(numStrSplit[1]), Integer.parseInt(numStrSplit[2]), 0);
        } else {
            return new IntegralNumber(Integer.parseInt(numStrSplit[0]));
//            return new IntegralNumber(Integer.parseInt(numStrSplit[1]));
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
