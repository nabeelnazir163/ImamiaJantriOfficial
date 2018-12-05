package com.namobiletech.imamiajantri.Adapter;

import  android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.namobiletech.imamiajantri.R;
import com.namobiletech.imamiajantri.UI.Activities.duaActivity;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap)
    {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount()
    {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i)
    {
        return listHashMap.get(listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i)
    {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1)
    {
        return listHashMap.get(listDataHeader.get(i)).get(i1); // i = group item , i1 = child item
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup)
    {
        String headerTitle = (String) getGroup(i);

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group, null);
        }

        TextView lblListheader = (TextView) view.findViewById(R.id.lblListHeader);
        lblListheader.setTypeface(null, Typeface.BOLD);
        Typeface open_Sans_font = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans_Regular.ttf");
        lblListheader.setTypeface(open_Sans_font);
        lblListheader.setText(headerTitle);

        if(b)
        {
            lblListheader.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
        else
        {
            lblListheader.setTextColor(context.getResources().getColor(android.R.color.black));
        }

        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup)
    {
        final String childText = (String) getChild(i, i1 );

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }

        TextView txtlistChild = (TextView) view.findViewById(R.id.lblListItem);
        Typeface open_Sans_font = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans_Regular.ttf");
        txtlistChild.setTypeface(open_Sans_font);
        txtlistChild.setText(childText);

        txtlistChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, duaActivity.class);
                intent.putExtra("heading", i);
                intent.putExtra("child", i1);
                context.startActivity(intent);

            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1)
    {
        return true;
    }
}
