package com.namobiletech.imamiajantri.UI.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.namobiletech.imamiajantri.Adapter.ExpandableListAdapter;
import com.namobiletech.imamiajantri.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class duaFragment extends Fragment {

    View v;

    ExpandableListView duaExpandableList;

    private ExpandableListAdapter myAdapter;

    AdView adView;

    public duaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_dua, container, false);

        MobileAds.initialize(getContext(), "ca-app-pub-4249442105194885~9707576313");

        adView = (AdView) v.findViewById(R.id.dua_banner_ad);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("95C4D4650E2CD4B48542C977DA198C30").build();
        adView.loadAd(adRequest);

        duaExpandableList = (ExpandableListView) v.findViewById(R.id.duasExpandableListview);

        //Heading List ... !!
        List<String> headings = new ArrayList<String>();

        //Childs List
        List<String> child1 = new ArrayList<String>();
        List<String> child2 = new ArrayList<String>();
        List<String> child3 = new ArrayList<String>();

        HashMap<String, List<String>> childList = new HashMap<>();

        String headingItems[] = getActivity().getResources().getStringArray(R.array.header_titles);

        String l1[] = getActivity().getResources().getStringArray(R.array.h1);
        String l2[] = getActivity().getResources().getStringArray(R.array.h2);
        String l3[] = getActivity().getResources().getStringArray(R.array.h3);

        for (String title : headingItems)
        {
            headings.add(title);
        }

        for (String child : l1)
        {
            child1.add(child);
        }

        for (String child : l2)
        {
            child2.add(child);
        }

        for (String child : l3)
        {
            child3.add(child);
        }

        childList.put(headings.get(0),child1);
        childList.put(headings.get(1),child2);
        childList.put(headings.get(2),child3);

        myAdapter = new ExpandableListAdapter(getContext(), headings, childList);

        duaExpandableList.setAdapter(myAdapter);

        return v;
    }

}
