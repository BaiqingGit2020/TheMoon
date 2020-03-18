package com.school.baiqing.themoon.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.fbreader.common.FBReaderHelper;
import com.school.baiqing.themoon.ActivityMain;
import com.school.baiqing.themoon.GreenDao.entity.Book;
import com.school.baiqing.themoon.GreenDao.service.BookService;
import com.school.baiqing.themoon.R;
import com.school.baiqing.themoon.ReadActivity;
import com.school.baiqing.themoon.Util.APPCONST;
import com.school.baiqing.themoon.Util.DialogCreator;
import com.school.baiqing.themoon.Util.StringHelper;

import org.geometerplus.android.fbreader.FBReader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zhao on 2017/5/19.
 */

public class BookcaseDragAdapter extends DragAdapter {
    private int mResourceId;
    private List<Book> list;
    private Context mContext;
    private boolean mEditState;
    private BookService mBookService = new BookService();
    private FBReaderHelper fbReaderHelper;
    private ActivityMain mActivityMain;


    public BookcaseDragAdapter(ActivityMain activityMain,Context context, int textViewResourceId, List<Book> objects, boolean editState) {
        mContext = context;
        mResourceId = textViewResourceId;
        list = objects;
        mEditState = editState;
        mActivityMain = activityMain;
        fbReaderHelper = activityMain.getFbReaderHelper();
    }

    @Override
    public void onDataModelMove(int from, int to) {
        Book b = list.remove(from);
        list.add(to, b);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSortCode(i);
        }
        mBookService.updateBooks(list);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Book getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getSortCode();
    }

    public void remove(Book item) {
        list.remove(item);
        notifyDataSetChanged();
        mBookService.deleteBook(item);
    }

    public void add(Book item) {
        list.add(item);
        notifyDataSetChanged();
        mBookService.addBook(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(mResourceId, null);
            viewHolder.ivBookImg = (ImageView) convertView.findViewById(R.id.iv_book_img);
            viewHolder.tvBookName = (TextView) convertView.findViewById(R.id.tv_book_name);
            viewHolder.tvNoReadNum = (TextView) convertView.findViewById(R.id.tv_no_read_num);
            viewHolder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        initView(position, viewHolder);
        Log.i("shelf","position"+position);
        return convertView;
    }

    private void initView(int position, ViewHolder viewHolder) {
        final Book book = getItem(position);
        if (StringHelper.isEmpty(book.getImgUrl())) {
            book.setImgUrl("");
        }
        Uri url = Uri.parse(book.getImgUrl());
        Glide.with(mContext)
                .load(url)
                .error(R.mipmap.no_image)
                .placeholder(R.mipmap.no_image)
                .into(viewHolder.ivBookImg);
        Log.i("shelf","position"+position);
        viewHolder.tvBookName.setText(StringHelper.getFixedTitle(book.getName()));
        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogCreator.createCommonDialog(mContext, "删除书籍", "确定删除《" + book.getName() + "》及其所有缓存吗？",
                        true, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                remove(book);

                                dialogInterface.dismiss();

                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
            }
        });


        if (mEditState) {
            viewHolder.tvNoReadNum.setVisibility(View.GONE);
            viewHolder.ivDelete.setVisibility(View.VISIBLE);
            viewHolder.ivBookImg.setOnClickListener(null);
        } else {
            viewHolder.ivDelete.setVisibility(View.GONE);
            if (book.getNoReadNum() != 0) {
                viewHolder.tvNoReadNum.setVisibility(View.VISIBLE);
                if (book.getNoReadNum() > 99) {
                    viewHolder.tvNoReadNum.setText("...");
                } else {
                    viewHolder.tvNoReadNum.setText(String.valueOf(book.getNoReadNum()));
                }
            } else {
                viewHolder.tvNoReadNum.setVisibility(View.GONE);
            }
            viewHolder.ivBookImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(StringHelper.isEquals(book.getLocation(),APPCONST.BookLocation_local)){
                        org.geometerplus.fbreader.book.Book Book = fbReaderHelper.getCollection().getBookByFile(book.getChapterUrl());
                        FBReader.openBook(mActivityMain, Book, null);
                    }
                    else if(StringHelper.isEquals(book.getLocation(),APPCONST.BookLocation_network)){
                        Intent intent = new Intent( mContext, ReadActivity.class);
                        intent.putExtra(APPCONST.BOOK, book);
                        book.setNoReadNum(0);
                        mBookService.updateEntity(book);
                        mContext.startActivity(intent);
                    }
                }
            });
        }

    }

    public void setmEditState(boolean mEditState) {
        this.mEditState = mEditState;
        notifyDataSetChanged();
    }

    public boolean ismEditState() {
        return mEditState;
    }

    class ViewHolder {
        ImageView ivBookImg;
        TextView tvBookName;
        TextView tvNoReadNum;
        ImageView ivDelete;
    }

}
