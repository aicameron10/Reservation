package com.app.reservation.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.app.reservation.R;
import com.app.reservation.activities.MainActivity;
import com.app.reservation.adapters.CustomerAdapter;
import com.app.reservation.model.Customers;
import com.app.reservation.model.NavItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private CustomerAdapter adapter;
    private List<Customers> searchList;
    public EditText search;
    View rootView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
        editor = pref.edit();


        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((MainActivity) getActivity()).loadDrawsHide();
        ((MainActivity) getActivity()).loadDrawsDisplay();


        NavItem nav = new NavItem();
        nav.setPage("HomePage");

        search = (EditText) rootView.findViewById(R.id.search);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        searchList = Arrays.asList(gson.fromJson(loadJSONFromAsset(), Customers[].class));
        adapter = new CustomerAdapter(getActivity(), searchList);
        recyclerView.setAdapter(adapter);

        addTextListener();

        return rootView;
    }


    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getActivity().getAssets().open("customer_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void addTextListener() {

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final List<Customers> list = new ArrayList<Customers>();

                for (int i = 0; i < searchList.size(); i++) {

                    final String text = searchList.get(i).getCustomerFirstName().toLowerCase();
                    if (text.contains(query)) {

                        list.add(searchList.get(i));

                    }
                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new CustomerAdapter(getActivity(), list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();  // data set changed
            }
        });
    }
}
