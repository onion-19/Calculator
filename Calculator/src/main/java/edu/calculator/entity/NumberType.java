package edu.calculator.entity;

public interface NumberType {

    public boolean isInteger();
    public boolean isLess(NumberType num);

    public int getNumerator();
    public int getDenominator();
    public int getInteger();

    public int getNum();
}
