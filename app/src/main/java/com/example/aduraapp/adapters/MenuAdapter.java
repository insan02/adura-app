package com.example.aduraapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aduraapp.R;
import com.example.aduraapp.models.Menu;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder>{
    ArrayList<Menu> listMenu = new ArrayList<Menu>();

    public MenuAdapter(ArrayList<Menu> listMenu) {
        this.listMenu = listMenu;
    }

    public void setListMenu(ArrayList<Menu>listMenu) {
        this.listMenu = listMenu;
    }
    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu menu = listMenu.get(position);
        holder.imageMenu.setImageResource(R.drawable.ic_buku);
        holder.textNamaMenu.setText(menu.getNamaMenu());

    }

    @Override
    public int getItemCount() {
        return listMenu.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageMenu;
        public TextView textNamaMenu;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMenu = itemView.findViewById(R.id.imageMenu);
            textNamaMenu = itemView.findViewById(R.id.textNamaMenu);
        }
    }
}
