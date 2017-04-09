package cn.lenovo.microreadpro.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.holder.ArticalCollectionViewHolder;
import cn.lenovo.microreadpro.model.CArticalBean;
import cn.lenovo.microreadpro.model.MCollection;

/**
 * Created by Aaron on 2016/12/31.
 */

public class ArticalCollectionRecyclerAdapter extends RecyclerArrayAdapter<MCollection.Artical> {

    private Activity context;

    public ArticalCollectionRecyclerAdapter(Activity context) {
        super(context);
        this.context=context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArticalCollectionViewHolder(parent,R.layout.item_collection_artical,context);
    }

}
