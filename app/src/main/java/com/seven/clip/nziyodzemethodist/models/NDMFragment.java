package com.seven.clip.nziyodzemethodist.models;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.seven.clip.nziyodzemethodist.interfaces.ColorChangeListener;
import com.seven.clip.nziyodzemethodist.interfaces.FabMenuListener;

public abstract class NDMFragment extends Fragment implements FabMenuListener, ColorChangeListener {

    public View rootView;
    public String fragmentName;

    public abstract void initViewIds();
    public abstract void initViewFunctions();
    public abstract void initOnClicks();
    public abstract void applyTheme();

}
