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

    public TypeSelector(int range, int sum) {
        this.integerRange = range;
        this.fractionRange = range;
        this.denominatorRange = range;
        this.maxInteger = new IntegralNumber(this.integerRange - 1);
        this.maxFraction = new FractionalNumber(this.denominatorRange - 2, this.denominatorRange - 1, this.fractionRange - 1);
        this.minFraction = new FractionalNumber(1, this.denominatorRange - 1, 0);
        this.minInteger = new IntegralNumber(0);
        this.sum = sum;
    }

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

}
