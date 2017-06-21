package com.seven.clip.nziyodzemethodist;

/**
 * Created by bennysway on 07.06.17.
 */


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by mxn on 2016/12/13.
 * MenuListFragment
 */

public class MenuListFragment extends Fragment {

    private ImageView appPic;
    private TextView appOwner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,
                false);

        NavigationView vNavigation = (NavigationView) view.findViewById(R.id.vNavigation);
        View headerLayout = vNavigation.getHeaderView(0);
        appPic = (ImageView) headerLayout.findViewById(R.id.imageOwner);
        appOwner = (TextView) headerLayout.findViewById(R.id.navHeaderSubTitle);
        vNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Toast.makeText(getActivity(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                return false;
            }
        }) ;
        setupHeader();
        return  view ;
    }

    private void setupHeader() {


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String name = preferences.getString("example_text","Set name");
        if(name.equals(""))
            name = "Set name";
        appOwner.setText(name);

        Data image = new Data(this.getContext(),"image");
        String imagePath =  image.get();
        if(imagePath.equals("")){
            appPic.setImageDrawable(getResources().getDrawable(R.drawable.nouser));

        }
        else{
            appPic.setImageBitmap(BitmapUtils.decodeSampledBitmapFromFile(
                    image.get(),
                    300,
                    170,
                    2
            ));
        }


        appPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChangePictureDialog().show(getFragmentManager(),"ChangePicture");
            }
        });

        appOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuListFragment.this.getContext(),ChangeNameDialog.class);
                startActivity(intent);
            }
        });

        /*Data image = new Data(this.getContext(),"image");
        appPic.setImageBitmap(BitmapUtils.decodeSampledBitmapFromFile(
                image.get(),
                appPic.getMeasuredWidth(),
                appPic.getMeasuredHeight(),
                2
        ));*/
    }
    @Override
    public void onResume(){
        super.onResume();
        setupHeader();
    }
    public interface IOnFocusListenable {
        public void onWindowFocusChanged(boolean hasFocus);
    }

}