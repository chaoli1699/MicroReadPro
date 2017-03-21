package cn.lenovo.microreadpro.adapter.holder;

import android.content.Context;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.model.NewsEntity;
import cn.lenovo.microreadpro.widget.TSImageView;

/**
 * Created by Aaron on 2016/12/30.
 */

public class TopStoriesViewHolder implements Holder<NewsEntity.TopStoriesBean> {

    private TSImageView tsImageView;

    @Override
    public View createView(Context context) {
        tsImageView=new TSImageView(context);
        return tsImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, NewsEntity.TopStoriesBean data) {
        tsImageView.setTSImage(context,data.getImage());
        tsImageView.setTSTitle(context.getResources().getString(R.string.top_stories)+data.getTitle());
    }

}
