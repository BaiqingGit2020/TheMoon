package com.school.baiqing.themoon.View.SwipeCard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.school.baiqing.themoon.GreenDao.entity.Book;
import com.school.baiqing.themoon.R;

import java.util.List;

public class RecommendAdapter extends SwipeCardAdapter <RecommendAdapter.MyHolder>{
    private Context mContext;

    public RecommendAdapter(Context context, List<Book> list) {
        super(list);
        mContext = context;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.swipecard_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Glide.with(mContext)
                .load(mList.get(position).getImgUrl())
                .apply(new RequestOptions()
                .placeholder(R.mipmap.no_image)
                .error(R.mipmap.no_image))
                .into(holder.recomCover);
        holder.recomTitle.setText(mList.get(position).getName());
        holder.recomDesc.setText(mList.get(position).getDesc());
        holder.recomWriter.setText(mList.get(position).getAuthor());
    }


    class MyHolder extends RecyclerView.ViewHolder {
        TextView recomTitle,recomWriter,recomDesc;
        ImageView recomCover;
        public MyHolder(View itemView) {
            super(itemView);
            recomTitle = (TextView) itemView.findViewById(R.id.recommend_book_title);
            recomWriter = (TextView) itemView.findViewById(R.id.recommend_book_writer);
            recomDesc = (TextView) itemView.findViewById(R.id.recommend_book_describe);
            recomCover = (ImageView) itemView.findViewById(R.id.recommend_book_img);
        }

    }
}
