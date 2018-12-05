package com.namobiletech.imamiajantri.UI.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namobiletech.imamiajantri.Adapter.SurahRecyclerAdapter;
import com.namobiletech.imamiajantri.Model.SurahList;
import com.namobiletech.imamiajantri.R;
import com.namobiletech.imamiajantri.Utils.dbUtility.DbHelper;

import java.util.ArrayList;

public class QuranFragment extends Fragment {


    View v;

    ArrayList<SurahList> arrayList = new ArrayList<>();
    private RecyclerView mSurahRecyclerview;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    public QuranFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_quran, container, false);

        mSurahRecyclerview = v.findViewById(R.id.surahsindex);
        layoutManager = new LinearLayoutManager(getContext());
        mSurahRecyclerview.setLayoutManager(layoutManager);
        mSurahRecyclerview.setHasFixedSize(true);

        DbHelper dbHelper = new DbHelper(getContext());

        Cursor cursor = dbHelper.getQuranInformation(dbHelper);
        cursor.moveToNext();

        do{

            SurahList surahList = new SurahList(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
            arrayList.add(surahList);

        }
        while(cursor.moveToNext());

        dbHelper.close();

        adapter = new SurahRecyclerAdapter(arrayList, getContext());
        mSurahRecyclerview.setAdapter(adapter);

        return v;
    }





}
