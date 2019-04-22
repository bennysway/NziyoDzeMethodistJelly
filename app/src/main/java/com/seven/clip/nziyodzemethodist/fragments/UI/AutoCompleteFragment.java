package com.seven.clip.nziyodzemethodist.fragments.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.interfaces.TitleBar;
import com.seven.clip.nziyodzemethodist.util.ColorThemes;
import com.seven.clip.nziyodzemethodist.util.Theme;
import com.seven.clip.nziyodzemethodist.util.Util;

import static android.graphics.Color.parseColor;
import static com.seven.clip.nziyodzemethodist.NziyoDzeMethodist.currentTheme;

public class AutoCompleteFragment extends Fragment {
    View rootView;
    AutoCompleteTextView autoCompleteTextView;
    Context onCompleteActivity;
    InputMethodManager imm;
    Bundle args;


    public AutoCompleteFragment() {
        // Required empty public constructor
    }

    public static AutoCompleteFragment newInstance() {
       return new AutoCompleteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_auto_complete, container, false);
        initViewIds();
        initViewFuntions();
        applyTheme();
        return rootView;
    }

    private void initViewFuntions() {
        autoCompleteTextView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    startSearching(autoCompleteTextView.getText().toString());
                    return true;
                }
                else if((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_BACK)){
                    TitleBar titleBar = (TitleBar) getContext();
                    if(titleBar!= null)
                        titleBar.colapseTitleBar();
                    return true;
                }
                return false;
            }
        });
    }

    private void applyTheme() {
        ColorThemes.addDrawableFilter(autoCompleteTextView.getBackground(),currentTheme.getIconBackgroundColor());
        autoCompleteTextView.setTextColor(parseColor(currentTheme.getIconColor()));
    }

    private void initViewIds() {
        autoCompleteTextView = rootView.findViewById(R.id.autoCompleteTextView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setOnCompleteActivity(Context activity){
        onCompleteActivity = activity;
    }

    public void setDatabase(String []strings){
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,strings);
        autoCompleteTextView.setAdapter(arrayAdapter);
    }

    public void startSearching(String s){
        Intent toSearch = new Intent(getContext(),onCompleteActivity.getClass());
        if(s.equals("")){
            Util.quickToast(getContext(),"Nothing to search...");
        }
        else {
            toSearch.putExtra("search",s);
            startActivity(toSearch);
        }

    }

}
