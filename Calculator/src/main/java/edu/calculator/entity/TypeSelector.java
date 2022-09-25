package edu.calculator.entity;

public class TypeSelector {
    private int type = -1; //命令行输入的范围参数的类型  -1表示用户未指定数值范围的情况
    //-r 10   ->不包括10
    private int range;

    public TypeSelector(int type, int range) {
        this.type = type;
        this.range = range;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range - 1; //range等于用户输入的范围减1
    }
}
