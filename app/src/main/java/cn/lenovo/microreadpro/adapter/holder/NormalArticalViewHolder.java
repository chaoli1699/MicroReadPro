package cn.lenovo.microreadpro.adapter.holder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.model.ArticalBox;

/**
 * Created by Aaron on 2017/2/6.
 */

public class NormalArticalViewHolder extends BaseViewHolder<ArticalBox.Artical> {

    @Bind(R.id.item_normal_artical_title)
    TextView title;
    @Bind(R.id.item_normal_artical_date)
    TextView date;

    public NormalArticalViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setData(ArticalBox.Artical data) {
        super.setData(data);
        if (data.getLabel()!=null){
            title.setText("["+data.getLabel()+"]"+data.getTitle());
        }else {
            title.setText(data.getTitle());
        }

        if (data.getOtherAttr().equals("withDate")){
            date.setText(data.getPublishDate());
        }
    }
}
