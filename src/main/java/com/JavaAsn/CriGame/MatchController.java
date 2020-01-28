package com.JavaAsn.CriGame;

public class MatchController {
    public static char randFunc(double rating)
    {
        int min=0,max=8,ran;
        //int ran=(int)(Math.random()*8);<--old way of getting runs scored off a delivery
        /*
        1. use f(s,g)=||s+R^n*(g-s)||, where s,g,R, and n denote smallest, greatest, random no., power respectively
         & ||num|| denotes floor of a number.
        2. n>1: makes lower numbers to frequently occur than higher numbers
           0<n<1: makes higher numbers to occur more frequently than lower numbers
           n=1: original case
        3. this distribution observes the cricket law that: Pr(i-1)>Pr(i) {i=1 to 6} & Pr(i)> Pr(W=7)-->requirement
        */
        double x,p,bias;
        p=1;//this is equivalent to original case

        if(rating>=0.0 && rating<2.5) //bad player
        {
            p=4;
        }
        if(rating>=2.5 && rating<5.0) //average player
        {
            p=2;
        }
        if(rating>=5.0 && rating<7.5) //good player
        {
            p=0.5;
        }
        if(rating>=7.5 && rating<10.0) //excellent player
        {
            p=0.25;
        }
        x=Math.random();
        bias=Math.pow(x,p);//used to implement the effect of player rating on ability to score a number on a ball
        ran= (int) Math.floor(min+(max-min)*bias);

        char ch;
        if(ran==7) ch='W';
        else ch=(char)(ran+48);
        return ch;
    }
}
