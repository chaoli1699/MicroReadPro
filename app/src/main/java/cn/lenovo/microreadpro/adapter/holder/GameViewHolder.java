package cn.lenovo.microreadpro.adapter.holder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.model.GameBean;

/**
 * Created by Aaron on 2016/12/31.
 */

public class GameViewHolder extends BaseViewHolder<GameBean> {

    @Bind(R.id.item_game_name)
    TextView itemGameName;
    @Bind(R.id.item_game_img)
    ImageView itemGameImg;

    public GameViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setData(final GameBean data) {
//        super.setData(data);
       Glide.with(getContext()).load(data.getImg_path()).into(itemGameImg);
        itemGameName.setText(data.getName());
    }
}
