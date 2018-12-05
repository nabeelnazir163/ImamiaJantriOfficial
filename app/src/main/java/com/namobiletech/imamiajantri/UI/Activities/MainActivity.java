package com.namobiletech.imamiajantri.UI.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.namobiletech.imamiajantri.R;
import com.namobiletech.imamiajantri.UI.Fragment.QuranFragment;
import com.namobiletech.imamiajantri.UI.Fragment.duaFragment;
import com.namobiletech.imamiajantri.UI.Fragment.homeFragment;
import com.namobiletech.imamiajantri.UI.Fragment.jantriFragment;
import com.namobiletech.imamiajantri.UI.Fragment.qiblaDirection;
import com.namobiletech.imamiajantri.Utils.AnalyticsApplication;
import com.namobiletech.imamiajantri.Utils.UserSessionManager;
import com.namobiletech.imamiajantri.Utils.dbUtility.DbHelper;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //WIDGETS
    //Linear Layout
    private LinearLayout home_LL;
    private LinearLayout Jantri_LL;
    private LinearLayout qiblaDirection_LL;
    private LinearLayout Quran_LL;
    private LinearLayout Dua_LL;
    private LinearLayout logout_LL;

    //TEXTVIEW
    private TextView username_nav;
    private TextView home_tv;
    private TextView Jantri_tv;
    private TextView qiblaDirection_tv;
    private TextView Dua_tv;
    private TextView logout_tv;

    DrawerLayout drawer;

    Cursor ayahcursor;
    Cursor surahCursor;
    DbHelper dbHelper;

    RequestQueue requestQueue_a;
    StringRequest request_a;

    RequestQueue requestQueue_b;
    StringRequest request_b;

    //user session referecne
    UserSessionManager sessionManager;

    public static final String KEY_NAME = "name";

    Tracker mTracker;

    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        //referencing UserSessionMangment
        sessionManager = new UserSessionManager(MainActivity.this);

        if (!sessionManager.CheckLogin()) {
            startActivity(new Intent(MainActivity.this, SigninScreen.class));
            finish();
        }

        dbHelper = new DbHelper(MainActivity.this);

        FacebookSdk.sdkInitialize(getApplicationContext());

        home_LL = (LinearLayout) findViewById(R.id.nav_homeLay);
        Jantri_LL = (LinearLayout) findViewById(R.id.nav_jantriLay);
        qiblaDirection_LL = (LinearLayout) findViewById(R.id.nav_qiblaLayout);
        Quran_LL = (LinearLayout) findViewById(R.id.nav_quranLayout);
        Dua_LL = (LinearLayout) findViewById(R.id.nav_duaLayout);
        logout_LL = (LinearLayout) findViewById(R.id.nav_logoutLay);

        username_nav = (TextView) findViewById(R.id.username_tv_nav);
        home_tv = (TextView) findViewById(R.id.nav_home_tv);
        Jantri_tv = (TextView) findViewById(R.id.nav_jantri_tv);
        qiblaDirection_tv = (TextView) findViewById(R.id.nav_qibla_tv);
        Dua_tv = (TextView) findViewById(R.id.nav_dua_tv);
        logout_tv = (TextView) findViewById(R.id.navlogout_tv);

        Typeface open_Sans_font = Typeface.createFromAsset(getAssets(), "fonts/OpenSans_Regular.ttf");

        username_nav.setTypeface(open_Sans_font);
        home_tv.setTypeface(open_Sans_font);
        Jantri_tv.setTypeface(open_Sans_font);
        qiblaDirection_tv.setTypeface(open_Sans_font);
        Dua_tv.setTypeface(open_Sans_font);
        logout_tv.setTypeface(open_Sans_font);


        home_LL.setOnClickListener(this);
        Jantri_LL.setOnClickListener(this);
        qiblaDirection_LL.setOnClickListener(this);
        Quran_LL.setOnClickListener(this);
        Dua_LL.setOnClickListener(this);
        logout_LL.setOnClickListener(this);

        HashMap<String, String> user = new HashMap<>();

        user = sessionManager.getUserDetails();

        String username = user.get(KEY_NAME);

        username_nav.setText(username);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer,new homeFragment());
        fragmentTransaction.commit();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("234249254590-0vr0oofi19kmse1u6jenvriraufnfhqd.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

//        loadArbiAyah();
//        loadQURAN();

        /*File file = getFileStreamPath("imamiajanri.pdf");
            if(!file.exists()) {
                new DownloadFileFromURL().execute(PDF_LINK);
            }
            else
            {
                Log.v("exists", "abc");
            }*/
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            Fragment fragment = getVisibleFragment();

            if(fragment instanceof homeFragment)
            {
                super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            }
            else {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer,new homeFragment());
                fragmentTransaction.commit();
            }

        }
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch(view.getId()) {
            case R.id.nav_homeLay:

                fragmentTransaction.replace(R.id.mainContainer, new homeFragment());
                fragmentTransaction.commit();
                drawer.closeDrawer(Gravity.START);

                break;
                case R.id.nav_jantriLay:

                fragmentTransaction.replace(R.id.mainContainer, new jantriFragment());
                fragmentTransaction.commit();
                drawer.closeDrawer(Gravity.START);

                break;

            case R.id.nav_qiblaLayout:

                fragmentTransaction.replace(R.id.mainContainer, new qiblaDirection());
                fragmentTransaction.commit();
                drawer.closeDrawer(Gravity.START);

                break;

            case R.id.nav_duaLayout:

                fragmentTransaction.replace(R.id.mainContainer, new duaFragment());
                fragmentTransaction.commit();
                drawer.closeDrawer(Gravity.START);

                break;

            case R.id.nav_quranLayout:

                fragmentTransaction.replace(R.id.mainContainer, new QuranFragment());
                fragmentTransaction.commit();
                drawer.closeDrawer(Gravity.START);

                break;

            case R.id.nav_logoutLay:

                Auth.GoogleSignInApi.signOut(mGoogleApiClient);

                LoginManager.getInstance().logOut();
                sessionManager.Logoutuser();
                finish();

                break;
        }

    }

    private void loadArbiAyah() {

        ayahcursor = dbHelper.getAyyahsInformation(dbHelper);
        requestQueue_a = Volley.newRequestQueue(this);
        String quran_url_uthmani = "http://api.alquran.cloud/quran";

        Toast.makeText(getApplicationContext(), "" + ayahcursor.getCount(), Toast.LENGTH_LONG).show();


        request_a = new StringRequest(Request.Method.GET, quran_url_uthmani, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (ayahcursor.getCount() == 0) {
                        dbHelper.PutAyyahInformation(dbHelper, jsonObject);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage() + " a ", Toast.LENGTH_LONG).show();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage() +  "b", Toast.LENGTH_LONG).show();
            }
        });
        request_a.setRetryPolicy(new DefaultRetryPolicy( 10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue_a.add(request_a);

    }

    private void loadQURAN() {

        surahCursor = dbHelper.getQuranInformation(dbHelper);
        requestQueue_b = Volley.newRequestQueue(this);
        String quran_url_uthmani = "http://api.alquran.cloud/quran/en.asad";

        request_b = new StringRequest(Request.Method.GET, quran_url_uthmani, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (surahCursor.getCount() == 0) {
                        dbHelper.PutQuranInformation(dbHelper, jsonObject);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage()+ "c", Toast.LENGTH_LONG).show();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();

            }
        });

        request_b.setRetryPolicy(new DefaultRetryPolicy( 10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue_b.add(request_b);

    }
/*
    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        *//**
         * Before starting background thread
         * *//*
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Starting download");
            Log.d("pdf", "start");
            Toast.makeText(getApplicationContext(), "downloading", Toast.LENGTH_LONG).show();
        }

        *//**
         * Downloading file in background thread
         * *//*
        @Override
        protected String doInBackground(String... f_url) {

            InputStream input = null;
            FileOutputStream output = null;

            try {
                URL url = new URL(f_url[0]);

                String outputName = "imamiajanri.pdf";

                input = url.openConnection().getInputStream();
                output = getApplicationContext().openFileOutput(outputName, Context.MODE_PRIVATE);

                int read;
                byte[] data = new byte[1024];
                while ((read = input.read(data)) != -1) {
                    output.write(data, 0, read);

                    Log.d("abc", output.toString());
                }

                return outputName;

            }
            catch (IOException e)
            {
                Log.e("exception", e.toString());
//                Toast.makeText(getApplicationContext(), "downloading interupted", Toast.LENGTH_LONG).show();
            }
            finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        Log.e("exception", e.toString());
//                        Toast.makeText(getApplicationContext(), "downloading interupted", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        Log.e("exception", e.toString());
//                        Toast.makeText(getApplicationContext(), "downloading interupted", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }



        *//**
         * After completing background task
         * **//*
        @Override
        protected void onPostExecute(String file_url) {
            System.out.println("Downloaded");

            Log.d("def", getFileStreamPath("imamiajanri.pdf").toString());

            SharedPreferences sharedPreferences =  getSharedPreferences("pdf", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            jantriFragment jantriFragment = new jantriFragment();

            Toast.makeText(getApplicationContext(), "downloaded", Toast.LENGTH_LONG).show();


            File file = getFileStreamPath("imamiajanri.pdf");
            if (file.exists()) {
                editor.putBoolean("pdf", true).apply();
            }else {
                editor.putBoolean("pdf", false).apply();
            }
        }

    }*/

    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("Image~" + "Splash Screen");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

}
