package com.namobiletech.imamiajantri.UI.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.namobiletech.imamiajantri.R;
import com.namobiletech.imamiajantri.Utils.AnalyticsApplication;

import java.util.ArrayList;
import java.util.List;

public class duaActivity extends AppCompatActivity {

    List<String> sleeping_list = new ArrayList<String>();
    List<String> toiletList = new ArrayList<String>();
    List<String> mosqueList = new ArrayList<String>();

    List<String> sleeping_list_eng = new ArrayList<String>();
    List<String> toiletList_eng = new ArrayList<String>();
    List<String> mosqueList_eng = new ArrayList<String>();

    private TextView duaArbi;
    private TextView duaEnglish;

    private ImageView duaBackBtn;

    Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dua);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        duaBackBtn = (ImageView) findViewById(R.id.backarraow_iv_dua);

        Intent intent = getIntent();
        int heading = intent.getIntExtra("heading", 0);
        int child = intent.getIntExtra("child", 0);

        Typeface MUHAMMAI_QURANIC_FONT = Typeface.createFromAsset(getAssets(), "fonts/arbi_font.ttf");
        Typeface open_Sans_font = Typeface.createFromAsset(getAssets(), "fonts/OpenSans_Regular.ttf");

        duaArbi = (TextView) findViewById(R.id.dua_arbi);
        duaArbi.setTypeface(MUHAMMAI_QURANIC_FONT);

        duaEnglish = (TextView) findViewById(R.id.dua_english);
        duaEnglish.setTypeface(open_Sans_font);

        String sleepingDua[] = getResources().getStringArray(R.array.sleeping_dua);
        String mosqueDua[] = getResources().getStringArray(R.array.dua_mosque);
        String toilet[] = getResources().getStringArray(R.array.toilet_dua);

        String sleepingDua_eng[] = getResources().getStringArray(R.array.sleeping_dua_english);
        String mosqueDua_eng[] = getResources().getStringArray(R.array.dua_mosque_english);
        String toilet_eng[] = getResources().getStringArray(R.array.toilet_dua_english);

        for (String ch : sleepingDua)
        {
            sleeping_list.add(ch);
        }

        for (String ch1 : mosqueDua)
        {
            mosqueList.add(ch1);
        }

        for (String ch2 : toilet)
        {
            toiletList.add(ch2);
        }

        for (String ch : sleepingDua_eng)
        {
            sleeping_list_eng.add(ch);
        }

        for (String ch1 : mosqueDua_eng)
        {
            mosqueList_eng.add(ch1);
        }

        for (String ch2 : toilet_eng)
        {
            toiletList_eng.add(ch2);
        }

        if(heading == 0)
        {
            duaArbi.setText(sleeping_list.get(child));
            duaEnglish.setText(sleeping_list_eng.get(child));
        }
        else if(heading == 1)
        {
            duaArbi.setText(toiletList.get(child));
            duaEnglish.setText(toiletList_eng.get(child));
        }
        else if(heading == 2)
        {
            duaArbi.setText(mosqueList.get(child));
            duaEnglish.setText(mosqueList_eng.get(child));
        }

        duaBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Image~" + "Dua Screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
