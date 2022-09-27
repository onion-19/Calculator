package edu.calculator.utils;

import edu.calculator.entity.NumberType;
import edu.calculator.entity.TypeSelector;
import edu.calculator.entity.impl.FractionalNumber;
import edu.calculator.entity.impl.IntegralNumber;
import org.apache.log4j.Logger;

import java.util.*;

public class Generator {

    private List<String> exercisesList = new ArrayList<>();
    private List<String> answersList = new ArrayList<>();

    private Map<NumberType, NumberType> addMap = new HashMap<>();
    private Map<NumberType, NumberType> subMap = new HashMap<>();
    private Map<NumberType, NumberType> mulMap = new HashMap<>();
    private Map<NumberType, NumberType> divMap = new HashMap<>();

    private NumberType op1, op2, curAns;
    private SymbolType symbolType;

    private Logger LOGGER;

    public Generator(Logger LOGGER) {
        this.LOGGER = LOGGER;
    }

    /**
     * @param type （自然数或真分数分母）范围
     * @param sum 题目个数
     * @return 题目列表
     */
//    public List<String> exercisesGenerator(TypeSelector type, int sum) {
//        Random rand = new Random(System.currentTimeMillis());
//        int numCount;
//        SymbolType symbolType;
//        NumberType op1, op2;
//        NumberType curAns;
//        String str = "";
//        while(0 != sum--) { //一次循环生成一道题
//            //生成一道题的操作数个数 2或3
//            numCount = rand.nextInt(2) + 2;
//            //生成两个操作数
//            op1 = numGenerator(type, rand);
//            op2 = numGenerator(type, rand);
//            //生成第一个符号
//            symbolType = SymbolType.values()[rand.nextInt(4)];
////            System.out.println(" sym: " + symbolType.toString() + " op1: " + op1.toString() + " op2: " + op2.toString());
//            //判断运算是否符合规则
//            while(!isCompliant(op1, op2, symbolType)) {
//                //如果用户指定了数值范围&&除法&&第一个操作数大于等于指定数值：重新生成第一个操作数
//                if(symbolType.ordinal() == 3) {
//                    op1 = numGenerator(type, rand, new IntegralNumber(0), op2);
//                } else if(symbolType.ordinal() == 1) {
//                    op2 = numGenerator(type, rand, new IntegralNumber(0), op1);
//                } else {
//                    op2 = numGenerator(type, rand);
//                }
//                //判断二元运算是否重复，并维护哈希表
//                while(isDuplicate(op1, op2, symbolType)) {
//                    op2 = numGenerator(type, rand);
//                }
//            }
//            System.out.println(" sym: " + symbolType.toString() + " op1: " + op1.toString() + " op2: " + op2.toString());
//            //计算二元运算的结果
//            curAns = calculatorChooser(op1, op2, symbolType);
//            if(0 == (numCount -= 2)) {
//                //把当前题目存入题目列表
//                exercisesList.add(op1.toString() + symbolType.toString() + op2.toString());
//                //保存计算结果到答案列表
//                answersList.add(curAns.toString());
//                continue;//如果当前题目只有两个操作数，则跳转生成下一题，否则继续生成下一个运算符和操作数
//            }
//            str += op1.toString() + symbolType.toString() + op2.toString();
//            //生成三元运算的第三个操作数
//            op2 = numGenerator(type, rand);
//            //生成第二个符号
//            symbolType = SymbolType.values()[rand.nextInt(4)];
//            System.out.println(" sym2: " + symbolType.toString() + " curAns: " + curAns.toString() + " op3: " + op2.toString());
//
//
//
////            //判断二元运算是否重复
////            while(isDuplicate(curAns, op2, symbolType)) {
////                op2 = numGenerator(type, rand);
////            }
////            //判断运算是否符合规则
////            while(!isCompliant(curAns, op2, symbolType)) {
////                op2 = numGenerator(type, rand);
////                System.out.println("rand op3: " + op2.toString());
////            }
//
//            //判断运算是否符合规则
//            while(!isCompliant(curAns, op2, symbolType)) {
//                if(symbolType.ordinal() == 3) {
//                    if(type.getType() == -1 || type.getType() == 0) {//用户没有指定范围 或 用户指定了真分数上限
//                        op2 = numGenerator(type, rand, curAns, new IntegralNumber(Integer.MAX_VALUE));
//                    } else if(curAns.isLess(new IntegralNumber(type.getRange()))) { //用户指定了自然数上限，并且curAns未超过用户给定的上限
//                        op2 = numGenerator(type, rand, curAns, new IntegralNumber(type.getRange()));
//                    } else {
//                        symbolType = SymbolType.values()[rand.nextInt(3)];
//                    }
//                } else if(symbolType.ordinal() == 1) {
//                    if(curAns.isInteger() || type.getType() == -1) {
//                        op2 = numGenerator(type, rand, new IntegralNumber(0), curAns);
//                    } else if(type.getType() == 0) { //用户指定了真分数上限
//                        if((curAns.getInteger() == 0 && curAns.getDenominator() <= type.getRange()) ||
//                            curAns.getInteger() != 0)
//                            op2 = numGenerator(type, rand, new IntegralNumber(0), curAns);
//                        else { //整数部分为 0 且分母超过了用户给定的范围
//                            symbolType = SymbolType.values()[rand.nextInt(3)];
//                        }
//                    } else { //用户指定了自然数上限
//                        if(curAns.isLess(new IntegralNumber(type.getRange())))
//                            op2 = numGenerator(type, rand, new IntegralNumber(0), curAns);
//                        else
//                            op2 = numGenerator(type, rand, new IntegralNumber(0), new IntegralNumber(type.getRange()));
//                    }
//                } else {
//                    op2 = numGenerator(type, rand);
//                }
//                //判断二元运算是否重复，并维护哈希表
//                while(isDuplicate(curAns, op2, symbolType)) {
//                    op2 = numGenerator(type, rand);
//                }
//                System.out.println("rand op3: " + op2.toString());
//            }
//
//
//            //计算二元运算的结果
//            curAns = calculatorChooser(curAns, op2, symbolType);
//            //把当前题目存入题目列表
//            exercisesList.add(str + symbolType.toString() + op2.toString());
//            //保存计算结果到答案列表
//            answersList.add(curAns.toString());
//            str = "";
//        }
//        return this.exercisesList;
//    }



//    public List<String> exercisesGenerator_2(TypeSelector type, int sum) {
//        int count;
//        Random random = new Random(System.currentTimeMillis());
//        NumberType op1 = null, op2 = null, curAns = null;
//        SymbolType symbolType;
//        while(sum-- != 0) {
//            count = random.nextInt(2) + 2;
//            op1 = numGenerator(type, random, null, null);
//            op2 = numGenerator(type, random, null, null);
//            symbolType = SymbolType.values()[random.nextInt(4)];
//            System.out.println(" sym: " + symbolType.toString() + " op1: " + op1.toString() + " op2: " + op2.toString());
//            if(count == 2) {
//                //生成合适的op1和op2
//                while(!isCompliant(op1, op2, symbolType) || isDuplicate(op1, op2, symbolType)) {
//                    if(symbolType.ordinal() == 3) { // 除法的极端情况：用户指定了真分数分母x和自然数y的范围，op1=y || op2=1/x -> 其中一种解决办法是换成减或加或乘
////                        if(null == (tmp = numGenerator(type, random, null, op2))) {
////                            op2 = numGenerator(type, random, op1, null); //生成比被除数大的除数
////                        } else {
////                            op1 = tmp; //生成比除数小的被除数
////                        }
//                        if(op1.getNum() == type.getIntegerRange() || (op2.getDenominator() == type.getFractionRange() && op2.getNumerator() == 1)) {
//                            symbolType = SymbolType.values()[random.nextInt(3)];
////                        } else if(op1.isInteger() && op1.getNum() < type.getIntegerRange()) { //TODO 其实只要下面两种情况任选一种就可以吧？
////                            op2 = numGenerator(type, random, op1, null); // 重新生成比被除数大的除数
////                        } else if{
////                            op1 = numGenerator(type, random, null, op2); // 重新生成比除数小的被除数
////                        }
//                        } else {
//                            op2 = numGenerator(type, random, op1, null); // 重新生成比被除数大的除数
//                        }
//                    } else if(symbolType.ordinal() == 0) { // 减法的极端情况：用户指定了真分数分母x和自然数y的范围，op1=1/x || op2=y -> 其中一种解决办法是换成加或乘或除
//                        if((op1.getDenominator() == type.getFractionRange() && op1.getNumerator() == 1) || op2.getNum() == type.getIntegerRange()) {
//                            symbolType = SymbolType.values()[random.nextInt(3) + 1];
////                        } else if(!op1.isInteger() && op1.getDenominator() < type.getIntegerRange()) { //TODO 其实只要下面两种情况任选一种就可以吧？
////                            op2 = numGenerator(type, random, op1, null); // 重新生成比被减数小的减数
////                        } else if{
////                            op1 = numGenerator(type, random, null, op2); // 重新生成比减数大的被减数
////                        }
//                        } else {
//                            op2 = numGenerator(type, random, op1, null); // 重新生成比被减数小的减数
//                        }
//                    } else {
//                        op2 = numGenerator(type, random, null, null);
//                    }
//                }
//                curAns = calculatorChooser(op1, op2, symbolType);
//                exercisesList.add(op1.toString() + symbolType.toString() + op2.toString());
//                answersList.add(curAns.toString());
//                continue;
//            }
//            op2 = numGenerator(type, random, null, null);
//            symbolType = SymbolType.values()[random.nextInt(4)];
//            while(!isCompliant(curAns, op2, symbolType) || isDuplicate(curAns, op2, symbolType)) {
////                if(null == (tmp = numGenerator(type, random, null, null))) {
////                    symbolType = symbolType.ordinal() == 3 ? SymbolType.values()[random.nextInt(3)] : SymbolType.values()[random.nextInt(4) + 1];
////                } else {
////                    op2 = tmp;
////                }
//                if(symbolType.ordinal() == 3) {
//                    if(curAns.getNum() == type.getIntegerRange() || curAns.getInteger() >= type.getIntegerRange()) {
//                        symbolType = SymbolType.values()[random.nextInt(3)];
//                    } else {
//                        op2 = numGenerator(type, random, curAns, null);
//                    }
//                } else if(symbolType.ordinal() == 0) {
//                    if(curAns.getDenominator() == type.getFractionRange() && curAns.getNumerator() == 1) {
//                        symbolType = SymbolType.values()[random.nextInt(3) + 1];
//                    } else {
//                        op2 = numGenerator(type, random, null, curAns);
//                    }
//                } else {
//                    op2 = numGenerator(type, random, null, null);
//                }
//            }
//            curAns = calculatorChooser(curAns, op2, symbolType);
//            exercisesList.add(curAns.toString() + symbolType.toString() + op2.toString());
//            answersList.add(curAns.toString());
//        }
//        return this.exercisesList;
//    }
//    //不包括 upperNum
//    private NumberType numGenerator(TypeSelector type, Random random, NumberType lowerNum, NumberType upperNum) {
//        int i = random.nextInt(2); // 0:整数    1:分数
//        FractionalNumber fraction;
//        if(lowerNum == null && upperNum == null) { // 如果null、null，随机生成符合大小范围的整数或分数
//            if(i == 0) {
//                return new IntegralNumber(random.nextInt(type.getIntegerRange()));
//            }
//            i = type.getFractionRange() == 2 ? 2 :
//                    random.nextInt(type.getFractionRange() - 2) + 2; // 分母从2开始
//            return new FractionalNumber(random.nextInt(i - 1) + 1, i); // 分子从1开始
//        } else if(lowerNum != null && upperNum == null) { // 如果lowerNum、null，出现在重新生成除数的情况下，生成大于lowerNum的数
//            if(i == 0 && lowerNum.isInteger()) { //lowerNum是整数，从比lowerNum大1的数开始生成
//                if(type.getIntegerRange() <= lowerNum.getNum()) {
//                    return null;
//                } else {
//                    return new IntegralNumber(random.nextInt(type.getIntegerRange() - lowerNum.getNum() - 1) + lowerNum.getNum() + 1);
//                }
//            } else if(i == 0) { //lowerNum是分数，从比lowerNum的整数部分大1的数开始生成
//                if(type.getIntegerRange() <= lowerNum.getInteger()) {
//                    return null;
//                } else {
//                    return new IntegralNumber(random.nextInt(type.getIntegerRange() - lowerNum.getInteger() - 1) + lowerNum.getInteger() + 1);
//                }
//            } else if(!lowerNum.isInteger() && lowerNum.getInteger() == 0) { // lowerNum是真分数，要生成比lowerNum大的真分数
//                i = Math.min(type.getFractionRange(), lowerNum.getDenominator()) == 2 ? 2 :
//                        random.nextInt(Math.min(type.getFractionRange(), lowerNum.getDenominator()) - 2) + 2; // 分母从2开始
//                fraction = new FractionalNumber(random.nextInt(i - 1) + 1, i); // 分子从1开始
//                while (!lowerNum.isLess(fraction)) {
//                    fraction.setNumerator(2 * fraction.getNumerator() + random.nextInt(i - fraction.getNumerator()));
//                }
//                return fraction;
//            } else if(lowerNum.getInteger() > 0 && lowerNum.getInteger() < type.getIntegerRange()) { //lowerNum是分数且整数部分大于0并小于用户给定的范围
//                return new IntegralNumber(random.nextInt(type.getIntegerRange() - lowerNum.getInteger() - 1) + lowerNum.getInteger() + 1);
//            } else { //lowerNum是整数或lowerNum是分数且整数部分非0，无法生成比整数大的真分数，返回null
//                return null;
//            }
//        } else if(lowerNum == null && upperNum != null) { // 如果null、upperNum，出现在重新生成减数（第二次）或被除数（第一次）的情况下，生成小于upperNum的数
//            if(i == 0 && upperNum.isInteger()) {
//                return new IntegralNumber(random.nextInt(upperNum.getNum()));
//            } else if(i == 0 && upperNum.getInteger() != 0) { // upperNum是分数，要生成比upperNum小的整数
//                return new IntegralNumber(random.nextInt(upperNum.getInteger()));
//            } else if(upperNum.isInteger() || upperNum.getInteger() > 0) { // upperNum是整数或upperNum是分数且整数部分不为0，要生成比整数小的真分数，任意真分数符合要求
//                i = type.getFractionRange() == 2 ? 2:
//                        random.nextInt(type.getFractionRange() - 2) + 2;
//                return new FractionalNumber(random.nextInt(i - 1) + 1, i);
//            } else if(i == 1 && upperNum.getInteger() == 0) { //upperNum是分数且整数部分等于0，要生成比upperNum小的真分数
//                if(type.getFractionRange() < upperNum.getDenominator()) {
//                    fraction = new FractionalNumber(1, type.getFractionRange());
//                    if(fraction.isLess(upperNum))
//                        return fraction;
//                    else
//                        return null;
//                }
//            } else {
//                return null;
//            }
//        }
//        return null; // 如果不能在合法范围内生成操作数，则返回null
//    }

