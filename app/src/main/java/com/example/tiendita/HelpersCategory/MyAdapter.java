package com.example.tiendita.HelpersCategory;

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

import com.example.tiendita.Category.DetailActivity;
import com.example.tiendita.R;
import com.example.tiendita.RetrofitCategory.Categorias;
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
    private List<Categorias> categorias;
    public String searchString = "";

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView categoriaTxt;
        private MaterialLetterIcon mIcon;
        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.mMaterialLetterIconC);
            categoriaTxt = itemView.findViewById(R.id.mCategoriaTxt);
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

    public MyAdapter(Context mContext, ArrayList<Categorias> categorias) {
        this.c = mContext;
        this.categorias = categorias;
        mMaterialColors = c.getResources().getIntArray(R.array.colors);
        mBackground = mTypedValue.resourceId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.modelcategoria, parent, false);
        view.setBackgroundResource(mBackground);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Categorias ca = categorias.get(position);

        holder.categoriaTxt.setText(ca.getCategoria());
        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(1);
        holder.mIcon.setLetterSize(15);
        holder.mIcon.setShapeColor(mMaterialColors[new Random().nextInt(mMaterialColors.length)]);
        holder.mIcon.setLetter(ca.getCategoria());

        String categoria = ca.getCategoria().toLowerCase(Locale.getDefault());

        if (categoria.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = categoria.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().newSpannable(holder.categoriaTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.categoriaTxt.setText(spanString);
        } else {
            // Utils.show(c, "Buscando, valor vacio");
        }

        holder.setItemClickListener(pos -> {
            Utils.sendCategoriasActivity(c, ca, DetailActivity.class);
        });
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    interface ItemClickListener {
        void onItemClick(int pos);
    }
}
