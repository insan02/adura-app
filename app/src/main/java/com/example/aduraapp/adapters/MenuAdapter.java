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
    ItemMenuClickListener listener;

    public MenuAdapter(ArrayList<Menu> listMenu) {
        this.listMenu = listMenu;
    }

    public MenuAdapter(ArrayList<Menu> listMenu, ItemMenuClickListener listener) {
        this.listMenu = listMenu;
        this.listener = listener;
    }

    public void setListener(ItemMenuClickListener listener) {
        this.listener = listener;
    }

    public void setListMenu(ArrayList<Menu> listMenu) {
        this.listMenu = listMenu;
        notifyDataSetChanged();
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

        int gambarId = menu.getGambarId();
        holder.imageMenu.setImageResource(gambarId);

        holder.textNamaMenu.setText(menu.getNamaMenu());

        holder.itemView.setBackgroundResource(menu.getBackgroundColor());

    }


    @Override
    public int getItemCount() {
        return listMenu.size();
    }

    public interface ItemMenuClickListener{
        void onItemMenuClick(Menu menu);
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageMenu;
        public TextView textNamaMenu;
        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMenu = itemView.findViewById(R.id.imageMenu);
            textNamaMenu = itemView.findViewById(R.id.textNamaMenu);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Menu menu = listMenu.get(getAdapterPosition());
            listener.onItemMenuClick(menu);
        }
    }
}
