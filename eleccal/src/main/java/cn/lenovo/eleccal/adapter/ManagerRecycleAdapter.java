package cn.lenovo.eleccal.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import cn.lenovo.eleccal.R;
import cn.lenovo.eleccal.adapter.holder.ManagerViewHolder;
import cn.lenovo.eleccal.model.User;

/**
 * Created by Aaron on 2017/1/9.
 */

public class ManagerRecycleAdapter extends RecyclerArrayAdapter<User> {

    public ManagerRecycleAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ManagerViewHolder( parent, R.layout.item_user_recycle);
    }
}
