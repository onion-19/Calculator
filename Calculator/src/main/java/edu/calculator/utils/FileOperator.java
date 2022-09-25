package edu.calculator.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileOperator {
    private final static String EXERCISES_PATH = "Exercises.txt";
    private final static String ANSWERS_PATH = "Answers.txt";

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
            for(int i = 1; i <= answersList.size(); i++) {
                awriter.write(i + ". " + answersList.get(i - 1) + "\n");
            }

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

}
