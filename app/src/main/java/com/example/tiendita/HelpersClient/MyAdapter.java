package com.example.tiendita.HelpersClient;

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

import com.example.tiendita.Clients.DetailActivity;
import com.example.tiendita.R;
import com.example.tiendita.RetrofitClient.Clientes;
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
    private List<Clientes> clientes;
    public String searchString = "";

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nombreTxt, direccionTxt, telefonoTxt;
        private MaterialLetterIcon mIcon;
        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.mMaterialLetterIconCl);
            nombreTxt = itemView.findViewById(R.id.mNombreCTxt);
            direccionTxt = itemView.findViewById(R.id.mDireccionTxt);
            telefonoTxt = itemView.findViewById(R.id.mTelefonoTxt);
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

    public MyAdapter(Context mContext, ArrayList<Clientes> clientes) {
        this.c = mContext;
        this.clientes = clientes;
        mMaterialColors = c.getResources().getIntArray(R.array.colors);
        mBackground = mTypedValue.resourceId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.modelclientes, parent, false);
        view.setBackgroundResource(mBackground);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Clientes ca = clientes.get(position);

        holder.nombreTxt.setText(ca.getNombre());
        holder.direccionTxt.setText(ca.getDireccion());
        holder.telefonoTxt.setText(ca.getTelefono());
        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(1);
        holder.mIcon.setLetterSize(25);
        holder.mIcon.setShapeColor(mMaterialColors[new Random().nextInt(mMaterialColors.length)]);
        holder.mIcon.setLetter(ca.getNombre());

        String nombre = ca.getNombre().toLowerCase(Locale.getDefault());

        if (nombre.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = nombre.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().newSpannable(holder.nombreTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.nombreTxt.setText(spanString);
        } else {
            // Utils.show(c, "Buscando, valor vacio");
        }

        holder.setItemClickListener(pos -> {
            Utils.sendClientesActivity(c, ca, DetailActivity.class);
        });
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    interface ItemClickListener {
        void onItemClick(int pos);
    }
}
