package com.app.reservation.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.reservation.R;
import com.app.reservation.model.Data;
import com.google.gson.Gson;

import java.util.List;


public class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyViewHolder> {
    private Context mContext;
    private List<Data> searchList;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public RelativeLayout color;
        public ImageView img;


        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.title);;
            color = (RelativeLayout) view.findViewById(R.id.color);
            img = (ImageView) view.findViewById(R.id.thumbnail);

        }
    }


    public TableAdapter(Context mContext, List<Data> searchList) {
        this.mContext = mContext;
        this.searchList = searchList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Data results = searchList.get(position);

        System.out.println(results.getValue());

        holder.title.setText("Table " + position + " " + searchList.get(position).getCustomer());

        if (!results.getValue()) {
            holder.color.setBackgroundColor(mContext.getResources().getColor(R.color.light_blue));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (results.getValue()) {

                    pref = mContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
                    editor = pref.edit();
                    results.setValue(false);
                    results.setCustomer(pref.getString("customerName", ""));

                    Gson gson = new Gson();
                    String json = gson.toJson(searchList);
                    pref.edit().putString("jsonTable", json).apply();
                    System.out.println(pref.getString("jsonTable", ""));


                    notifyDataSetChanged();
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}