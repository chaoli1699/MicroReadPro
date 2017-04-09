package cn.lenovo.microreadpro.adapter.holder;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mcxiaoke.bus.Bus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.model.CStoriedBean;
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.widget.HintPopupWindow;

/**
 * Created by Aaron on 2016/12/31.
 */

public class NewsCollectionViewHolder extends BaseViewHolder<MCollection.Artical> {

    @Bind(R.id.item_collection_title)
    TextView itemNewsTitle;
    @Bind(R.id.item_collection_img)
    ImageView itemNewsImg;
    @Bind(R.id.item_collection_more)
    ImageView more;
//    @Bind(R.id.collection_container)
//    CardView container;
    private Activity context;

    public NewsCollectionViewHolder(ViewGroup parent, @LayoutRes int res,Activity context) {
        super(parent, res);
        ButterKnife.bind(this,itemView);
        this.context=context;
    }

    @Override
    public void setData(final MCollection.Artical data) {
//        super.setData(data);
        Glide.with(getContext()).load(data.getImage_path()).into(itemNewsImg);
        itemNewsTitle.setText(data.getTitle());

        final HintPopupWindow popupWindow=new HintPopupWindow(context);

        List<String> strList=new ArrayList<>();
        strList.add("删除");
        List<View.OnClickListener> clickList=new ArrayList<>();
        clickList.add(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String artJson=new Gson().toJson(data);
                Bus.getDefault().post("removeN:"+artJson);
                popupWindow.dismissPopupWindow();
            }
        });

        popupWindow.setActionList(strList,clickList);

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.showPopupWindow(view);
            }
        });
    }
}
