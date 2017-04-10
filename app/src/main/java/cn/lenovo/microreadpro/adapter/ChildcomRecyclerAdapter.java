package cn.lenovo.microreadpro.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.holder.ChildcomViewHolder;
import cn.lenovo.microreadpro.adapter.holder.CommentViewHolder;
import cn.lenovo.microreadpro.model.MComment;

/**
 * Created by Aaron on 2016/12/31.
 */

public class ChildcomRecyclerAdapter extends RecyclerArrayAdapter<MComment.CComment> {

    public ChildcomRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChildcomViewHolder(parent,R.layout.item_childcom);
    }

}
