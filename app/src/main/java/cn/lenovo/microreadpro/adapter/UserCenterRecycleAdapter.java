package cn.lenovo.microreadpro.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.holder.UserCenterViewHolder;
import cn.lenovo.microreadpro.model.ListBean;
import cn.lenovo.microreadpro.model.MUFeture;

/**
 * Created by Aaron on 2017/1/2.
 */

public class UserCenterRecycleAdapter extends RecyclerArrayAdapter<MUFeture.UFeture>{

    public UserCenterRecycleAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserCenterViewHolder(parent,R.layout.item_mfetures);
    }
}
