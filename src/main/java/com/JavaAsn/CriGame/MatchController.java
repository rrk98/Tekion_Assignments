package com.JavaAsn.CriGame;

public class MatchController {
    public static char randFunc()
    {
        int ran=(int)(Math.random()*8);
        char ch;
        if(ran==7) ch='W';
        else ch=(char)(ran+48);
        return ch;
    }
}