    public List<String> exercisesGenerator(TypeSelector type, int sum) {
        LOGGER.info("type integerRange: " + type.getIntegerRange() + " fractionRange: " + type.getFractionRange() + " denominatorRange: " + type.getDenominatorRange());
        int count;
        Random random = new Random(System.currentTimeMillis());
        String str = "";
        while(sum-- != 0) { //一次循环生成一道题目
            count = random.nextInt(2) + 2; //生成2或3
            op1 = numGenerator(type, random, null, null);
            op2 = numGenerator(type, random, null, null);
            symbolType = SymbolType.values()[random.nextInt(4)];
            LOGGER.info("count: " + count + " " + op1.toString() +  symbolType.toString() + op2.toString());
            while(!isCompliant(op1, op2, symbolType) || isDuplicate(op1, op2, symbolType)) {
                LOGGER.debug("============================pre deal======= " + op1 + symbolType + op2);
                deal(type, random);
            }
            curAns = calculatorChooser(op1, op2, symbolType);
            if(count == 2) {
//                System.out.println(op1.toString() + symbolType.toString() + op2.toString() + " = " + curAns.toString());
                exercisesList.add(op1.toString() + symbolType.toString() + op2.toString());
                answersList.add(curAns.toString());
                continue;
            }
            str += op1.toString() + symbolType.toString() + op2.toString();
            op1 = curAns;
            op2 = numGenerator(type, random, null, null);
            symbolType = SymbolType.values()[random.nextInt(4)];
            LOGGER.error(str + symbolType + op2);
            while(!isCompliant(op1, op2, symbolType) || isDuplicate(op1, op2, symbolType)) {
                LOGGER.debug("第二次二元运算--------pre deal-----------" + op1 + symbolType + op2);
                deal(type, random);
            }
            curAns = calculatorChooser(op1, op2, symbolType);
            System.out.println(str + symbolType.toString() + op2.toString() + " = " + curAns.toString());
            exercisesList.add(str + symbolType.toString() + op2.toString());
            answersList.add(curAns.toString());
            str = "";
        }
        return this.exercisesList;
    }
    //负责生成指定范围的数字，调用numGenerator之前已经在deal方法处理好了极端情况
    private NumberType numGenerator(TypeSelector type, Random random, NumberType lowerNum, NumberType upperNum) {
        LOGGER.info("------------------------------numGenerator------------------------------------");
        if(lowerNum != null) {
            LOGGER.warn("==========lowerNum: " + lowerNum);
        } else if(upperNum != null) {
            LOGGER.warn("==========upperNum: " + upperNum);
        }
        int choose = random.nextInt(2); // 0：整数     1：分数
        int x = type.getIntegerRange();
        int y = type.getFractionRange();
        int z = type.getDenominatorRange();
        IntegralNumber maxInteger = new IntegralNumber(x);
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
                    return new IntegralNumber(random.nextInt(x - lowerNum.getInteger() - 1) + lowerNum.getInteger() + 1);
                }
            }   System.out.println("pre351: maxFraction.getInteger: " + maxFraction.getInteger() + " lowerNum.getInteger(): " + lowerNum.getInteger());
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
                }   System.out.println("351: y: " + y + " lowerNum.getInteger(): " + lowerNum.getInteger());
                return new FractionalNumber(random.nextInt(denominator - 1) + 1, denominator,
                        random.nextInt(y - lowerNum.getInteger() - 1) + lowerNum.getInteger() + 1);
            }
            if(lowerNum.isLess(maxInteger)) { //choose等于1但无法生成合法分数的情况
                if(lowerNum.isInteger()) {
                    return new IntegralNumber(random.nextInt(x - lowerNum.getNum() - 1) + lowerNum.getNum() + 1);
                }
                return new IntegralNumber(random.nextInt(x - lowerNum.getInteger() - 1) + lowerNum.getInteger() + 1);
            }   System.out.println("++++++++++++++++++++++++++++++++++++ lowerNum: " + lowerNum.toString());
            return null;
        } else if(lowerNum == null && upperNum != null) {
            if(choose == 0) {
                if(upperNum.isLess(maxInteger)) {
                    if(upperNum.isInteger()) {
                        return new IntegralNumber(random.nextInt(upperNum.getNum()));
                    }   System.out.println("366: " + " upperNum.getInteger(): " + upperNum.getInteger());
                    return new IntegralNumber(random.nextInt(upperNum.getInteger() + 1));
                }
                return new IntegralNumber(random.nextInt(x));
            }
            if(upperNum.isLess(maxFraction)) {
                if(upperNum.isInteger()) {   System.out.println("373: " + " upperNum.getNum(): " + upperNum.getNum());
                    return new FractionalNumber(random.nextInt(denominator - 1) + 1, denominator,
                            random.nextInt(upperNum.getNum()));
                }   System.out.println("376: " + " upperNum.getInteger(): " + upperNum.getInteger());   if(upperNum.getInteger() == 0)  return new FractionalNumber(random.nextInt(denominator - 1) + 1, denominator, 0);
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
    //负责处理冲突（不符合规则或题目出现重复）
    //除法的极端情况：对于上限(自然数：x,真分数：y,真分数分母：z)，无法比op1更大【当x>y时，op1=x；当x=y时，op1=x或op1=(y-1)'(z-1)/z；当x<y时，op1=(y-1)'(z-1)/z】，或无法比op2更小【op2=1/z】，这时候如果重新生成符合要求的数字则需要两次生成，所以选择重新生成符号加或减或乘【这种好像也不行，因为如果加或乘很有可能会溢出】，所以这时候如果优先减少重新生成数字或符号的次数，就只能把符号换成【减】…………
    //减法的极端情况：和除法极端情况相反
    private void deal(TypeSelector type, Random random) {
        LOGGER.info("------------------------------deal------------------------------------");
        int x = type.getIntegerRange();
        int y = type.getFractionRange();
        int z = type.getDenominatorRange();
        IntegralNumber maxInteger = new IntegralNumber(x);
        FractionalNumber maxFraction = new FractionalNumber(z - 1, z, y - 1);
        if(symbolType.ordinal() == 3) {
//            if((x > y && op1.getNum() >= x) ||
//                    (x == y && (op1.getNum() >= x || (op1.getInteger() == y - 1 && op1.getNumerator() == z - 1 && op1.getDenominator() == z))) ||
//                    (x < y && op1.getInteger() == y - 1 && op1.getNumerator() == z - 1 && op1.getDenominator() == z) ||
//                    (op2.getNumerator() == 1 && op2.getDenominator() == z)) {
            LOGGER.debug(op1 + symbolType.toString() + op2 + "    " + op1.isLess(maxInteger) + "    " + op1.isLess(maxFraction));
            if(!op1.isLess(maxInteger) && !op1.isLess(maxFraction)) {
                symbolType = SymbolType.values()[0];
            } else {
                op2 = numGenerator(type, random, op1, null);
            }
        } else if(symbolType.ordinal() == 0) {
//            if((x > y && op2.getNum() == x) ||
//                    (x == y && (op2.getNum() == x || (op2.getInteger() == y - 1 && op2.getNumerator() == z - 1 && op2.getDenominator() == z))) ||
//                    (x < y && op2.getInteger() == y - 1 && op2.getNumerator() == z - 1 && op2.getDenominator() == z) ||
//                    (op1.getNumerator() == 1 && op1.getDenominator() == z)) {
            if(op1.getNum() == 0 || (op1.getInteger() == 0 && op1.getDenominator() == z && op1.getNumerator() == 1)) {
                symbolType = SymbolType.values()[random.nextInt(3) + 1];
            } else {
                op2 = numGenerator(type, random, null, op1);
            }
        } else if(symbolType.ordinal() == 1) { //加法溢出的情况，op2等于上限减去op1范围内的任意数（保证相加后不超过上限）
            NumberType tmp = x >= y ? Calculator.subCalculate(new IntegralNumber(x), op1) : Calculator.subCalculate(new IntegralNumber(y), op1);
            op2 = numGenerator(type, random, null, tmp);
        } else if(symbolType.ordinal() == 2) { //乘法溢出的情况，op2等于上限除以op1范围内的任意数（保证相乘后不超过上限）
            NumberType tmp = x >= y ? Calculator.divCalculate(new IntegralNumber(x), op1) : Calculator.divCalculate(new IntegralNumber(y), op1);
            op2 = numGenerator(type, random, null, tmp);
        } else { //符号为空
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("--------------------deal--符号为空------------------");
            }
        }
        LOGGER.info("after deal, op1: " + op1 + " op2: " + op2);
    }








    private boolean isDuplicate(NumberType op1, NumberType op2, SymbolType symbolType) {
        LOGGER.info("------------------------------isDuplicate------------------------------------");
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
//    private NumberType numGenerator(TypeSelector type, Random random) {
////        if(type.getType() == 0) {//0：生成整数   1：生成分数
//        if(random.nextInt(2) == 0) {//0：生成整数   1：生成分数
//            return type.getType() == -1 ?
//                    new IntegralNumber(random.nextInt()) :
//                    new IntegralNumber(random.nextInt(type.getRange()));
//        }
//        int denominator = 0;
//        while(denominator == 0 || denominator == 1) { //分母不等于0或1
//            denominator = type.getType() == -1 ?
//                    random.nextInt() :
//                    random.nextInt(type.getRange());
//        }
//        int numerator = 0;
//        while(numerator == 0) { //分子不等于0（0不是真分数）
//            numerator = random.nextInt(denominator);
//        }
//        return new FractionalNumber(numerator, denominator);
//    }


    /**
     * 判断运算是否符合规定（被减数大于等于减数、被除数小于除数）（加法、乘法是否溢出）
     * @param op1 左操作数
     * @param op2 右操作数
     * @param symbolType 符号类型
     * @return 是否符合规定
     */
    private boolean isCompliant(NumberType op1, NumberType op2, SymbolType symbolType) {
        LOGGER.info("------------------------------isCompliant------------------------------------");
        switch (symbolType) {
            case ADD:
//                System.out.println("0000000000000000000000");
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
//                System.out.println("44444444444444444444444");
                if(op1.isLess(op2)) {
//                    System.out.println("111111111111111111111");
                    return false;
                }
                return true;
            case MUL:
//                System.out.println("2222222222222222222222222");
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
//                System.out.println("3333333333333333333333");
                if(op1.isLess(op2)) { //NullPointerException
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
        SUB, ADD, MUL, DIV;
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
