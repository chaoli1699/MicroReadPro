package cn.lenovo.microreadpro.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.holder.ArticalCollectionViewHolder;
import cn.lenovo.microreadpro.adapter.holder.CommentViewHolder;
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.model.MComment;

/**
 * Created by Aaron on 2016/12/31.
 */

public class CommentRecyclerAdapter extends RecyclerArrayAdapter<MComment.PComment> {

    private Context context;
    private String mom_type;

    public CommentRecyclerAdapter(Context context, String mom_type) {
        super(context);
        this.context=context;
        this.mom_type=mom_type;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentViewHolder(parent,R.layout.item_comment,context,mom_type);
    }

}
