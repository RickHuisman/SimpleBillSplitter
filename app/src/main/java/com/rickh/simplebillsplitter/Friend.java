package com.rickh.simplebillsplitter;

import java.math.BigDecimal;

public class Friend {

    private String mName;
    private boolean mLocked;
    private BigDecimal mPercentage;
    private static int mFriendCount;
    private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public Friend() {
        mName = "Person " + ALPHABET[mFriendCount];

        mLocked = false;
        mPercentage = new BigDecimal(0);

        mFriendCount++;
    }

    public String getName() {
        return mName;
    }

    public boolean isLocked() {
        return mLocked;
    }

    public void setLocked(boolean locked) {
        this.mLocked = locked;
    }

    public BigDecimal getPercentage() {
        return mPercentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.mPercentage = percentage;
    }
}
