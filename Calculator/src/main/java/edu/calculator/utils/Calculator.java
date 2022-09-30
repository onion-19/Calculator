package edu.calculator.utils;

import edu.calculator.entity.NumberType;
import edu.calculator.entity.impl.FractionalNumber;
import edu.calculator.entity.impl.IntegralNumber;

public class Calculator {

    public static NumberType addCalculate(NumberType op1, NumberType op2) {
        if(op1.isInteger() && op2.isInteger()) {
            return new IntegralNumber(op1.getNum() + op2.getNum());
        } else if(op1.isInteger() && !op2.isInteger()) {
            return new FractionalNumber(op2.getNumerator(), op2.getDenominator(),
                    op1.getNum() + op2.getInteger());
        } else if(!op1.isInteger() && op2.isInteger()) {
            return new FractionalNumber(op1.getNumerator(), op1.getDenominator(),
                    op1.getInteger() + op2.getNum());
        }
        //两个分数相加（分数部分是真分数）
        //1）求两个分母的最小公倍数，两个分子分别乘以最小公倍数
        int lcm = getLeastCommonMultiple(op1.getDenominator(), op2.getDenominator());
        int preN1 = lcm / op1.getDenominator() * op1.getNumerator();
        int preN2 = lcm / op2.getDenominator() * op2.getNumerator();
        //2）求两个分子（乘以最小公倍数之后）之和与最小公倍数之间的最大公约数
        int gcd = getGreatestCommonDivisor(preN1 + preN2, lcm);
        //3）如果求和后的分子大于最小公倍数，相减得到剩下的分数部分，化简分数部分；否则直接化简
        if((preN1 + preN2) % lcm == 0) { //TODO
            return new IntegralNumber(op1.getInteger() + op2.getInteger() + 1);
        }
        return preN1 + preN2 > lcm ?
                new FractionalNumber((preN1 + preN2 - lcm) / gcd, lcm / gcd,
                op1.getInteger() + op2.getInteger() + 1) :
                new FractionalNumber((preN1 + preN2) / gcd, lcm / gcd,
                op1.getInteger() + op2.getInteger());
    }
    public static NumberType subCalculate(NumberType op1, NumberType op2) {
        if(op1.isInteger() && op2.isInteger()) {
            return new IntegralNumber(op1.getNum() - op2.getNum());
        } else if(op1.isInteger() && !op2.isInteger()) {
            return new FractionalNumber(op2.getDenominator() - op2.getNumerator(),
                    op2.getDenominator(), op1.getNum() - op2.getInteger() - 1);
        } else if(!op1.isInteger() && op2.isInteger()) { //分数减整数
            if(op2.getNum() == 0)
                return op1;
            return new FractionalNumber(op1.getNumerator(), op1.getDenominator(), op1.getInteger() - op2.getNum());
        }
        //两个分数相减
        //1）求两个分母的最小公倍数，两个分子分别乘以最小公倍数
        int lcm = getLeastCommonMultiple(op1.getDenominator(), op2.getDenominator());
        int preN1 = lcm / op1.getDenominator() * op1.getNumerator();
        int preN2 = lcm / op2.getDenominator() * op2.getNumerator();
        //2）求两个分子（乘以最小公倍数之后）之差与最小公倍数之间的最大公约数
        int leftInteger = op1.getInteger();
        if(preN1 - preN2 == 0) {
            return new IntegralNumber(op1.getInteger() - op2.getInteger());
        } else if(preN1 - preN2 < 0) {
            leftInteger -= 1;
            preN1 += lcm;
        }
        int gcd = getGreatestCommonDivisor(preN1 - preN2, lcm);
        //3）如果相减后的分子大于最小公倍数，相减得到剩下的分数部分，化简分数部分；否则直接化简
        return new FractionalNumber((preN1 - preN2) % lcm / gcd, lcm / gcd,
                leftInteger - op2.getInteger());
    }
    public static NumberType mulCalculate(NumberType op1, NumberType op2) {
        if(op1.isInteger() && op2.isInteger()) {
            return new IntegralNumber(op1.getNum() * op2.getNum());
        } else if(op1.isInteger() && !op2.isInteger()) {
            int preN = op2.getNumerator() * op1.getNum();
            if(preN % op2.getDenominator() == 0) {
                return new IntegralNumber(op1.getNum() * op2.getInteger() + preN / op2.getDenominator());
            }
            int tmpInteger = preN / op2.getDenominator();
            if(tmpInteger > 0) {
                preN %= op2.getDenominator();
            }
            int gcd = getGreatestCommonDivisor(preN, op2.getDenominator());
            return new FractionalNumber(preN / gcd, op2.getDenominator() / gcd,
                    op1.getNum() * op2.getInteger() + tmpInteger);//整数部分相乘加上分数部分相乘后得到的整数部分
        } else if(!op1.isInteger() && op2.isInteger()) {
            int preN = op1.getNumerator() * op2.getNum();
            if(preN % op1.getDenominator() == 0) {
                return new IntegralNumber(op2.getNum() * op1.getInteger() + preN / op1.getDenominator());
            }
            int tmpInteger = preN / op1.getDenominator();
            if(tmpInteger > 0) {
                preN %= op1.getDenominator();
            }
            int gcd = getGreatestCommonDivisor(preN, op1.getDenominator());
            return new FractionalNumber(preN / gcd, op1.getDenominator() / gcd,
                    op2.getNum() * op1.getInteger() + tmpInteger);
        }
        //两个分数相乘
        int n = (op1.getNumerator() + op1.getInteger() * op1.getDenominator()) *
                (op2.getNumerator() + op2.getInteger() * op2.getDenominator());
        int d = op1.getDenominator() * op2.getDenominator();
        int gcd = getGreatestCommonDivisor((n % d), d);
        if(n % d == 0) {
            return new IntegralNumber(n / d);
        }
        return new FractionalNumber((n % d) / gcd, d / gcd, n / d);
    }
    public static NumberType divCalculate(NumberType op1, NumberType op2) {
        if(op1.getNum() == 0)
            return op1;
        if(op1.isInteger() && op2.isInteger()) {
            int gcd = getGreatestCommonDivisor(op1.getNum(), op2.getNum());
            return new FractionalNumber(op1.getNum() / gcd, op2.getNum() / gcd);
        } else if(op1.isInteger() && !op2.isInteger()) {
            int d = op2.getDenominator(); //原来的分母
            int n = op2.getNumerator() + op2.getInteger() * d; //原来的分子（加上整数部分）
            if(n == 1) { //如果原来的分子为1，取倒数后变为整数
                return mulCalculate(op1, new IntegralNumber(d));
            }
            return mulCalculate(op1, new FractionalNumber(d % n, n, d / n));
        } else if(!op1.isInteger() && op2.isInteger()) {
            return mulCalculate(op1, new FractionalNumber(1, op2.getNum()));
        }
        //两个分数相除
        int d = op2.getDenominator(); //op2原来的分母
        int n = op2.getNumerator() + op2.getInteger() * d; //原来的分子（加上整数部分）
        if(n == 1) { //如果原来的分子为1，取倒数后变为整数
            return mulCalculate(op1, new IntegralNumber(d));
        }
        return mulCalculate(op1, new FractionalNumber(d % n, n, d / n));
    }
    public static int getLeastCommonMultiple(int d1, int d2) {//分母1、分母2
        return d1 * d2 / getGreatestCommonDivisor(d1, d2);
    }
    public static int getGreatestCommonDivisor(int m, int n) {
        if(m == 0)
            return n;
        int tmp;
        if(m < n) {// 保证 m >= n
            tmp = m;
            m = n;
            n = tmp;
        }
        while(m % n != 0) { //辗转相除，直到除数为 0 时取除数
            tmp = m % n;
            m = n;
            n = tmp;
        }
        return n;
    }
    public static NumberType calculatorChooser(NumberType op1, NumberType op2, Generator.SymbolType symbolType) {
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
    public static NumberType calculate(NumberType op1, NumberType op2, NumberType op3,
                                       Generator.SymbolType symbol1, Generator.SymbolType symbol2, int bracketsPos) {
        if(bracketsPos == 0) {
            if(symbol2 != null && (symbol1 == Generator.SymbolType.ADD || symbol1 == Generator.SymbolType.SUB) &&
                    (symbol2 == Generator.SymbolType.MUL || symbol2 == Generator.SymbolType.DIV)) {
                return calculatorChooser(op1, calculatorChooser(op2, op3, symbol2), symbol1);
            } else if(symbol2 != null){
                return calculatorChooser(calculatorChooser(op1, op2, symbol1), op3, symbol2);
            } else {
                return calculatorChooser(op1, op2, symbol1);
            }
        } else if(bracketsPos == 1) {
            if(op3 == null) {
                return calculatorChooser(op1, op2, symbol1);
            } else {
                return calculatorChooser(calculatorChooser(op1, op2, symbol1), op3, symbol2);
            }
        } else { //先算右边
            return calculatorChooser(op1, calculatorChooser(op2, op3, symbol2), symbol1);
        }

    }

}
