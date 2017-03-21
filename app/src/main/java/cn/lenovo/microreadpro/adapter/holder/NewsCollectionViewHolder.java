package cn.lenovo.microreadpro.adapter.holder;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.model.CStoriedBean;

/**
 * Created by Aaron on 2016/12/31.
 */

public class NewsCollectionViewHolder extends BaseViewHolder<CStoriedBean> {

    @Bind(R.id.item_collection_title)
    TextView itemNewsTitle;
    @Bind(R.id.item_collection_img)
    ImageView itemNewsImg;
//    @Bind(R.id.item_collection_mark)
//    ImageView mark;
//    @Bind(R.id.collection_container)
//    CardView container;

    public NewsCollectionViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setData(final CStoriedBean data) {
//        super.setData(data);
        Glide.with(getContext()).load(data.getImages().get(0)).into(itemNewsImg);
        itemNewsTitle.setText(data.getTitle());

//        mark.setVisibility(View.GONE);
//
//        if (SystermParams.COLLECTION_STATUS.equals("write")){
//            container.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (mark.getVisibility()==View.GONE){
//                        mark.setVisibility(View.VISIBLE);
////                        container.setBackgroundResource(R.color.mdColor_grey);
//                        SystermParams.removeStoriesBeans.add(data);
//                    }else if (mark.getVisibility()==View.VISIBLE){
//                        mark.setVisibility(View.GONE);
////                        container.setBackgroundResource(R.color.white);
//                        if (SystermParams.removeStoriesBeans!=null&&SystermParams.removeStoriesBeans.size()>0){
//                            for (int i=0;i<SystermParams.removeStoriesBeans.size();i++){
//                                NewsEntity.StoriesBean s=SystermParams.removeStoriesBeans.get(i);
//                                if (s.getId()==data.getId()){
//                                    SystermParams.removeStoriesBeans.remove(i);
//                                }
//                            }
//                        }
//                    }
//                }
//            });
//        }else if (SystermParams.COLLECTION_STATUS.equals("read")){
//            container.clearFocus();
//        }
    }
}
