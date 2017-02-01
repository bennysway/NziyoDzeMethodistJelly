package com.seven.clip.nziyodzemethodist;

/**
 * Created by bennysway on 27.01.17.
 */

public class HymnListScroll {
    private int position;
    private String letterIndex;
    private int [] scrollernumNames = {
          0,3,13,18,23,28,30,38,66,88,109,178,226,227,234,242,250,286,293,305,309
    };
    private String [] alphaScrollShow= {
            "A","B","C","D","F","G","H","I","J","K","M","N","O","P","R","S","T","U","V","W","Z"
    };
    
    public HymnListScroll(){}

    private int range(int a){
        int i;
        for(i=0;i<21;i++){
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
        if(a<309){
            position = scrollernumNames[range(a)+1];
            letterIndex = alphaScrollShow[range(a)+1];
        }
    }

    public int pos() {
        return position;
    }

    public String letter() {
        return letterIndex;
    }
}