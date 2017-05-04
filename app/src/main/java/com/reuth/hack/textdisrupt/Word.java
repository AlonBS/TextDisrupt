package com.reuth.hack.textdisrupt;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shacharla on 5/4/2017.
 */

public class Word implements Parcelable {
    int begin, end;
    String value;

    public Word(int begin, int end, String value) {
        this.begin = begin;
        this.end = end;
        this.value = value;
    }

    protected Word(Parcel in) {
        begin = in.readInt();
        end = in.readInt();
        value = in.readString();
    }

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    public int getBegin() {
        return this.begin;
    }

    public int getEnd() {
        return this.end;
    }

    public String getValue() {
        return this.value;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(begin);
        dest.writeInt(end);
        dest.writeString(value);
    }
}

