package com.img.equran;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class Tutor extends Fragment {

ListView lv;
ArrayList<String> arr=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tutor, container, false);


        lv=view.findViewById(R.id.list_view);
        arr.add("03214788470");
        arr.add("03130449397");
        //ArrayAdapter adapter=new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,arr);
        TeacherAdapter adapter=new TeacherAdapter(getActivity(),arr);
        lv.setAdapter(adapter);

        return view;
    }
}
