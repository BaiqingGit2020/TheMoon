package com.school.baiqing.themoon.View.Library.SwipeCardView;

import android.support.v7.widget.RecyclerView;

import com.school.baiqing.themoon.GreenDao.entity.Book;

import java.util.List;

/**
 * Created by WangXiandeng on 2016/11/3.
 */

public abstract class SwipeCardAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected List<Book> mList;

    public SwipeCardAdapter(List<Book> list) {
        mList = list;
    }

    /**
     * 删除最顶部Item
     */

    public void delTopItem() {
        int position = getItemCount() - 1;
        mList.remove(position);
        notifyItemRemoved(position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
