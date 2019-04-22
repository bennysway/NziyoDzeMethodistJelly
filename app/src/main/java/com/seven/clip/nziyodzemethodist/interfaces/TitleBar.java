package com.seven.clip.nziyodzemethodist.interfaces;

import android.widget.ImageView;

import com.seven.clip.nziyodzemethodist.models.FabPackage;

public interface TitleBar {
    Runnable onLeftIconClick();
    void colapseTitleBar();
    void setRightIcon(ImageView icon);
    void setLeftClickMode(LeftClickMode mode);
    FabPackage getCircleMenu();

    enum LeftClickMode{
        BACK,MENU,CUSTOM
    }

}
