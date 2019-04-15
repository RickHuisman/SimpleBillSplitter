package com.rickh.simplebillsplitter;

public class Friend {

    private String mName;
    private static int mFriendCount;
    private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public Friend() {
        mName = "Person " + ALPHABET[mFriendCount];

        mFriendCount++;
    }

    public String getName() {
        return mName;
    }
}
