package com.seven.clip.nziyodzemethodist;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class Notifications extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Button eventLogButton = findViewById(R.id.eventLogButton);
        final TextView notificationText = findViewById(R.id.notification_text);
        LinearLayout includeView = findViewById(R.id.bubble_include);
        final String[] reviews = getResources().getStringArray(R.array.reviews_comments);
        final String[] replies = getResources().getStringArray(R.array.review_replies);
        final TypedArray images = getResources().obtainTypedArray(R.array.reviews_stars);


        View[] bubbles = new View[reviews.length];
        Random rnd = new Random();
        //
        //includeView.addView(bubbles);


        for (int i = 0; i < reviews.length; i++) {
            bubbles[i] = getLayoutInflater().inflate(R.layout.bubble_picker, null);
            TextView t1 = bubbles[i].findViewById(R.id.userReview);
            TextView t2 = bubbles[i].findViewById(R.id.userReply);
            ImageView stars = bubbles[i].findViewById(R.id.review_stars);
            stars.setImageResource(images.getResourceId(i, -1));
            t1.setText(reviews[i]);
            t2.setText(replies[i]);
            t1.setTextColor(Color.WHITE);
            t2.setTextColor(Color.WHITE);
            t1.setBackground(getResources().getDrawable(R.drawable.burn_round));
            t2.setBackground(getResources().getDrawable(R.drawable.burn_round));
            if (replies[i].equals(".")) t2.setVisibility(View.GONE);
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            t1.getBackground().mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            includeView.addView(bubbles[i]);

        }
        images.recycle();
        eventLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.seven.clip.nziyodzemethodist")));
                } catch (ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.seven.clip.nziyodzemethodist")));
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
