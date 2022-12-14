package edu.calculator.test;

import edu.calculator.entity.NumberType;
import edu.calculator.entity.TypeSelector;
import edu.calculator.entity.impl.FractionalNumber;
import edu.calculator.entity.impl.IntegralNumber;
import edu.calculator.utils.Calculator;
import edu.calculator.utils.Comparator;
import edu.calculator.utils.FileOperator;
import edu.calculator.utils.Generator;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestUnit {
    private enum SymbolType {
        ADD, SUB, MUL, DIV;
    }
    @Test
    public void testEnum() {
        SymbolType type = SymbolType.values()[3];
        System.out.println(SymbolType.MUL.ordinal());
        System.out.println(type);
        System.out.println(type.ordinal());
        type = SymbolType.values()[2];
        System.out.println(type);
    }
    @Test
    public void testDouble() {
        System.out.println(((double)3 / (double)10));
        System.out.println(((double)33 / (double)100));
        System.out.println(((double)34 / (double)100));

        System.out.println(((double)10 / (double)3));
        System.out.println(((double)100 / (double)33));
        System.out.println(((double)100 / (double)34));
    }
//    @Test
//    public void testExercisesGenerate() {//75、54
//        Generator generator = new Generator();
//        List<String> list = generator.exercisesGenerator(new TypeSelector(0, 100), 100);
//        List<String> ansList = generator.getAnswersList();
//        list.forEach(System.out::println);
//        System.out.println("\nanswers：");
//        ansList.forEach(System.out::println);
//    }
    @Test
    public void testRandom() {
        Random rand = new Random(System.currentTimeMillis());
        for(int i = 0; i < 50; i++) {
            System.out.println(rand.nextInt(2) + 2);
        }
    }
    @Test
    public void testWriteFile() {
//        PropertyConfigurator.configure("log4j.properties");
        Logger LOGGER = Logger.getLogger(Generator.class);
//        Generator generator = new Generator(LOGGER);
        Generator generator = new Generator();
//        List<String> list = generator.exercisesGenerator(new TypeSelector(4, 100, 50, 22), 10000);
//        List<String> ansList = generator.getAnswersList();
//        FileOperator.writeFile(list, ansList);
    }
    @Test
    public void testSub() {
        FractionalNumber f1 = new FractionalNumber(1, 2, 25);
        FractionalNumber f2 = new FractionalNumber(7, 36, 19);
        NumberType result = Calculator.subCalculate(f1, f2);
        System.out.println(f1 + " - " + f2 + " = " + result);
    }
    @Test
    public void testMul() {
        FractionalNumber f1 = new FractionalNumber(4, 9, 1);
        FractionalNumber f2 = new FractionalNumber(6, 13, 30);
        NumberType result = Calculator.mulCalculate(f1, f2);
        System.out.println(f1 + " × " + f2 + " = " + result);
    }
    @Test
    public void testIsLess() {
        IntegralNumber maxInteger = new IntegralNumber(100);
        FractionalNumber maxFraction = new FractionalNumber(99, 100, 49);
        FractionalNumber tmp = new FractionalNumber(1, 4, 114);
        System.out.println(tmp.isLess(maxFraction));
        if(maxFraction.isInteger()) {
            System.out.println("1 " + (tmp.getInteger() < maxFraction.getNum()));
        } else if(tmp.getInteger() == maxFraction.getInteger()) {
            System.out.println("3 " + (((double)tmp.getNumerator() / (double)tmp.getDenominator()) <
                    ((double)maxFraction.getNumerator() / (double)maxFraction.getDenominator())));
        } else {
            System.out.println("2 " + (tmp.getInteger() < maxFraction.getInteger()));
        }
    }
    private int test;
    private void set(int i) {
        i = 4;
    }
    @Test
    public void testVariable() {
        set(test);
        System.out.println(test);
    }
    @Test
    public void testWriteFile2() {
        Logger LOGGER = Logger.getLogger(Generator.class);
//        Generator generator = new Generator(LOGGER);
        Generator generator = new Generator();
        List<String> list = generator.exercisesGenerator(new TypeSelector(4, 101, 51, 22, 10000));
        List<String> ansList = generator.getAnswersList();
        FileOperator.writeFile(list, ansList);
    }
    @Test
    public void testGetNumber() {
        System.out.println(FileOperator.getNumber("7)）_——  \n ' 34/100"));
    }
    @Test
    public void testRegex() {
//        String numStr = "( 5 ' 34/100";
        String numStr = "6. 11 ÷ (18 + 11'12/14) = ";
        String regex = "[ ()（）\\[\\]【】{}'`/\n\t]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numStr);
        String[] numStrSplit = matcher.replaceAll(" ").split("\\s+");
        System.out.println(numStrSplit.length + "?????????????");
        for(int i = 0; i < numStrSplit.length; i++) {
            System.out.println(numStrSplit[i]);
        }
//        for(int i = 0; i < numStrSplit.length; i++) {
//            if(i == 0) {
//                continue;
//            }
//            System.out.println("----------   " + Integer.parseInt(numStrSplit[i]));
//        }
    }
    @Test
    public void testSplit() {
        String numStr = "6. 11 ÷ (18 + 11'12/14) = ";
        String[] numStrSplit = numStr.split("\\s+");
        System.out.println(numStrSplit.length + "?????????????");
        for(int i = 0; i < numStrSplit.length; i++) {
            System.out.println(numStrSplit[i]);
        }
        for(int i = 0; i < numStrSplit.length; i++) {
            if(i == 1 || i == 3 || i == 5) {
                System.out.println("<<<<<<<<<<<<<<<<<<<<  " + FileOperator.getNumber(numStrSplit[i]));
            }
        }
    }
    @Test
    public void testReadExercises() {
        List<NumberType> correctAns = FileOperator.readExercises("O:\\Exercises.txt");
        List<NumberType> ans = FileOperator.readAnswers("O:\\Answers.txt");
        for (int i = 0; i < correctAns.size(); i++) {
            System.out.println((i + 1) + ". " + correctAns.get(i) + "   ----   " + ans.get(i));
        }
    }
    @Test
    public void testCompare() {
        List<NumberType> correctAns = FileOperator.readExercises("O:\\Exercises.txt");
        List<NumberType> ans = FileOperator.readAnswers("O:\\Answers.txt");
        Comparator.compare(correctAns, ans);
    }
    @Test
    public void testAbs() {
        System.out.println((double)Math.abs(3 - 4));
    }
    private void setArray(int[] array) {
        array[0] = 9;
        array[1] = 8;
        array[2] = 7;
    }
    @Test
    public void testArray() {
        int[] arr = {2, 3, 7};
        setArray(arr);
        for (int i : arr) {
            System.out.println(":: " + i);
        }
    }
    @Test
    public void testCalculateWrong() throws IOException {
        BufferedReader bread = new BufferedReader(new FileReader(new File("O:\\Grade.txt")));
        String wrongLine = "";
        bread.readLine();
        wrongLine = bread.readLine();
        bread.close();
        int leftBracketIndex = wrongLine.indexOf("(");
        wrongLine = wrongLine.substring(leftBracketIndex + 1, wrongLine.length() - 1);
        String[] wrongNum;
        Pattern pattern = Pattern.compile("[, ]");
        Matcher matcher = pattern.matcher(wrongLine);
        wrongNum = matcher.replaceAll(" ").split("\\s+");
        bread = new BufferedReader(new FileReader(new File("O:\\new\\Exercises.txt")));
        BufferedReader b2 = new BufferedReader(new FileReader(new File("O:\\new\\Answers.txt")));
        String e, a;
        int w; //转换从Grade.txt里面读出的错题的题号
        int wrongNumIndex = 0;
        w = Integer.parseInt(wrongNum[wrongNumIndex++]);
        BufferedWriter bwrite = new BufferedWriter(new FileWriter(new File("O:\\new\\WrongExercises.txt")));
        BufferedWriter bw2 = new BufferedWriter(new FileWriter(new File("O:\\new\\WrongAnswers.txt")));
        for(int i = 1; i <= 10000; i++) {
            e = bread.readLine();
            a = b2.readLine();
            if(i == w && i < 9991) {
                bwrite.write(e + "\n");
                bw2.write(a + "\n");
                w = Integer.parseInt(wrongNum[wrongNumIndex++]);
            }
        }
        bwrite.close();
        bw2.close();
    }
    @Test
    public void testCompareWrong() throws IOException {
        List<NumberType> correctAns = FileOperator.readExercises("O:\\Exercises.txt");
//        List<NumberType> correctAns = FileOperator.readExercises("O:\\new\\WrongExercises.txt");
//        List<NumberType> ans = FileOperator.readAnswers("O:\\new\\WrongAnswers.txt");
        List<NumberType> ans = FileOperator.readAnswers("O:\\Answers.txt");
        Comparator.compare(correctAns, ans);
//        BufferedWriter bwriter = new BufferedWriter(new FileWriter(new File("O:\\CalAns.txt")));
//        for(int i = 0; i < correctAns.size(); i++) {
//            bwriter.write((i + 1) + ". " + correctAns.get(i).toString() + "\n");
//        }
//        bwriter.close();
    }
    @Test
    public void testEntry() {
        //测试题目生成功能
        TypeSelector type = new TypeSelector(11, 10000);
        Generator generator = new Generator();
        List<String> exercisesList = generator.exercisesGenerator(type);
        List<String> answersList = generator.getAnswersList();
        FileOperator.writeFile(exercisesList, answersList);
        //测试判断答案对错功能
        List<NumberType> correctAns = FileOperator.readExercises("O:\\Exercises.txt");
        List<NumberType> ans = FileOperator.readAnswers("O:\\Answers.txt");
        if(correctAns == null || ans == null) {
            System.exit(0);
        } else {
            Comparator.compare(correctAns, ans);
        }
    }
    @Test
    public void testCalculate() {
        FractionalNumber f1 = new FractionalNumber(1, 2, 25); //25'1/2
        FractionalNumber f2 = new FractionalNumber(7, 36, 19); //19'7/36
        FractionalNumber f3 = new FractionalNumber(5, 6, 53); //19'7/36
        IntegralNumber i1 = new IntegralNumber(20);
        IntegralNumber i2 = new IntegralNumber(7);
        //测试两个分数之间四则运算
        System.out.println(f1 + " + " + f2 + " = " + Calculator.addCalculate(f1, f2));
        System.out.println(f1 + " - " + f2 + " = " + Calculator.subCalculate(f1, f2));
        System.out.println(f1 + " × " + f2 + " = " + Calculator.mulCalculate(f1, f2));
        System.out.println(f2 + " ÷ " + f1 + " = " + Calculator.divCalculate(f2, f1));
        //测试两个整数之间的四则运算
        System.out.println(i1 + " + " + i2 + " = " + Calculator.addCalculate(i1, i2));
        System.out.println(i1 + " - " + i2 + " = " + Calculator.subCalculate(i1, i2));
        System.out.println(i1 + " × " + i2 + " = " + Calculator.mulCalculate(i1, i2));
        System.out.println(i2 + " ÷ " + i1 + " = " + Calculator.divCalculate(i2, i1));
        //测试三个整数之间的四则运算
        System.out.println(i1 + " + " + i2 + " + " + i2 + " = " + Calculator.calculate(i1, i2, i2, Generator.SymbolType.ADD, Generator.SymbolType.ADD, 0));
        System.out.println(i1 + " + " + i2 + " - " + i2 + " = " + Calculator.calculate(i1, i2, i2, Generator.SymbolType.ADD, Generator.SymbolType.SUB, 0));
        System.out.println(i1 + " + " + i2 + " × " + i2 + " = " + Calculator.calculate(i1, i2, i2, Generator.SymbolType.ADD, Generator.SymbolType.MUL, 0));
        System.out.println(i1 + " + " + i2 + " ÷ " + i2 + " = " + Calculator.calculate(i1, i2, i2, Generator.SymbolType.ADD, Generator.SymbolType.DIV, 0));
        //测试三个整数之间的四则运算（括号在前两个数的位置）
        System.out.println("(" + i1 + " + " + i2 + ")" + " + " + i2 + " = " + Calculator.calculate(i1, i2, i2, Generator.SymbolType.ADD, Generator.SymbolType.ADD, 1));
        System.out.println("(" + i1 + " + " + i2 + ")" + " - " + i2 + " = " + Calculator.calculate(i1, i2, i2, Generator.SymbolType.ADD, Generator.SymbolType.SUB, 1));
        System.out.println("(" + i1 + " + " + i2 + ")" + " × " + i2 + " = " + Calculator.calculate(i1, i2, i2, Generator.SymbolType.ADD, Generator.SymbolType.MUL, 1));
        System.out.println("(" + i1 + " + " + i2 + ")" + " ÷ " + i1 + " = " + Calculator.calculate(i1, i2, i1, Generator.SymbolType.ADD, Generator.SymbolType.DIV, 1));
        //测试三个整数之间的四则运算（括号在后两个数的位置）
        System.out.println(i1 + " × " + "(" + i1 + " - " + i2 + ")" + " = " + Calculator.calculate(i1, i1, i2, Generator.SymbolType.MUL, Generator.SymbolType.SUB, 2));
        System.out.println(i2 + " ÷ " + "(" + i1 + " - " + i2 + ")" + " = " + Calculator.calculate(i2, i1, i2, Generator.SymbolType.DIV, Generator.SymbolType.SUB, 2));
        System.out.println(i1 + " × " + "(" + i2 + " + " + i2 + ")" + " = " + Calculator.calculate(i1, i2, i2, Generator.SymbolType.MUL, Generator.SymbolType.ADD, 2));
        System.out.println(i2 + " ÷ " + "(" + i1 + " + " + i2 + ")" + " = " + Calculator.calculate(i2, i1, i2, Generator.SymbolType.DIV, Generator.SymbolType.ADD, 2));
        //测试三个分数之间的四则运算
        System.out.println(f1 + " + " + f2 + " + " + f2 + " = " + Calculator.calculate(f1, f2, f2, Generator.SymbolType.ADD, Generator.SymbolType.ADD, 0));
        System.out.println(f1 + " + " + f2 + " - " + f2 + " = " + Calculator.calculate(f1, f2, f2, Generator.SymbolType.ADD, Generator.SymbolType.SUB, 0));
        System.out.println(f1 + " + " + f2 + " × " + f2 + " = " + Calculator.calculate(f1, f2, f2, Generator.SymbolType.ADD, Generator.SymbolType.MUL, 0));
        System.out.println(f1 + " + " + f2 + " ÷ " + f1 + " = " + Calculator.calculate(f1, f2, f1, Generator.SymbolType.ADD, Generator.SymbolType.DIV, 0));
        //测试三个分数之间的四则运算（括号在前两个数的位置）
        System.out.println("(" + f1 + " + " + f2 + ")" + " + " + f3 + " = " + Calculator.calculate(f1, f2, f3, Generator.SymbolType.ADD, Generator.SymbolType.ADD, 1));
        System.out.println("(" + f3 + " + " + f2 + ")" + " - " + f1 + " = " + Calculator.calculate(f3, f2, f1, Generator.SymbolType.ADD, Generator.SymbolType.SUB, 1));
        System.out.println("(" + f1 + " + " + f2 + ")" + " × " + f2 + " = " + Calculator.calculate(f1, f2, f2, Generator.SymbolType.ADD, Generator.SymbolType.MUL, 1));
        System.out.println("(" + f1 + " + " + f2 + ")" + " ÷ " + f3 + " = " + Calculator.calculate(f1, f2, f3, Generator.SymbolType.ADD, Generator.SymbolType.DIV, 1));
        //测试三个分数之间的四则运算（括号在后两个数的位置）
        System.out.println(f1 + " × " + "(" + f3 + " - " + f2 + ")" + " = " + Calculator.calculate(f1, f3, f2, Generator.SymbolType.MUL, Generator.SymbolType.SUB, 2));
        System.out.println(f2 + " ÷ " + "(" + f3 + " - " + f1 + ")" + " = " + Calculator.calculate(f2, f3, f1, Generator.SymbolType.DIV, Generator.SymbolType.SUB, 2));
        System.out.println(f1 + " × " + "(" + f2 + " + " + f3 + ")" + " = " + Calculator.calculate(f1, f2, f3, Generator.SymbolType.MUL, Generator.SymbolType.ADD, 2));
        System.out.println(f2 + " ÷ " + "(" + f1 + " + " + f3 + ")" + " = " + Calculator.calculate(f2, f1, f3, Generator.SymbolType.DIV, Generator.SymbolType.ADD, 2));
        //测试整数和分数之间的四则运算
        System.out.println(i1 + " + " + f2 + " + " + f2 + " = " + Calculator.calculate(i1, f2, f2, Generator.SymbolType.ADD, Generator.SymbolType.ADD, 0));
        System.out.println(f1 + " + " + i1 + " - " + f2 + " = " + Calculator.calculate(f1, i1, f2, Generator.SymbolType.ADD, Generator.SymbolType.SUB, 0));
        System.out.println(f1 + " + " + i2 + " × " + f2 + " = " + Calculator.calculate(f1, i2, f2, Generator.SymbolType.ADD, Generator.SymbolType.MUL, 0));
        System.out.println(f1 + " + " + i2 + " ÷ " + f1 + " = " + Calculator.calculate(f1, i2, f1, Generator.SymbolType.ADD, Generator.SymbolType.DIV, 0));


    }




}
