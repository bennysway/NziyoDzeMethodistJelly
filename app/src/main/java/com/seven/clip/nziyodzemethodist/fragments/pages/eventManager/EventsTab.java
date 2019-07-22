package com.seven.clip.nziyodzemethodist.fragments.pages.eventManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;
import android.widget.Toast;

import com.seven.clip.nziyodzemethodist.R;
import com.seven.clip.nziyodzemethodist.Settings;
import com.seven.clip.nziyodzemethodist.interfaces.BundleListener;
import com.seven.clip.nziyodzemethodist.interfaces.FabMenuListener;
import com.seven.clip.nziyodzemethodist.models.FabPackage;
import com.seven.clip.nziyodzemethodist.models.NDMFragment;
import com.seven.clip.nziyodzemethodist.util.ColorThemes;
import com.seven.clip.nziyodzemethodist.util.Theme;

import static android.graphics.Color.parseColor;
import static com.seven.clip.nziyodzemethodist.NziyoDzeMethodist.currentTheme;

public class EventsTab extends NDMFragment implements BundleListener {

    Context context;
    private FabMenuListener fabMenu;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.fabMenu = (FabMenuListener) context;
    }
    @Override
    public Context getContext() {
        return super.getContext();
    }
    public EventsTab(){}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public static EventsTab newInstance() { return new EventsTab(); }

    @Override
    public void sendLiveObject(String key, Object object) {

    }

    @Override
    public FabPackage getMenu() {

        FabPackage fabPackage = new FabPackage();
        int color = parseColor(currentTheme.getIconBackgroundColor());
        fabPackage.iconResources.add(R.drawable.ic_help);
        fabPackage.iconResources.add(R.drawable.ic_add);
        fabPackage.iconResources.add(R.drawable.ic_search);
        fabPackage.iconResources.add(R.drawable.ic_map);
        for(int i=0; i<3; i++){
            fabPackage.colorResources.add(color);
            color = currentTheme.isInDayMode() ? ColorThemes.getHigherHue(color) : ColorThemes.getDarkerColor(color);
        }
        Runnable run0 = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,"Opening Help",Toast.LENGTH_LONG).show();
            }
        };
        Runnable run1 = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context,Settings.class));
            }
        };
        Runnable run2 = new Runnable() {
            @Override
            public void run() {
                ColorThemes.setMode(context,currentTheme);
                Toast.makeText(context,"Change mode",Toast.LENGTH_LONG).show();

            }
        };
        fabPackage.runnables.append(0,run0);
        fabPackage.runnables.append(1,run1);
        fabPackage.runnables.append(2,run2);
        return fabPackage;
    }

    @Override
    public void transform(Theme previousTheme, Theme newTheme) {

    }

    @Override
    public void initViewIds() {

    }

    @Override
    public void initViewFunctions() {

    }

    @Override
    public void initOnClicks() {

    }

    @Override
    public void applyTheme() {

    }

    @Override
    public View getAdjustableView() {
        return null;
    }
}
