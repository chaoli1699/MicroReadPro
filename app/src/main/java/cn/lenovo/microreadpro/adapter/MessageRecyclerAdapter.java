package cn.lenovo.microreadpro.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.holder.CommentViewHolder;
import cn.lenovo.microreadpro.adapter.holder.MessageViewHolder;
import cn.lenovo.microreadpro.model.MComment;
import cn.lenovo.microreadpro.model.MMessage;

/**
 * Created by Aaron on 2016/12/31.
 */

public class MessageRecyclerAdapter extends RecyclerArrayAdapter<MMessage.Message> {

    private Context context;

    public MessageRecyclerAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(parent,R.layout.item_message, context);
    }

}
