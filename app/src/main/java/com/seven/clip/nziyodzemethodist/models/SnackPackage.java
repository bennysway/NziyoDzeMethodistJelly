package com.seven.clip.nziyodzemethodist.models;

public class SnackPackage {
    public String message;
    public String action;
    private boolean executable;
    public Runnable runnable;

    public SnackPackage(String message,String actionLabel,Runnable runnable){
        executable = true;
        this.message = message;
        this.action = actionLabel;
        this.runnable = runnable;
    }
    public SnackPackage(String message){
        executable = false;
        this.message = message;
    }

    public boolean isExecutable() {
        return executable;
    }
}
