package edu.calculator.utils;

import edu.calculator.entity.NumberType;
import edu.calculator.entity.TypeSelector;
import edu.calculator.entity.impl.FractionalNumber;
import edu.calculator.entity.impl.IntegralNumber;

import java.util.*;

public class Generator {

    private List<String> exercisesList = new ArrayList<>();
    private List<String> answersList = new ArrayList<>();

    private Map<NumberType, NumberType> addMap = new HashMap<>();
    private Map<NumberType, NumberType> subMap = new HashMap<>();
    private Map<NumberType, NumberType> mulMap = new HashMap<>();
    private Map<NumberType, NumberType> divMap = new HashMap<>();

    /**
     * @param type （自然数、真分数和真分数分母）范围
     * @param sum 题目个数
     * @return 题目列表
     */
    public List<String> exercisesGenerator(TypeSelector type, int sum) {
        Random rand = new Random(System.currentTimeMillis());
        int numCount;
        SymbolType symbolType;
        NumberType op1, op2;
        NumberType curAns;
        String str = "";
        while(0 != sum--) { //一次循环生成一道题
            //生成一道题的操作数个数 2或3
            numCount = rand.nextInt(2) + 2;
            //生成两个操作数
            op1 = numGenerator(type, rand);
            op2 = numGenerator(type, rand);
            //生成第一个符号
            symbolType = SymbolType.values()[rand.nextInt(4)];
            //判断二元运算是否重复，并维护哈希表
            while(isDuplicate(op1, op2, symbolType)) {
                op2 = numGenerator(type, rand);
            }
            //判断运算是否符合规则
            while(!isCompliant(op1, op2, symbolType)) {
                op2 = numGenerator(type, rand);
            }
            //计算二元运算的结果
            curAns = calculatorChooser(op1, op2, symbolType);
            if(0 == (numCount -= 2)) {
                //把当前题目存入题目列表
                exercisesList.add(op1.toString() + symbolType.toString() + op2.toString());
                //保存计算结果到答案列表
                answersList.add(curAns.toString());
                continue;//如果当前题目只有两个操作数，则跳转生成下一题，否则继续生成下一个运算符和操作数
            }
            str += op1.toString() + symbolType.toString() + op2.toString();
            //生成三元运算的第三个操作数
            op2 = numGenerator(type, rand);
            //生成第二个符号
            symbolType = SymbolType.values()[rand.nextInt(3)];
            //判断二元运算是否重复
            while(isDuplicate(curAns, op2, symbolType)) {
                op2 = numGenerator(type, rand);
            }
            //判断运算是否符合规则
            while(!isCompliant(curAns, op2, symbolType)) {
                op2 = numGenerator(type, rand);
            }
            //计算二元运算的结果
            curAns = calculatorChooser(curAns, op2, symbolType);
            //把当前题目存入题目列表
            exercisesList.add(str + symbolType.toString() + op2.toString());
            //保存计算结果到答案列表
            answersList.add(curAns.toString());
            str = "";
        }
        return this.exercisesList;
    }
    private boolean isDuplicate(NumberType op1, NumberType op2, SymbolType symbolType) {
        switch (symbolType) {
            case ADD:
                if(addMap.get(op1) == null && addMap.get(op2) == null) {
                    addMap.put(op1, op2);
                    addMap.put(op2, op1);
                    return false;
                }
                return true;
            case SUB:
                if(subMap.get(op1) == null) {
                    subMap.put(op1, op2);
                    return false;
                }
                return true;
            case MUL:
                if(mulMap.get(op1) == null && mulMap.get(op2) == null) {
                    mulMap.put(op1, op2);
                    mulMap.put(op2, op1);
                    return false;
                }
                return true;
            default:
                if(divMap.get(op1) == null) {
                    divMap.put(op1, op2);
                    return false;
                }
                return true;
        }
    }
    /**
     * 生成操作数
     * @param type 操作数类型、范围
     * @return 生成的操作数
     */
    private NumberType numGenerator(TypeSelector type, Random random) {
//        if(type.getType() == 0) {//0：生成整数   1：生成分数
        if(random.nextInt(1) == 0) {//0：生成整数   1：生成分数
            return type.getType() == -1 ?
                    new IntegralNumber(random.nextInt()) :
                    new IntegralNumber(random.nextInt(type.getRange()));
        }
        int denominator = 0;
        while(denominator == 0 || denominator == 1) { //分母不等于0或1
            denominator = type.getType() == -1 ?
                    random.nextInt() :
                    random.nextInt(type.getRange());
        }
        int numerator = 0;
        while(numerator == 0) { //分子不等于0（0不是真分数）
            numerator = random.nextInt(denominator - 1);
        }
        return new FractionalNumber(numerator, denominator);
    }
    /**
     * 判断运算是否符合规定（被减数大于等于减数、被除数小于除数）（加法、乘法是否溢出）
     * @param op1 左操作数
     * @param op2 右操作数
     * @param symbolType 符号类型
     * @return 是否符合规定
     */
    private boolean isCompliant(NumberType op1, NumberType op2, SymbolType symbolType) {
        switch (symbolType) {
            case ADD:
//                if(op1.isInteger() && op2.isInteger()) {
//                } else if(op1.isInteger() && !op2.isInteger()) {
//                } else if(!op1.isInteger() && op2.isInteger()) {
//                } else {
//                }
                try {
                    Math.addExact(op1.getNum(), op2.getNum());
                    Math.addExact(op1.getNum(), op2.getInteger());
                    Math.addExact(op1.getInteger(), op2.getNum());
                    Math.addExact(op1.getNumerator(), op2.getNumerator());
                    Math.addExact(op1.getDenominator(), op2.getDenominator());
                } catch(ArithmeticException e) {
                    return false;
                }
                return true;
            case SUB:
                if(op1.isLess(op2)) {
                    return false;
                }
                return true;
            case MUL:
                try {
                    Math.multiplyExact(op1.getNum(), op2.getNum());
                    Math.multiplyExact(op1.getNum(), op2.getInteger());
                    Math.multiplyExact(op1.getInteger(), op2.getNum());
                    Math.multiplyExact(op1.getNumerator(), op2.getNumerator());
                    Math.multiplyExact(op1.getDenominator(), op2.getDenominator());
                } catch(ArithmeticException e) {
                    return false;
                }
                return true;
            default:
                if(op1.isLess(op2)) {
                    return true;
                }
                return false;
        }
    }
    private NumberType calculatorChooser(NumberType op1, NumberType op2, SymbolType symbolType) {
        switch (symbolType) {
            case ADD:
                return Calculator.addCalculate(op1, op2);
            case SUB:
                return Calculator.subCalculate(op1, op2);
            case MUL:
                return Calculator.mulCalculate(op1, op2);
            default:
                return Calculator.divCalculate(op1, op2);
        }
    }
    public List<String> getAnswersList() {
        return answersList;
    }
    private enum SymbolType {
        ADD, SUB, MUL, DIV;
        @Override
        public String toString() {
            switch (this) {
                case ADD:
                    return " + ";
                case SUB:
                    return " - ";
                case MUL:
                    return " * ";
                default:
                    return " / ";
            }
        }
    }
}
