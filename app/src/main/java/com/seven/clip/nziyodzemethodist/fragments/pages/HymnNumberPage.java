package com.seven.clip.nziyodzemethodist.fragments.pages;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.models.FabPackage;
import com.seven.clip.nziyodzemethodist.models.NDMActivity;
import com.seven.clip.nziyodzemethodist.models.NDMFragment;
import com.seven.clip.nziyodzemethodist.util.Theme;
import com.seven.clip.nziyodzemethodist.util.Util;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class HymnNumberPage extends NDMFragment {
    TextView number1;
    TextView number2;
    TextView number3;
    TextView number4;
    TextView number5;
    TextView number6;
    TextView number7;
    TextView number8;
    TextView number9;
    TextView number0;
    TextView inputNumber;
    AppCompatImageView backButton;
    AppCompatImageView acceptButton;
    TextView[] numbers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_hymn_number_page, container, false);
        initViewIds();
        initOnClicks();
        fragmentName = "Hymn Number";
        return rootView;
    }

    @Override
    public void transform(Theme previousTheme, Theme newTheme) {

    }

    @Override
    public FabPackage getMenu() {
        return null;
    }

    @Override
    public void initOnClicks() {
        numbers = new TextView[]{number0,number1,number2,number3,number4,number5,number6,number7,number8,number9};
        for(int i=0;i<numbers.length; i++){
            final int finalI = i;
            numbers[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addNumber(finalI);
                }
            });
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeNumber();
            }
        });
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.quickToast(getContext(),inputNumber.getText().toString());
            }
        });
    }

    @Override
    public void initViewIds() {
        number0 = rootView.findViewById(R.id.number0);
        number1 = rootView.findViewById(R.id.number1);
        number2 = rootView.findViewById(R.id.number2);
        number3 = rootView.findViewById(R.id.number3);
        number4 = rootView.findViewById(R.id.number4);
        number5 = rootView.findViewById(R.id.number5);
        number6 = rootView.findViewById(R.id.number6);
        number7 = rootView.findViewById(R.id.number7);
        number8 = rootView.findViewById(R.id.number8);
        number9 = rootView.findViewById(R.id.number9);
        inputNumber = rootView.findViewById(R.id.inputNumber);
        backButton = rootView.findViewById(R.id.backButton);
        acceptButton = rootView.findViewById(R.id.acceptButton);
    }

    @Override
    public void initViewFunctions() {

    }

    @Override
    public void applyTheme() {

    }

    private void addNumber(int number) {
        String check = inputNumber.getText().toString();
        if(Integer.valueOf(check + number) < 322)
            inputNumber.setText(String.valueOf(check + number));
    }

    private void removeNumber() {
        String check = inputNumber.getText().toString();
        if(!check.equals(""))
            inputNumber.setText(check.substring(0,check.length()-1));
    }
}
