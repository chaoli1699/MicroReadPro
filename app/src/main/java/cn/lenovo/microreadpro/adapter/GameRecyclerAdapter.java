package cn.lenovo.microreadpro.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.holder.GameViewHolder;
import cn.lenovo.microreadpro.model.GameBean;

/**
 * Created by Aaron on 2016/12/31.
 */

public class GameRecyclerAdapter extends RecyclerArrayAdapter<GameBean> {

    public GameRecyclerAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new GameViewHolder(parent, R.layout.item_game);
    }

}
