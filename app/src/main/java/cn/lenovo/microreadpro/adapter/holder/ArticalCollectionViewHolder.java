package cn.lenovo.microreadpro.adapter.holder;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mcxiaoke.bus.Bus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.CArticalBean;
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.widget.HintPopupWindow;

/**
 * Created by Aaron on 2016/12/31.
 */

public class ArticalCollectionViewHolder extends BaseViewHolder<MCollection.Artical> {

    @Bind(R.id.item_collection_actical_title)
    TextView itemTitle;
    @Bind(R.id.item_collection_artical_author)
    TextView itemAuthor;
    @Bind(R.id.item_collection_actical_more)
    ImageView more;
//    @Bind(R.id.item_collection_actical_desc)
//    TextView itemDesc;
//    @Bind(R.id.item_collection_actical_coltime)
//    TextView itemTime;
//    @Bind(R.id.collection_artical_container)
//    CardView container;
    private Activity context;

    public ArticalCollectionViewHolder(ViewGroup parent, @LayoutRes int res,Activity context) {
        super(parent, res);
        ButterKnife.bind(this,itemView);
        this.context=context;
    }

    @Override
    public void setData(final MCollection.Artical data) {
//        super.setData(data);
        itemTitle.setText(data.getTitle());
        itemAuthor.setText(data.getAuthor());
//        itemDesc.setText(data.getShortDesc()+"...");
//        itemTime.setText(data.getCol_time());

        final HintPopupWindow popupWindow=new HintPopupWindow(context);

        List<String> strList=new ArrayList<>();
        strList.add("删除");
        List<View.OnClickListener> clickList=new ArrayList<>();
        clickList.add(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String artJson=new Gson().toJson(data);
                Bus.getDefault().post("removeA:"+artJson);
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
