package com.rickh.simplebillsplitter;

import java.math.BigDecimal;

public class Bill {

    private BigDecimal mTotal = new BigDecimal(0);

    public void calculate(int number) {
        String totalString = mTotal.toString();
        String numberString = String.valueOf(number);

        mTotal = new BigDecimal(totalString.concat(numberString));
    }

    public void backspace() {
        String totalString = mTotal.toString();

        if (totalString.length() > 1) {
            mTotal = new BigDecimal(totalString.substring(0, totalString.length() - 1));
        } else {
            mTotal = new BigDecimal(0);
        }
    }

    public BigDecimal getTotal() {
        return mTotal;
    }

    public void setTotal(BigDecimal mTotal) {
        this.mTotal = mTotal;
    }
}
