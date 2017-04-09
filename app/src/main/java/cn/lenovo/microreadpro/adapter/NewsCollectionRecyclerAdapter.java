package cn.lenovo.microreadpro.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.holder.NewsCollectionViewHolder;
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.model.NewsEntity;

/**
 * Created by Aaron on 2016/12/31.
 */

public class NewsCollectionRecyclerAdapter extends RecyclerArrayAdapter<MCollection.Artical> {

    private Activity context;

    public NewsCollectionRecyclerAdapter(Activity context) {
        super(context);
        this.context=context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NewsCollectionViewHolder(parent, R.layout.item_collection_news,context);
    }

}
