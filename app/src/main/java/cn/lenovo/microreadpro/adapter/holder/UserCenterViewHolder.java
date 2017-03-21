package cn.lenovo.microreadpro.adapter.holder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.model.ListBean;

/**
 * Created by Aaron on 2017/1/2.
 */

public class UserCenterViewHolder extends BaseViewHolder<ListBean> {

    @Bind(R.id.item_usercenter_title)
    TextView title;
    @Bind(R.id.item_usercenter_subtitle)
    TextView subtitle;
    @Bind(R.id.item_usercenter_head)
    ImageView head;
    @Bind(R.id.item_usercenter_icon)
    ImageView icon;
    @Bind(R.id.item_usercenter_value)
    TextView value;
    @Bind(R.id.item_usercenter_button)
    TextView button;
    @Bind(R.id.item_usercenter_line)
    View line;

    public UserCenterViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setData(ListBean data) {
//        super.setData(data);

        if (!data.getTitle().equals("")&&!data.isButton()){
            title.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            title.setText(data.getTitle());
        }else {
            itemView.setBackgroundResource(R.color.mdColor_grey_light);
        }

        if (!data.getSubtitle().equals("")){
            subtitle.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
            subtitle.setText(data.getSubtitle());
        }
        if (!data.getImage().equals("")){
            head.setVisibility(View.VISIBLE);
//            Picasso.with(getContext()).load(data.getImage()).into(head);
        }
        if (!data.getValue().equals("")){
            value.setVisibility(View.VISIBLE);
            value.setText(data.getValue());
        }
        if (data.isIconShow()){
            icon.setVisibility(View.VISIBLE);
        }
        if (data.isButton()){
            button.setVisibility(View.VISIBLE);
            button.setText(data.getTitle());
        }

    }
}
