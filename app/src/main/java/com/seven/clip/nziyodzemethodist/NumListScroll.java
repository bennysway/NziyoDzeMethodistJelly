package com.seven.clip.nziyodzemethodist;

/**
 * Created by bennysway on 27.01.17.
 */

public class NumListScroll {
    private int position;
    private String letterIndex;
    private int [] scrollernumNames = {
          0,50,100,150,200,250,300
    };
    private String [] alphaScrollShow= {
            "1-","50-","100-","150-","200-","250-","300-"
    };

    public NumListScroll(){}

    private int range(int a){
        int i;
        for(i=0;i<7;i++){
            if(a<=scrollernumNames[i])
                break;
        }
        return i;
    }

    public void up(int a){
        if(a>1){
            position = scrollernumNames[range(a)-1];
            letterIndex = alphaScrollShow[range(a)-1];
        }

    }

    public void down(int a){
        if(a<299){
            position = scrollernumNames[range(a)];
            letterIndex = alphaScrollShow[range(a)];
        }
    }

    public int pos() {
        return position;
    }

    public String letter() {
        return letterIndex;
    }
}
