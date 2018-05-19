package com.seven.clip.nziyodzemethodist;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BiblePicker extends AppCompatActivity {

    ListView appList;
    ArrayList<String> appNames, appPackages;
    UserDataIO userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bible_picker);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        appList = findViewById(R.id.appList);
        appNames = new ArrayList<>();
        appPackages = new ArrayList<>();
        userData = new UserDataIO(this);

        aquireApps();

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appNames);
        appList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userData.setBible(appPackages.get(position));
                Intent intent = new Intent();
                intent.putExtra("requestCode", appPackages.get(position));
                setResult(1, intent);
                finish();
            }
        });
        appList.setAdapter(itemsAdapter);
    }

    public ArrayList<String> aquireApps() {
        List<PackageInfo> packList = getPackageManager().getInstalledPackages(0);

        Comparator<PackageInfo> orderAppPackages = new Comparator<PackageInfo>() {
            @Override
            public int compare(PackageInfo arg0, PackageInfo arg1) {
                return arg0.applicationInfo.loadLabel(getPackageManager()).toString().compareTo(arg1.applicationInfo.loadLabel(getPackageManager()).toString());
            }
        };

        Collections.sort(packList, orderAppPackages);

        for (int i = 0; i < packList.size(); i++) {
            PackageInfo packInfo = packList.get(i);
            if ((packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                appNames.add(packInfo.applicationInfo.loadLabel(getPackageManager()).toString());
                appPackages.add(packInfo.packageName);
            }
        }

        return appNames;
    }

    public void QuickToast(String s) {
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }
}