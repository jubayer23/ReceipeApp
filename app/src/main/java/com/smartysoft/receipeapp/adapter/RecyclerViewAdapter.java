package com.smartysoft.receipeapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.smartysoft.receipeapp.R;
import com.smartysoft.receipeapp.model.Recepie;

import java.util.List;

public class RecyclerViewAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int GRID = 0;
    public static final int LIST = 1;
    private int listStyle = 0;
    private Context mContext;
    private List<Recepie> moviesList;

    public RecyclerViewAdapter(List<Recepie> paramList, Context paramContext) {
        this.moviesList = paramList;
        this.mContext = paramContext;
    }

    public int getItemCount() {
        return this.moviesList.size();
    }

    @Override
    public int getItemViewType(int paramInt) {
        return this.listStyle;
    }


    public void onBindViewHolder(RecyclerView.ViewHolder paramViewHolder, int paramInt) {
        Recepie localHeavyMovie =  this.moviesList.get(paramInt);



        if (paramViewHolder.getItemViewType() == 0) {
            ((GridViewHolder) paramViewHolder).tv_title.setText(localHeavyMovie.getTitle());
            ((GridViewHolder) paramViewHolder).img_cover.setImageURI(localHeavyMovie.getImage());
            return;
        }
        ((ListViewHolder) paramViewHolder).tv_title.setText(localHeavyMovie.getTitle());
        ((ListViewHolder) paramViewHolder).img_cover.setImageURI(localHeavyMovie.getImage());
        if(localHeavyMovie.getSubTitle() != null && !localHeavyMovie.getSubTitle().isEmpty()){
            ((ListViewHolder) paramViewHolder).tv_sub_title.setText(localHeavyMovie.getSubTitle());
        }else{
            ((ListViewHolder) paramViewHolder).tv_sub_title.setText("No genre");
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt) {
        if (paramInt == 0) {
            return new GridViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.row_grid_item, null));
        }
       return new ListViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.row_list_item, null));
    }

    public void removeAllAndNotify() {
        int i = this.moviesList.size();
        this.moviesList.clear();
        notifyItemRangeRemoved(0, i);
    }

    public void setListStyle(int paramInt) {
        this.listStyle = paramInt;
    }

    public class GridViewHolder
            extends RecyclerView.ViewHolder {
        public SimpleDraweeView img_cover;
        public TextView tv_title;

        public GridViewHolder(View paramView) {
            super(paramView);
            this.tv_title = ((TextView) paramView.findViewById(R.id.tv_title));
            this.img_cover = ((SimpleDraweeView) paramView.findViewById(R.id.img_cover));
        }
    }

    public class ListViewHolder
            extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public SimpleDraweeView img_cover;
        public TextView tv_sub_title;

        public ListViewHolder(View paramView) {
            super(paramView);
            this.tv_title = ((TextView) paramView.findViewById(R.id.tv_title));
            this.img_cover = ((SimpleDraweeView) paramView.findViewById(R.id.img_cover));
            this.tv_sub_title = ((TextView) paramView.findViewById(R.id.tv_sub_title));
        }
    }
}


/* Location:           E:\APK\dex2jar\classes-dex2jar.jar
 * Qualified Name:     vinitm.yts.Views.InfiniteScrollRecyclerView.Adapter.RecyclerViewAdapter
 * JD-Core Version:    0.7.0.1
 */