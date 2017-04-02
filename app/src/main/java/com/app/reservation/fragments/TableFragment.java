package com.app.reservation.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.reservation.R;
import com.app.reservation.activities.MainActivity;
import com.app.reservation.adapters.TableAdapter;
import com.app.reservation.model.Data;
import com.app.reservation.model.NavItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class TableFragment extends Fragment {

    View rootView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    RecyclerView gridView;
    private TableAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_tables, container, false);
        pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
        editor = pref.edit();

        ((MainActivity) getActivity()).loadDrawsHide();
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);


        NavItem nav = new NavItem();
        nav.setPage("Home");


        Gson gson = new Gson();
        String json = pref.getString("jsonTable", "");
        Type listType = new TypeToken<List<Data>>() {}.getType();
        final List<Data> dd = gson.fromJson(json, listType);

        gridView = (RecyclerView) rootView.findViewById(R.id.gridView);

        int numberOfColumns = 3;
        gridView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        adapter = new TableAdapter(getActivity(), dd);
        gridView.setAdapter(adapter);



        return rootView;
    }


}
