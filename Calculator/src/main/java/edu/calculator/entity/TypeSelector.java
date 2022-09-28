package edu.calculator.entity;

import edu.calculator.entity.impl.FractionalNumber;
import edu.calculator.entity.impl.IntegralNumber;

public class TypeSelector {

    private int integerRange; //-r 10   ->不包括10
    private int fractionRange;
    private int denominatorRange;

    private final static int NONE_RANGE = 0;
    private final static int INTEGER_RANGE = 1;
    private final static int FRACTION_RANGE = 2;
    private final static int DENOMINATOR_RANGE = 3;
    private final static int ALL_RANGE = 4;

    private int sum;

    public NumberType getMaxInteger() {
        return maxInteger;
    }

    public NumberType getMaxFraction() {
        return maxFraction;
    }

    public NumberType getMinFraction() {
        return minFraction;
    }

    private NumberType maxInteger;
    private NumberType maxFraction;
    private NumberType minFraction;

    public NumberType getMinInteger() {
        return minInteger;
    }

    private NumberType minInteger;

    public TypeSelector(int type, Integer integerRange, Integer fractionRange, Integer denominatorRange, int sum) {
        switch(type) {
            case NONE_RANGE:
                this.integerRange = Integer.MAX_VALUE;
                this.fractionRange = Integer.MAX_VALUE;
                this.denominatorRange = Integer.MAX_VALUE;
                break;
            case INTEGER_RANGE:
                this.integerRange = integerRange;
                this.fractionRange = Integer.MAX_VALUE;
                this.denominatorRange = Integer.MAX_VALUE;
                break;
            case FRACTION_RANGE:
                this.integerRange = Integer.MAX_VALUE;
                this.fractionRange = fractionRange;
                this.denominatorRange = Integer.MAX_VALUE;
                break;
            case DENOMINATOR_RANGE:
                this.integerRange = Integer.MAX_VALUE;
                this.fractionRange = Integer.MAX_VALUE;
                this.denominatorRange = denominatorRange;
                break;
            case ALL_RANGE:
                this.integerRange = integerRange;
                this.fractionRange = fractionRange;
                this.denominatorRange = denominatorRange;
                break;
            default:
                break;
        }
        this.maxInteger = new IntegralNumber(this.integerRange - 1);
        this.maxFraction = new FractionalNumber(this.denominatorRange - 2, this.denominatorRange - 1, this.fractionRange - 1);
        this.minFraction = new FractionalNumber(1, this.denominatorRange - 1, 0);
        this.minInteger = new IntegralNumber(0);
        this.sum = sum;
    }
    public int getIntegerRange() {
        return this.integerRange;
    }
    public int getFractionRange() {
        return this.fractionRange;
    }
    public int getDenominatorRange() {
        return this.denominatorRange;
    }
    public int getSum() {
        return this.sum;
    }

//    private int type = -1; //命令行输入的范围参数的类型  -1表示用户未指定数值范围的情况 0表示真分数分母的上限 1表示自然数的上限
//    //-r 10   ->不包括10
//    private int range;
//
//    public TypeSelector(int type, int range) {
//        this.type = type;
//        this.range = range;
//    }
//
//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    public int getRange() {
//        return range;
//    }
//
//    public void setRange(int range) {
////        this.range = range - 1; //range等于用户输入的范围减1
//        this.range = range;
//    }
}
