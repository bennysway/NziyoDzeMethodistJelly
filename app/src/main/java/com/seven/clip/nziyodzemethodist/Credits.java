package com.seven.clip.nziyodzemethodist;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Credits extends AppCompatActivity {
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);


        TextView cr1 = findViewById(R.id.credit1);
        TextView cr2 = findViewById(R.id.credit2);
        TextView cr3 = findViewById(R.id.credit3);
        TextView cr4 = findViewById(R.id.credit4);
        TextView cr5 = findViewById(R.id.credit5);
        ImageView cr6 = findViewById(R.id.credit6);
        ImageView cr7 = findViewById(R.id.credit7);
        ImageView cr8 = findViewById(R.id.credit8);
        ImageView cr9 = findViewById(R.id.credit9);
        ImageView cr10 = findViewById(R.id.credit10);
        ImageView cr11 = findViewById(R.id.credit11);
        ImageView cr12 = findViewById(R.id.credit12);
        ImageView cr13 = findViewById(R.id.credit13);
        ImageView cr14 = findViewById(R.id.credit14);
        TextView cr15 = findViewById(R.id.credit15);
        TextView cr16 = findViewById(R.id.credit16);
        TextView cr17 = findViewById(R.id.credit17);
        ImageView cr18 = findViewById(R.id.credit18);
        TextView cr19 = findViewById(R.id.credit19);
        ImageView cr20 = findViewById(R.id.credit20);
        TextView cr21 = findViewById(R.id.credit21);
        ImageView cr22 = findViewById(R.id.credit22);


        cr1.setAlpha(0f);
        cr2.setAlpha(0f);
        cr3.setAlpha(0f);
        cr4.setAlpha(0f);
        cr5.setAlpha(0f);
        cr6.setAlpha(0f);
        cr7.setAlpha(0f);
        cr8.setAlpha(0f);
        cr9.setAlpha(0f);
        cr10.setAlpha(0f);
        cr11.setAlpha(0f);
        cr12.setAlpha(0f);
        cr13.setAlpha(0f);
        cr14.setAlpha(0f);
        cr15.setAlpha(0f);
        cr16.setAlpha(0f);
        cr17.setAlpha(0f);
        cr18.setAlpha(0f);
        cr19.setAlpha(0f);
        cr20.setAlpha(0f);
        cr21.setAlpha(0f);
        cr22.setAlpha(0f);

        int a =100,m=150;
        cr1.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr2.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr3.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr4.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr5.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr6.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr7.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr8.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr9.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr10.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr11.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr12.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr13.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr14.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr22.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr15.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr16.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr17.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr18.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr19.animate().alpha(1f).setStartDelay(a);
        a+=m;
        cr20.animate().alpha(1f).setStartDelay(a);

        cr21.animate().alpha(1f).setStartDelay(5000);

        cr6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                try {
                    // get the Twitter app if possible
                    context.getPackageManager().getPackageInfo("com.twitter.android", 0);
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=bennyfsway"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (Exception e) {
                    // no Twitter app, revert to browser
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/bennyfsway"));
                }
                startActivity(intent);
            }
        });

        cr7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                try {

                    getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    String url = "https://www.facebook.com/bennyswayofficial/";

                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href="+url));
                    startActivity(intent);
                }

                catch (Exception e) {

                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/bennyswayofficial/"));
                    startActivity(intent);

                    e.printStackTrace();
                }
            }
        });

        cr8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","farai.fatso@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        cr9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                try {
                    intent = new Intent("android.intent.action.VIEW");
                    intent.setClassName("com.skype.raider", "com.skype.raider.Main");
                    intent.setData(Uri.parse("skype:blessing_farai"));
                    startActivity(intent);
                } catch (Exception e){
                    QuickToast("Install Skype to view profile");
                    QuickToast("Or Skype call this number");
                    intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:+48739497898"));
                    startActivity(intent);
                }

            }
        });

        cr10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=null;
                String url = "http://www.youtube.com/channel/UCku3u-BFZL3BKnerFjNiTPw";
                try {
                    intent =new Intent(Intent.ACTION_VIEW);
                    intent.setPackage("com.google.android.youtube");
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                }
            }
        });

        cr11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/u/0/100524571138591471113")));
            }
        });

        cr12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+48739497898"));
                startActivity(intent);
            }
        });
        cr13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/_u/bennysway");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/bennysway")));
                }
            }
        });
        cr14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://bennysway.blogspot.com/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        cr18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/syedowaisali/crystal-preloaders";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        cr20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.seven.clip.nziyodzemethodist")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.seven.clip.nziyodzemethodist")));
                }
            }
        });

        cr22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://faraifatso.wixsite.com/bennysway";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }


    public void QuickToast(String s){
        Toast.makeText(this, s,
                Toast.LENGTH_SHORT).show();
    }
}
