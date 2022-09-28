package edu.calculator.utils;

import edu.calculator.entity.NumberType;
import edu.calculator.entity.TypeSelector;
import edu.calculator.entity.impl.FractionalNumber;
import edu.calculator.entity.impl.IntegralNumber;
import org.apache.log4j.Logger;

import java.util.*;

public class Generator {
    private List<String> exercisesList = new ArrayList<>();
//    private HashSet<String> exercisesList = new HashSet<>();
    private List<String> answersList = new ArrayList<>();

    private HashSet<String> isNew = new HashSet<>();

    private Map<NumberType, NumberType> addMap = new HashMap<>();
    private Map<NumberType, NumberType> subMap = new HashMap<>();
    private Map<NumberType, NumberType> mulMap = new HashMap<>();
    private Map<NumberType, NumberType> divMap = new HashMap<>();

    private NumberType op1, op2, op3, ans1, ans2, result;
    private SymbolType symbol1, symbol2;

    private Logger LOGGER;

    public Generator(Logger LOGGER) {
        this.LOGGER = LOGGER;
    }

    public void checkMap(NumberType op1, NumberType op2, SymbolType symbolType) {
        switch (symbolType) {
            case ADD:
                addMap.putIfAbsent(op1, op2);
                addMap.putIfAbsent(op2, op1);
                break;
            case SUB:
                subMap.putIfAbsent(op1, op2);
                break;
            case MUL:
                mulMap.putIfAbsent(op1, op2);
                mulMap.putIfAbsent(op2, op1);
                break;
            default:
                divMap.putIfAbsent(op1, op2);
                break;
        }
    }
    private NumberType numGenerator(TypeSelector type, Random random, NumberType lowerNum, NumberType upperNum) {
//        LOGGER.debug("--------------------------numGenerator-------------------------");
        int choose = random.nextInt(2); // 0：整数     1：分数
        int x = type.getIntegerRange();
        int y = type.getFractionRange();
        int z = type.getDenominatorRange();
        IntegralNumber maxInteger = new IntegralNumber(x - 1);
        FractionalNumber maxFraction = new FractionalNumber(z - 1, z, y - 1);
        int denominator = z == 2 ? 2 : (random.nextInt(z - 2) + 2); //分母从2开始    [2, z)
        if(lowerNum == null && upperNum == null) {
            if(choose == 0) {
                return new IntegralNumber(random.nextInt(x));
            }
            return new FractionalNumber(random.nextInt(denominator - 1) + 1, denominator, random.nextInt(y)); //分子从1开始
        } else if(lowerNum != null && upperNum == null) {
            if(choose == 0) {
                if(lowerNum.isLess(maxInteger)) {
                    if(lowerNum.isInteger()) {
                        return new IntegralNumber(random.nextInt(x - lowerNum.getNum() - 1) + lowerNum.getNum() + 1);
                    }
                    return lowerNum.getInteger() == x - 1 ? null :
                            new IntegralNumber(random.nextInt(x - lowerNum.getInteger() - 1) + lowerNum.getInteger() + 1);
                }
            } //  System.out.println("pre351: maxFraction.getInteger: " + maxFraction.getInteger() + " lowerNum.getInteger(): " + lowerNum.getInteger());
            if(lowerNum.isLess(maxFraction)) {
                if(lowerNum.isInteger()) {
                    return new FractionalNumber(random.nextInt(denominator - 1) + 1, denominator,
                            random.nextInt(y - lowerNum.getNum()) + lowerNum.getNum());
                }
                if(lowerNum.getInteger() == y) {
                    double rate = (double)lowerNum.getNumerator() / (double)lowerNum.getDenominator();
                    while(Math.ceil(rate * (double)denominator) == denominator) { //TODO
                        denominator = random.nextInt(z - denominator - 1) + denominator + 1;
                    }
                    return new FractionalNumber(random.nextInt((int)Math.ceil(rate * (double)denominator)), denominator, y - 1);
                }  // System.out.println("351: y: " + y + " lowerNum.getInteger(): " + lowerNum.getInteger());
                if(lowerNum.getInteger() == y - 1) {
                    return new FractionalNumber(random.nextInt(denominator - 1) + 1, denominator, y - 1);
                }
                return new FractionalNumber(random.nextInt(denominator - 1) + 1, denominator,
                        random.nextInt(y - lowerNum.getInteger() - 1) + lowerNum.getInteger() + 1);
            }
            if(lowerNum.isLess(maxInteger)) { //choose等于1但无法生成合法分数的情况
                if(lowerNum.isInteger()) {
                    return new IntegralNumber(random.nextInt(x - lowerNum.getNum() - 1) + lowerNum.getNum() + 1);
                }
                return new IntegralNumber(random.nextInt(x - lowerNum.getInteger() - 1) + lowerNum.getInteger() + 1);
            } //  System.out.println("++++++++++++++++++++++++++++++++++++ lowerNum: " + lowerNum.toString());
            return null;
        } else if(lowerNum == null && upperNum != null) {
            if(upperNum.getNum() == 0) {
                return new IntegralNumber(0);
            }
            if(choose == 0) {
                if(upperNum.isLess(maxInteger)) {
                    if(upperNum.isInteger()) {
                        return new IntegralNumber(random.nextInt(upperNum.getNum()));
                    } //  System.out.println("366: " + " upperNum.getInteger(): " + upperNum.getInteger());
                    return new IntegralNumber(random.nextInt(upperNum.getInteger() + 1));
                }
                return new IntegralNumber(random.nextInt(x));
            }
            if(upperNum.isLess(maxFraction)) {
                if(upperNum.isInteger()) { //  System.out.println("373: " + " upperNum.getNum(): " + upperNum.getNum());
                    return new FractionalNumber(random.nextInt(denominator - 1) + 1, denominator,
                            random.nextInt(upperNum.getNum()));
                } //  System.out.println("376: " + " upperNum.getInteger(): " + upperNum.getInteger());
                if(upperNum.getInteger() == 0)
                    return new FractionalNumber(random.nextInt(denominator - 1) + 1, denominator, 0);
                return new FractionalNumber(random.nextInt(denominator - 1) + 1, denominator,
                        random.nextInt(upperNum.getInteger()));
            }
            return new FractionalNumber(random.nextInt(denominator - 1) + 1, denominator,
                    random.nextInt(y));
        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("--------------------numGenerator--同时指定了上下限的情况------------------");
                return null;
            }
        }
    }
    public List<String> exercisesGenerator(TypeSelector type) {
        Random random = new Random(System.currentTimeMillis());
        int count, brackets;
        int sum = type.getSum();
        while(sum-- != 0 || exercisesList.size() < type.getSum()) {
            count = random.nextInt(2) + 2;
            brackets = random.nextInt(3);
            op1 = numGenerator(type, random, null, null);
            op2 = numGenerator(type, random, null, null);
            symbol1 = SymbolType.values()[random.nextInt(4)];
            if(count == 2) {
                while(op2 != null && (!isCompliant(op1, op2, symbol1) || isDuplicate(op1, op2, symbol1))) {
                    op2 = deal(type, random, op1, op2, symbol1, 2);
                }
                if(op2 == null) {   sum++;  continue;   }
                ans1 = calculatorChooser(op1, op2, symbol1);
    //                LOGGER.info("============success   count: " + count + "    " + op1 + symbol1 + op2 + " = " + ans1);
                checkMap(op1, op2, symbol1);
                if(isNew.add(op1.toString() + symbol1.toString() + op2.toString())) {
                    exercisesList.add(op1.toString() + symbol1.toString() + op2.toString());
                } else {
                    sum++;
                    continue;
                }
                answersList.add(ans1.toString());
                continue;
            }
            op3 = numGenerator(type, random, null, null);
            symbol2 = SymbolType.values()[random.nextInt(4)];
            if(symbol1.ordinal() == 2 || symbol1.ordinal() == 3) { //第一个运算符是乘或除
                if(brackets == 2 && (symbol2 == SymbolType.SUB || symbol2 == SymbolType.ADD)) {//2*(3+5)
                    if(!rightFirst(type, random)) {
                        sum++;
                        continue;
                    }
                    //TODO 三元查重
                    if(isNew.add(op1 + symbol1.toString() + "(" + op2 + symbol2.toString() + op3 + ")")) {
                        exercisesList.add(op1 + symbol1.toString() + "(" + op2 + symbol2.toString() + op3 + ")");
                    } else {
                        sum++;
                        continue;
                    }
                    answersList.add(ans1.toString());
                } else { //从左往右依次计算
                    if(!leftToRight(type, random)) {
                        sum++;
                        continue;
                    }
                    if(symbol1 == SymbolType.MUL && symbol2 == SymbolType.MUL) { //连乘查重
                        checkMap(ans1, op3, symbol2);
                        checkMap(op1, Calculator.mulCalculate(op2, op3), symbol1);
                    } else {
                        //TODO 三元查重
                    }
                    if(isNew.add(op1 + symbol1.toString() + op2 + symbol2.toString() + op3)) {
                        exercisesList.add(op1 + symbol1.toString() + op2 + symbol2.toString() + op3);
                    } else {
                        sum++;
                        continue;
                    }
//                    exercisesList.add(op1 + symbol1.toString() + op2 + symbol2.toString() + op3);
                    answersList.add(ans2.toString());
                }
            } else { //第一个运算符是加或减
                if(brackets != 1 && (symbol2 == SymbolType.MUL || symbol2 == SymbolType.DIV)) {//2+3*8
                    if(!rightFirst(type, random)) {
                        sum++;
                        continue;
                    }
                    //TODO 三元查重
                    if(isNew.add(op1 + symbol1.toString() + op2 + symbol2.toString() + op3)) {
                        exercisesList.add(op1 + symbol1.toString() + op2 + symbol2.toString() + op3);
                    } else {
                        sum++;
                        continue;
                    }
//                    exercisesList.add(op1 + symbol1.toString() + op2 + symbol2.toString() + op3);
                    answersList.add(ans1.toString());
                } else if(brackets == 1 && (symbol2 == SymbolType.MUL || symbol2 == SymbolType.DIV)) { // (2+3)*8
                    if(!leftToRight(type, random)) {
                        sum++;
                        continue;
                    }
                    //TODO 三元查重
                    if(isNew.add("(" + op1 + symbol1.toString() + op2 + ")" + symbol2.toString() + op3)) {
                        exercisesList.add("(" + op1 + symbol1.toString() + op2 + ")" + symbol2.toString() + op3);
                    } else {
                        sum++;
                        continue;
                    }
//                    exercisesList.add("(" + op1 + symbol1.toString() + op2 + ")" + symbol2.toString() + op3);
                    answersList.add(ans2.toString());
                } else { //第二个运算符是加或减 //从左往右依次计算
                    if(!leftToRight(type, random)) {
                        sum++;
                        continue;
                    }
                    if(symbol1 == SymbolType.ADD && symbol2 == SymbolType.ADD) { //连加查重
                        checkMap(ans1, op3, symbol2);
                        checkMap(op1, Calculator.addCalculate(op2, op3), symbol1);
                    } else {
                        //TODO 三元查重
                    }
                    if(isNew.add(op1 + symbol1.toString() + op2 + symbol2.toString() + op3)) {
                        exercisesList.add(op1 + symbol1.toString() + op2 + symbol2.toString() + op3);
                    } else {
                        sum++;
                        continue;
                    }
//                    exercisesList.add(op1 + symbol1.toString() + op2 + symbol2.toString() + op3);
                    answersList.add(ans2.toString());
                }
            }
        }
        return this.exercisesList;
    }
    private boolean rightFirst(TypeSelector type, Random random) {
        while(op3 != null && (!isCompliant(op2, op3, symbol2) || isDuplicate(op2, op3, symbol2))) {
            op3 = deal(type, random, op2, op3, symbol2, 2);
        }
        if(op3 == null) {
            return false;
        }
        ans2 = calculatorChooser(op2, op3, symbol2);
        while(op1 != null && (!isCompliant(op1, ans2, symbol1) || isDuplicate(op1, ans2, symbol1))) {
            op1 = deal(type, random, op1, ans2, symbol1, 1);
        }
        if(op1 == null) {
            return false;
        }
        ans1 = calculatorChooser(op1, ans2, symbol1);
        return true;
    }
    private boolean leftToRight(TypeSelector type, Random random) {
        while(op2 != null && (!isCompliant(op1, op2, symbol1) || isDuplicate(op1, op2, symbol1))) {
            op2 = deal(type, random, op1, op2, symbol1, 2);
        }
        if(op2 == null) {
            return false;
        }
        ans1 = calculatorChooser(op1, op2, symbol1);
        while(op3 != null && (!isCompliant(ans1, op3, symbol2) || isDuplicate(ans1, op3, symbol2))) {
            op3 = deal(type, random, ans1, op3, symbol2, 2);
        }
        if(op3 == null) {
            return false;
        }
        ans2 = calculatorChooser(ans1, op3, symbol2);
        return true;
    }
    private NumberType deal(TypeSelector type, Random random, NumberType leftOp, NumberType rightOp, SymbolType symbol, int changeableOp) {
        if(symbol == SymbolType.DIV) {
            if(!leftOp.isLess(type.getMaxInteger()) && !leftOp.isLess(type.getMaxFraction())) {  //rightOp可变 无符合要求的数
                return null;
            } else if(changeableOp == 2) { //rightOp可变 存在符合要求的数
                return numGenerator(type, random, leftOp, null);
            } else if(rightOp.equals(type.getMinFraction()) || rightOp.equals(type.getMinInteger())) { //leftOp可变 无符合要求的数
                return null;
            } else { //leftOp可变 存在符合要求的数
                return numGenerator(type, random, null, rightOp);
            }
        } else if(symbol == SymbolType.SUB) {
            if (leftOp.equals(type.getMinFraction())) {
                return null;
            } else if (changeableOp == 2) {
                return numGenerator(type, random, null, leftOp);
            } else {
                return numGenerator(type, random, rightOp, null);
            }
        } else {
            return null; //加或乘溢出
        }
    }
    private boolean isCompliant(NumberType op1, NumberType op2, SymbolType symbolType) {
        switch (symbolType) {
            case ADD:
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
                if(op1.isLess(op2)) { //NullPointerException
                    return true;
                }
                return false;
        }
    }
    private boolean isDuplicate(NumberType op1, NumberType op2, SymbolType symbolType) {
        switch (symbolType) {
            case ADD:
                if(addMap.get(op1) == null && addMap.get(op2) == null) {
                    return false;
                }
                return true;
            case SUB:
                if(subMap.get(op1) == null) {
                    return false;
                }
                return true;
            case MUL:
                if(mulMap.get(op1) == null && mulMap.get(op2) == null) {
                    return false;
                }
                return true;
            default:
                if(divMap.get(op1) == null) {
                    return false;
                }
                return true;
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
        SUB, ADD, MUL, DIV;
        @Override
        public String toString() {
            switch (this) {
                case ADD:
                    return " + ";
                case SUB:
                    return " - ";
                case MUL:
                    return " × ";
                default:
                    return " ÷ ";
            }
        }
    }
}
