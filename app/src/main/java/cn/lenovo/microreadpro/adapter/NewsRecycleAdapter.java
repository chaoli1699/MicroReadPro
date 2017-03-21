package cn.lenovo.microreadpro.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.holder.NewsViewHolder;
import cn.lenovo.microreadpro.model.NewsEntity;

/**
 * Created by lenovo on 2016/11/30.
 */

public class NewsRecycleAdapter extends RecyclerArrayAdapter<NewsEntity.StoriesBean> {

    public NewsRecycleAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsViewHolder(parent, R.layout.item_news);
    }
}
