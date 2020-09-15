package com.example.hbd.learnabc;

public class Word {

    private String mAlphabet;
    private String mSymbol;
    private int mImageResource;
    private  int mAudioResource;

    public Word(String alphabet, String symbol, int imageR, int audio)
    {
        mAlphabet= alphabet;
        mSymbol= symbol;
        mImageResource= imageR;
        mAudioResource = audio;
    }
    public String getAphabet()
    {
        return mAlphabet;
    }
    public String getSymbol()
    {
        return mSymbol;
    }
    public int getImageR()
    {
        return mImageResource;
    }
    public int getAudioID()
    {
        return mAudioResource;
    }
}
