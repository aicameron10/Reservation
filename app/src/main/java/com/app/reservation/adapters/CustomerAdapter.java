package com.app.reservation.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.reservation.R;
import com.app.reservation.activities.MainActivity;
import com.app.reservation.model.Customers;

import java.util.List;


public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    private Context mContext;
    private List<Customers> searchList;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, surname, idCus,id;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            surname = (TextView) view.findViewById(R.id.surname);
            idCus = (TextView) view.findViewById(R.id.card_id);
            id = (TextView) view.findViewById(R.id.id);
        }
    }


    public CustomerAdapter(Context mContext, List<Customers> searchList) {
        this.mContext = mContext;
        this.searchList = searchList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Customers results = searchList.get(position);
        holder.name.setText(results.getCustomerFirstName());
        holder.surname.setText(results.getCustomerLastName());
        holder.id.setText(results.getId() + ".");
        holder.idCus.setText(String.valueOf(results.getId()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref = mContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE); // 0 - for private mode
                editor = pref.edit();
                editor.putString("customerName", results.getCustomerFirstName() + " " + results.getCustomerLastName());
                editor.apply();

                ((MainActivity) mContext).closeKeyBoard();
                ((MainActivity) mContext).displayView(1);

            }
        });


    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }
}
