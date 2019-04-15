package com.rickh.simplebillsplitter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Bill implements Serializable {

    private BigDecimal mTotal = new BigDecimal(0);
    private BigDecimal mBillAmount = new BigDecimal(0);
    private int mFriends = 2;
    private BigDecimal mTip;
    private int mTipPercentage;

    public void add(int number) {
        String billAmountString = mBillAmount.toString();
        String numberString = String.valueOf(number);

        mBillAmount = new BigDecimal(billAmountString.concat(numberString));

        calculateTotal();
    }

    public void addDecimal() {
//        String totalString = mBillAmount.toString();
//
//        System.out.println(totalString.concat(".1"));
//        mBillAmount = new BigDecimal(totalString.concat(".1"));
//
//        calculateTotal();
    }

    public void backspace() {
        String billAmountString = mBillAmount.toString();

        if (billAmountString.length() > 1) {
            mBillAmount = new BigDecimal(billAmountString.substring(0, billAmountString.length() - 1));
        } else {
            mBillAmount = new BigDecimal(0);
        }
        calculateTotal();
    }

    public void calculateTotal() {
        mTotal = mBillAmount.multiply(new BigDecimal("1." + String.valueOf(mTipPercentage)));

        mTotal = mTotal.setScale(2, RoundingMode.HALF_UP);
        mTotal = new BigDecimal(mTotal.stripTrailingZeros().toPlainString());

        mTip = mTotal.subtract(mBillAmount);
    }

    public BigDecimal getTip() {
        return mTip;
    }

    public int getTipPercentage() {
        return mTipPercentage;
    }

    public void setTipPercentage(int percentage) {
        mTipPercentage = percentage;

        calculateTotal();
    }

    public BigDecimal getTotal() {
        return mTotal;
    }

    public BigDecimal getBillAmount() {
        return mBillAmount;
    }

    public int getFriends() {
        return mFriends;
    }

    public void setFriends(int friends) {
        this.mFriends = friends;
    }
}
