package com.udacity.food.feasta.foodfeasta.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.food.feasta.foodfeasta.R;

import butterknife.ButterKnife;

/**
 * Created by jinal on 10/22/2016.
 */

public class MenuCursorAdapter extends
        RecyclerViewCursorAdapter<MenuCursorAdapter.MenuViewHolder>
implements View.OnClickListener{
    private final LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    public MenuCursorAdapter(final Context context)
    {
        super();

        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType)
    {
        final View view = this.layoutInflater.inflate(R.layout.menu_item, parent, false);
        view.setOnClickListener(this);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, final Cursor cursor)
    {
        holder.bindData(cursor);
    }

     /*
     * View.OnClickListener
     */

    @Override
    public void onClick(final View view)
    {
        if (this.onItemClickListener != null)
        {
            final RecyclerView recyclerView = (RecyclerView) view.getParent();
            final int position = recyclerView.getChildLayoutPosition(view);
            if (position != RecyclerView.NO_POSITION)
            {
                final Cursor cursor = this.getItem(position);
                this.onItemClickListener.onItemClicked(cursor);
            }
        }
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder
    {
        /*@Bind(R.id.id)
        TextView textViewName;*/

        public MenuViewHolder(final View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final Cursor cursor)
        {
            final String name = cursor.getString(cursor.getColumnIndex("name"));
            //this.textViewName.setText(name);
        }
    }

    public interface OnItemClickListener
    {
        void onItemClicked(Cursor cursor);
    }
}