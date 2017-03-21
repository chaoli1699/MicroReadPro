package cn.lenovo.microreadpro.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.holder.NormalArticalViewHolder;
import cn.lenovo.microreadpro.model.ArticalBox;

/**
 * Created by Aaron on 2017/2/6.
 */

public class NormalArticalRecycleAdapter extends RecyclerArrayAdapter<ArticalBox.Artical> {

    public NormalArticalRecycleAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalArticalViewHolder(parent, R.layout.item_normal_artical);
    }
}
