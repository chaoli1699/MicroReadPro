package cn.lenovo.microreadpro.adapter.holder;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.model.CArticalBean;

/**
 * Created by Aaron on 2016/12/31.
 */

public class ArticalCollectionViewHolder extends BaseViewHolder<CArticalBean> {

    @Bind(R.id.item_collection_actical_title)
    TextView itemTitle;
    @Bind(R.id.item_collection_artical_author)
    TextView itemAuthor;
//    @Bind(R.id.item_collection_actical_mark)
//    ImageView mark;
//    @Bind(R.id.item_collection_actical_desc)
//    TextView itemDesc;
    @Bind(R.id.item_collection_actical_coltime)
    TextView itemTime;
//    @Bind(R.id.collection_artical_container)
//    CardView container;

    public ArticalCollectionViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setData(final CArticalBean data) {
//        super.setData(data);
        itemTitle.setText(data.getTitle());
        itemAuthor.setText(data.getAuthor());
//        itemDesc.setText(data.getShortDesc()+"...");
        itemTime.setText(data.getCol_time());
    }
}
