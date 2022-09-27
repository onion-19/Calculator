package edu.calculator.test;

import edu.calculator.entity.NumberType;
import edu.calculator.entity.TypeSelector;
import edu.calculator.entity.impl.FractionalNumber;
import edu.calculator.entity.impl.IntegralNumber;
import edu.calculator.utils.Calculator;
import edu.calculator.utils.FileOperator;
import edu.calculator.utils.Generator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import java.util.List;
import java.util.Random;

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
        List<String> list = generator.exercisesGenerator(new TypeSelector(4, 100, 50, 100), 100);
        List<String> ansList = generator.getAnswersList();
        FileOperator.writeFile(list, ansList);
    }
    @Test
    public void testSub() {
        FractionalNumber f1 = new FractionalNumber(1, 2, 25);
        FractionalNumber f2 = new FractionalNumber(7, 36, 19);
        NumberType result = Calculator.subCalculate(f1, f2);
        System.out.println(f1 + " - " + f2 + " = " + result);
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
//        else if(tmp.getInteger() < maxFraction.getInteger()){
//            System.out.println("2 " + (tmp.getInteger() < maxFraction.getInteger()));
//        } else {
//            System.out.println("3 " + (((double)tmp.getNumerator() / (double)tmp.getDenominator()) <
//                    ((double)maxFraction.getNumerator() / (double)maxFraction.getDenominator())));
//        }

    }

}
