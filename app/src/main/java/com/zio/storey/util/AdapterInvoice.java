package com.zio.storey.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.zio.storey.R;
import com.zio.storey.data.ModelInvoice;

import java.util.List;

public class AdapterInvoice extends RecyclerView.Adapter<AdapterInvoice.ViewHolder> {

    final Context context;
    final List<ModelInvoice> InvList;


    public AdapterInvoice(Context context, List<ModelInvoice> InvList) {
        this.context = context;
        this.InvList = InvList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_invoice, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModelInvoice model = InvList.get(position);

        holder.t1.setText("#" + model.getInv_id());
        holder.t3.setText("₹" + model.getNet());


    }


    @Override
    public int getItemCount() {
        return InvList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView t1, t2, t3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(R.id.inv_num);
            t2 = itemView.findViewById(R.id.inv_date);
            t3 = itemView.findViewById(R.id.inv_amt);

        }
    }
}
