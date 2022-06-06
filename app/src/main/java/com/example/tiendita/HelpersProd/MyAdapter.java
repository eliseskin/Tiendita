package com.example.tiendita.HelpersProd;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendita.Products.DetailActivity;
import com.example.tiendita.HelpersProd.Utils;
import com.example.tiendita.R;
import com.example.tiendita.RetrofitProd.Productos;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context c;
    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private int[] mMaterialColors;
    private List<Productos> productos;
    public String searchString = "";

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView productoTxt, precioTxt, descripcionTxt;
        private MaterialLetterIcon mIcon;
        private MyAdapter.ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.mMaterialLetterIconP);
            productoTxt = itemView.findViewById(R.id.mProductoTxt);
            precioTxt = itemView.findViewById(R.id.mPrecioTxt);
            descripcionTxt = itemView.findViewById(R.id.mDescripcionTxt);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }
    }

    public MyAdapter(Context mContext, ArrayList<Productos> productos) {
        this.c = mContext;
        this.productos = productos;
        mMaterialColors = c.getResources().getIntArray(R.array.colors);
        mBackground = mTypedValue.resourceId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.modelprod, parent, false);
        view.setBackgroundResource(mBackground);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Productos ca = productos.get(position);

        holder.productoTxt.setText(ca.getProducto());
        holder.descripcionTxt.setText(ca.getDescripcion());
        holder.precioTxt.setText(ca.getPrecio());
        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(1);
        holder.mIcon.setLetterSize(25);
        holder.mIcon.setShapeColor(mMaterialColors[new Random().nextInt(mMaterialColors.length)]);
        holder.mIcon.setLetter(ca.getProducto());

        String producto = ca.getProducto().toLowerCase(Locale.getDefault());

        if (producto.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = producto.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().newSpannable(holder.productoTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.productoTxt.setText(spanString);
        } else {
            // Utils.show(c, "Buscando, valor vacio");
        }

        holder.setItemClickListener(pos -> {
            Utils.sendProductosActivity(c, ca, DetailActivity.class);
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    interface ItemClickListener {
        void onItemClick(int pos);
    }
}
