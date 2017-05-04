package com.reuth.hack.textdisrupt;

/**
 * Created by shacharla on 5/4/2017.
 */

public class Word {
    int begin, end;
    String value;

    public Word(int begin, int end, String value) {
        this.begin = begin;
        this.end = end;
        this.value = value;
    }

    public int getBegin() {
        return this.begin;
    }

    public int getEnd() {
        return this.end;
    }

    public String getValue() {
        return this.value;
    }



}

