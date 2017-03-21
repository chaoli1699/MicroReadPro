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
import cn.lenovo.microreadpro.model.NewsEntity;

/**
 * Created by lenovo on 2016/11/30.
 */

public class NewsViewHolder extends BaseViewHolder<NewsEntity.StoriesBean> {

    @Bind(R.id.item_news_title)
    TextView itemNewsTitle;
    @Bind(R.id.item_news_img)
    ImageView itemNewsImg;
//    @Bind(R.id.item_news_type)
//    TextView type;

    public NewsViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setData(NewsEntity.StoriesBean data) {
//        super.setData(data);
        Glide.with(getContext()).load(data.getImages().get(0)).into(itemNewsImg);
        itemNewsTitle.setText(data.getTitle());
//        type.setText(data.getType()+"");
    }
}
