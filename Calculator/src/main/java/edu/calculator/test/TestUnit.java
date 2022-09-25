package edu.calculator.test;

import edu.calculator.entity.TypeSelector;
import edu.calculator.utils.FileOperator;
import edu.calculator.utils.Generator;
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
    @Test
    public void testExercisesGenerate() {//75、54
        Generator generator = new Generator();
        List<String> list = generator.exercisesGenerator(new TypeSelector(0, 100), 100);
        List<String> ansList = generator.getAnswersList();
        list.forEach(System.out::println);
        System.out.println("\nanswers：");
        ansList.forEach(System.out::println);
    }
    @Test
    public void testRandom() {
        Random rand = new Random(System.currentTimeMillis());
        for(int i = 0; i < 50; i++) {
            System.out.println(rand.nextInt(2) + 2);
        }
    }
    @Test
    public void testWriteFile() {
        Generator generator = new Generator();
        List<String> list = generator.exercisesGenerator(new TypeSelector(0, 100), 100);
        List<String> ansList = generator.getAnswersList();
        FileOperator.writeFile(list, ansList);
    }

}
