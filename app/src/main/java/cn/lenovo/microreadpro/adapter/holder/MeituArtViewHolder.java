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
import cn.lenovo.microreadpro.model.ArticalBox;
import cn.lenovo.microreadpro.net.ArticalMWApiStores;

/**
 * Created by Aaron on 2016/12/31.
 */

public class MeituArtViewHolder extends BaseViewHolder<ArticalBox.Artical> {

    @Bind(R.id.item_meitu_art_title)
    TextView itemMeituTitle;
    @Bind(R.id.item_meitu_art_img)
    ImageView itemMeituImg;

    public MeituArtViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setData(final ArticalBox.Artical data) {
//        super.setData(data);
        Glide.with(getContext()).load(ArticalMWApiStores.API_MEIWEN_URL+data.getImagePath()).into(itemMeituImg);
        itemMeituTitle.setText(data.getTitle());
    }
}
