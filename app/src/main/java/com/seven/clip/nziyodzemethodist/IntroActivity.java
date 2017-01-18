package com.seven.clip.nziyodzemethodist;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        addSlide(AppIntroFragment.newInstance("A hymn book with a difference", "crafted and carefully coded with passion",R.drawable.startbook, getResources().getColor(R.color.startbookbg)));
        addSlide(AppIntroFragment.newInstance("Set Favourites with ease", "Long press on any hymn, hymn title, or scroll to the end of a hymn", R.drawable.love, getResources().getColor(R.color.lovebg)));
        addSlide(AppIntroFragment.newInstance("Quick search", "any word, any part-word...", R.drawable.look, getResources().getColor(R.color.lookbg)));
        addSlide(AppIntroFragment.newInstance("Make memories, Save Captions", "dedicate notes or recordings to a particular hymn", R.drawable.mic, getResources().getColor(R.color.micbg)));
        addSlide(AppIntroFragment.newInstance("Farai iwe m'Kristu", "Enjoy the HymnBook. ", R.drawable.lightbook, getResources().getColor(R.color.burn)));

        showSkipButton(false);


    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
            finish();
    }

}
