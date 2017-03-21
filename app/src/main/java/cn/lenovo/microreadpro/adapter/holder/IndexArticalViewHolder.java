package cn.lenovo.microreadpro.adapter.holder;

import android.content.Context;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

import cn.lenovo.microreadpro.model.ArticalBox;
import cn.lenovo.microreadpro.net.ArticalMWApiStores;
import cn.lenovo.microreadpro.widget.TSImageView;

/**
 * Created by Aaron on 2016/12/30.
 */

public class IndexArticalViewHolder implements Holder<ArticalBox.Artical> {

    private TSImageView tsImageView;

    @Override
    public View createView(Context context) {
        tsImageView=new TSImageView(context);
        return tsImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, ArticalBox.Artical data) {
        tsImageView.setTSImage(context, ArticalMWApiStores.API_MEIWEN_URL+data.getImagePath());
        tsImageView.setTSTitle(data.getTitle());
    }

}
