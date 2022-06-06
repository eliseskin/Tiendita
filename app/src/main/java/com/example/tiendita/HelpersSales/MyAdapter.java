package com.example.tiendita.HelpersSales;

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

import com.example.tiendita.HelpersSales.Utils;
import com.example.tiendita.Sales.DetailActivity;
import com.example.tiendita.R;
import com.example.tiendita.RetrofitSales.Ventas;
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
    private List<Ventas> ventas;
    public String searchString = "";

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView folioTxt, cantidadTxt, totalTxt;
        private MaterialLetterIcon mIcon;
        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.mMaterialLetterIconV);
            folioTxt = itemView.findViewById(R.id.mFolioventaTxt);
            cantidadTxt = itemView.findViewById(R.id.mCantidadTxt);
            totalTxt = itemView.findViewById(R.id.mTotalTxt);
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

    public MyAdapter(Context mContext, ArrayList<Ventas> ventas) {
        this.c = mContext;
        this.ventas = ventas;
        mMaterialColors = c.getResources().getIntArray(R.array.colors);
        mBackground = mTypedValue.resourceId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.modelventa, parent, false);
        view.setBackgroundResource(mBackground);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Ventas ca = ventas.get(position);

        holder.folioTxt.setText(ca.getFolioventa());
        holder.cantidadTxt.setText(ca.getCatidad_productos());
        holder.totalTxt.setText(ca.getTotal());
        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(1);
        holder.mIcon.setLetterSize(25);
        holder.mIcon.setShapeColor(mMaterialColors[new Random().nextInt(mMaterialColors.length)]);
        holder.mIcon.setLetter(ca.getFolioventa());

        String folio = ca.getFolioventa().toLowerCase(Locale.getDefault());

        if (folio.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = folio.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().newSpannable(holder.folioTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.folioTxt.setText(spanString);
        } else {
            // Utils.show(c, "Buscando, valor vacio");
        }

        holder.setItemClickListener(pos -> {
            Utils.sendVentasActivity(c, ca, DetailActivity.class);
        });
    }

    @Override
    public int getItemCount() {
        return ventas.size();
    }

    interface ItemClickListener {
        void onItemClick(int pos);
    }
}
