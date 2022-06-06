package com.example.tiendita.Helpers;

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

import com.example.tiendita.R;
import com.example.tiendita.Retrofit.Usuarios;
import com.example.tiendita.Views.DetailActivity;
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
    private List<Usuarios> usuarios;
    public String searchString = "";

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView usuarioTxt, nombreTxt, passwordTxt;
        private MaterialLetterIcon mIcon;
        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.mMaterialLetterIcon);
            usuarioTxt = itemView.findViewById(R.id.mUsuarioTxt);
            nombreTxt = itemView.findViewById(R.id.mNombreTxt);
            passwordTxt = itemView.findViewById(R.id.mPasswordTxt);
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

    public MyAdapter(Context mContext, ArrayList<Usuarios> usuarios) {
        this.c = mContext;
        this.usuarios = usuarios;
        mMaterialColors = c.getResources().getIntArray(R.array.colors);
        mBackground = mTypedValue.resourceId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.model, parent, false);
        view.setBackgroundResource(mBackground);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Usuarios u = usuarios.get(position);

        holder.usuarioTxt.setText(u.getUsuario());
        holder.nombreTxt.setText(u.getNombre());
        holder.mIcon.setInitials(true);
        holder.mIcon.setInitialsNumber(1);
        holder.mIcon.setLetterSize(25);
        holder.mIcon.setShapeColor(mMaterialColors[new Random().nextInt(mMaterialColors.length)]);
        holder.mIcon.setLetter(u.getUsuario());

        String usuario = u.getUsuario().toLowerCase(Locale.getDefault());
        String nombre = u.getNombre().toLowerCase(Locale.getDefault());

        if (usuario.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = usuario.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().newSpannable(holder.usuarioTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.RED), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.usuarioTxt.setText(spanString);
        } else {
            // Utils.show(c, "Buscando, valor vacio");
        }

        if (nombre.contains(searchString) && !(searchString.isEmpty())) {
            int startPos = nombre.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanString = Spannable.Factory.getInstance().newSpannable(holder.nombreTxt.getText());
            spanString.setSpan(new ForegroundColorSpan(Color.BLUE), startPos, endPos,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.nombreTxt.setText(spanString);
        }

        holder.setItemClickListener(pos -> {
            Utils.sendUsuariosActivity(c, u, DetailActivity.class);
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    interface ItemClickListener {
        void onItemClick(int pos);
    }
}
