package edu.calculator.utils;

import edu.calculator.entity.NumberType;

import java.util.ArrayList;
import java.util.List;

public class Comparator {
    private static List<Integer> correctList = new ArrayList<>();
    private static List<Integer> wrongList = new ArrayList<>();


    public static void compare(List<NumberType> correctAns, List<NumberType> ans) {
        for(int i = 0; i < correctAns.size(); i++) {
            if(correctAns.get(i) == null || ans.get(i) == null) {
                wrongList.add(i + 1);
                continue;
            }
            if(Math.abs(correctAns.get(i).getValue() - ans.get(i).getValue()) <= 0.001) {
                correctList.add(i + 1);
            } else {
                wrongList.add(i + 1);
            }
        }
        FileOperator.writeGrade(correctList, wrongList);
    }



}
