package edu.calculator.entity.impl;

import edu.calculator.entity.NumberType;

public class IntegralNumber implements NumberType {
    private int num;
    public IntegralNumber(int num) {
        this.num = num;
    }
    @Override
    public int getNum() {
        return this.num;
    }

    @Override
    public int getNumerator() {
        return -1;
    }
    @Override
    public int getDenominator() {
        return -1;
    }
    @Override
    public int getInteger() {
        return -1;
    }

    @Override
    public boolean isInteger() {
        return true;
    }
    @Override
    public boolean isLess(NumberType num) {
        if(num.getNum() != -1) { //num是整数
            return this.num < num.getNum();
        } else if(this.num < num.getInteger()) { //num是分数且num的整数部分大于当前整数
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "" + num;
    }
}
