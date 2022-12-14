package edu.calculator.entity.impl;

import edu.calculator.entity.NumberType;

public class FractionalNumber implements NumberType {
    private int numerator;//分子
    private int denominator;//分母
    private int integer; //整数部分

    public FractionalNumber(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.integer = 0;
    }
    public FractionalNumber(int numerator, int denominator, int integer) {
        this.numerator = numerator;
        this.denominator = denominator;
        this.integer = integer;
    }

    @Override
    public int getNumerator() {
        return this.numerator;
    }
    @Override
    public int getDenominator() {
        return this.denominator;
    }
    @Override
    public int getInteger() {
        return this.integer;
    }
    @Override
    public int getNum() {
        return -1;
    }
    @Override
    public boolean isInteger() {
        return false;
    }

    @Override
    public boolean isLess(NumberType num) {
        if(num.isInteger()) { //num是整数，比较当前分数的整数部分和num的大小
            return this.integer < num.getNum();
        } else if(this.integer == num.getInteger()){ //num是分数，比较当前分数的整数部分和num的整数部分的大小
            return ((double)this.numerator / (double)this.denominator) <
                    ((double)num.getNumerator() / (double)num.getDenominator());
        } else { //比较两个真分数的大小
            return this.integer < num.getInteger();
        }
    }
    @Override
    public boolean equals(NumberType num) {
        return this.getValue() == num.getValue();
    }
    @Override
    public double getValue() {
        return (double)(integer + (double)numerator / (double)denominator);
    }

    @Override
    public String toString() {
        if(integer != 0) {
            return integer + "'" + numerator + "/" + denominator;
        }
        return numerator + "/" + denominator;
    }
}
