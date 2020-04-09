package com.school.baiqing.themoon.View.BookInfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.school.baiqing.themoon.GreenDao.entity.Chapter;
import com.school.baiqing.themoon.R;

import java.util.List;

public class BookInfoChapterTitleAdapter extends RecyclerView.Adapter<BookInfoChapterTitleAdapter.ChapterViewHolder> {

    private List<Chapter> chapters;
    private Context mContext;

    public BookInfoChapterTitleAdapter(Context context,  List<Chapter> datas){
        chapters = datas;
        this.mContext = context;
    }

    public int getItemCount() {
        return chapters.size();
    }
    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new ChapterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.listview_chapter_title_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        holder.tvTitle.setText("【" + chapters.get(position).getTitle() + "】");
        Log.e("themoonHTTP",chapters.get(position).getTitle());
    }


    class ChapterViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        public ChapterViewHolder(View view){
            super(view);
            tvTitle = view.findViewById(R.id.tv_chapter_title);
        }

    }

}
