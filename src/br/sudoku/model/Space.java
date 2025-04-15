package br.sudoku.model;

public class Space {
    private Integer actualValue;
    private final int expectedValue;
    private final boolean fixed;

    public Space( int expectedValue, boolean fixed) {
        this.expectedValue = expectedValue;
        this.fixed = fixed;
        if (fixed){
            actualValue = expectedValue;
        }
    }

    public Integer getActualValue() {
        return actualValue;
    }


    public void setActualValue(Integer actualValue) {
        if (fixed) return;
        this.actualValue = actualValue;
    }
    public void clearActualValue(){
        setActualValue(null);
    }

    public int getExpectedValue() {
        return expectedValue;
    }

    public boolean isFixed() {
        return fixed;
    }
}
