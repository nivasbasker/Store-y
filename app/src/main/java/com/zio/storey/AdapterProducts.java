package com.zio.storey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zio.storey.data.ModelOrder;
import com.zio.storey.data.ModelProduct;

import java.util.List;

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.ViewHolder> {

    final Context context;
    final List<ModelProduct> prodList;

    final List<ModelOrder> orders;

    final int type;

    private OnItemClickListener itemClickListener; // Interface for click callbacks when used for searching
    private QuantityChangeListener quantityChangeListener;

    public interface OnItemClickListener {
        void onItemClick(ModelProduct product);
    }

    public interface QuantityChangeListener {
        void onQuantityIncreased(ModelOrder order);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public void setQuantityChangeListener(QuantityChangeListener listener) {
        this.quantityChangeListener = listener;
    }

    public AdapterProducts(Context context, List<ModelProduct> prodList) {
        this.context = context;
        this.prodList = prodList;
        orders = null;
        type = 1;
    }

    public AdapterProducts(List<ModelOrder> orders, Context context, QuantityChangeListener listener) {
        this.context = context;
        this.orders = orders;
        prodList = null;
        type = 2;
        this.quantityChangeListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (type == 1) {
            ModelProduct model = prodList.get(position);

            holder.t1.setText(model.getProduct_name());
            holder.t2.setText(model.getQuantity() + "  x  ₹" + model.getNet());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(model);
                    }
                }
            });

        } else {
            ModelOrder model = orders.get(position);

            holder.t1.setText(model.getProduct_name());
            holder.t2.setText(model.getQuantity() + "  x  ₹" + model.getNet());

            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int q = model.getQuantity();
                    Toast.makeText(context, String.valueOf(model.getMaxi()), Toast.LENGTH_SHORT).show();
                    if (q < model.getMaxi()) {
                        model.setQuantity(q + 1);
                        holder.t2.setText(model.getQuantity() + "  x  ₹" + model.getNet());
                        if (quantityChangeListener != null) {
                            quantityChangeListener.onQuantityIncreased(model);
                        }
                    }
                }
            });
        }


    }


    @Override
    public int getItemCount() {
        if (type == 1)
            return prodList.size();
        else return orders.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        public TextView t1, t2;
        LinearLayout t3;
        ImageButton add, edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            t1 = itemView.findViewById(R.id.item_name);
            t2 = itemView.findViewById(R.id.item_net);
            t3 = itemView.findViewById(R.id.product_laout);
            add = itemView.findViewById(R.id.add);

        }
    }
}
