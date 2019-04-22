package com.seven.clip.nziyodzemethodist.models;

import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.List;

public class FabPackage {
    public List<Integer> iconResources;
    public List<Integer>  colorResources;
    public SparseArray<Runnable> runnables;

    public FabPackage(){
        iconResources = new ArrayList<>();
        colorResources = new ArrayList<>();
        runnables = new SparseArray<>();
    }

}
