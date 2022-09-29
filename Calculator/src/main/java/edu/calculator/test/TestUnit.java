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
        Generator generator = new Generator(LOGGER);
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
        Generator generator = new Generator(LOGGER);
        List<String> list = generator.exercisesGenerator(new TypeSelector(4, 101, 51, 22, 10000));
        List<String> ansList = generator.getAnswersList();
        FileOperator.writeFile(list, ansList);
    }
    @Test
    public void testGetNumber() {
//        System.out.println(FileOperator.getNumber("( 7)）  \n ' 34/100"));
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
//                System.out.println("----------   " + Integer.parseInt(numStrSplit[i]));
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


}
