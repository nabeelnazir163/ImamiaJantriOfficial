package com.namobiletech.imamiajantri.UI.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.namobiletech.imamiajantri.R;
import com.namobiletech.imamiajantri.UI.Activities.MainActivity;
import com.namobiletech.imamiajantri.UI.Activities.ReadJantri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static android.content.Context.MODE_PRIVATE;

public class jantriFragment extends Fragment {

    //View
    View v;

    //WIDGETS
    //TextView
    private TextView imamiaJantri_tv;

    //Linear Layout
    private RelativeLayout jantriLyout;

    AdView adView;

    private final String PDF_LINK = "http://imamiajantri.com/imamiajantrimedium.pdf";

    public jantriFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_jantri, container, false);

        Typeface open_Sans_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans_Regular.ttf");

        MobileAds.initialize(getContext(), "ca-app-pub-4249442105194885~9707576313");

        adView = (AdView) v.findViewById(R.id.read_banner_ad);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("95C4D4650E2CD4B48542C977DA198C30").build();
        adView.loadAd(adRequest);

        imamiaJantri_tv = (TextView) v.findViewById(R.id.imamiaJantriText);
        imamiaJantri_tv.setTypeface(open_Sans_font);

        jantriLyout = (RelativeLayout) v.findViewById(R.id.jantriLayout_jantriFrag);

        jantriLyout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), ReadJantri.class));

            }
        });

        return v;
    }

    /*class DownloadFileFromURL extends AsyncTask<String, String, String> {

        *//**
         * Before starting background thread
         * *//*
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            System.out.println("Starting download");

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
                output = getContext().openFileOutput(outputName, Context.MODE_PRIVATE);

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
                Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
            finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
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

            Log.d("def", getContext().getFileStreamPath("imamiajanri.pdf").toString());

            SharedPreferences sharedPreferences =  getContext().getSharedPreferences("pdf", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putBoolean("pdf", true).apply();
        }

    }*/

}
