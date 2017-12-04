package com.idoon.fruitandveg.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idoon.fruitandveg.Interface.ItemClickListener;
import com.idoon.fruitandveg.R;

/**
 * Created by itayi on 02/12/2017.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMenuName;
    public ImageView imageView;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView){
        super(itemView);

        txtMenuName = (TextView) itemView.findViewById(R.id.menu_name);
        imageView = (ImageView) itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);

    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setTxtMenuName(TextView txtMenuName) {
        this.txtMenuName = txtMenuName;
    }

    @Override
    public void onClick(View view){
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
