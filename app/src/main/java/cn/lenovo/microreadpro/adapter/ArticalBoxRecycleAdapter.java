package cn.lenovo.microreadpro.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.holder.ArticalBoxViewHolder;
import cn.lenovo.microreadpro.model.ArticalBox;

/**
 * Created by Aaron on 2017/2/6.
 */

public class ArticalBoxRecycleAdapter extends RecyclerArrayAdapter<ArticalBox> {

    private Context context;
    public ArticalBoxRecycleAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArticalBoxViewHolder(parent, R.layout.item_artical_box,context);
    }
}
