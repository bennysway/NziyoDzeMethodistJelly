package com.seven.clip.nziyodzemethodist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
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
    private UserDataIO userData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,
                false);

        userData = new UserDataIO(getContext());
        NavigationView vNavigation = view.findViewById(R.id.vNavigation);
        View headerLayout = vNavigation.getHeaderView(0);
        appPic = headerLayout.findViewById(R.id.imageOwner);
        appOwner = headerLayout.findViewById(R.id.navHeaderSubTitle);
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
        String name = userData.getUserName();
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
                Dialog changeName = new ChangeNameDialog(MenuListFragment.this.getContext());
                changeName.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        setupHeader();
                    }
                });
                changeName.show();
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

}