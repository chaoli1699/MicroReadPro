package cn.lenovo.microreadpro.adapter.holder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.model.MUFeture;
import cn.lenovo.microreadpro.net.MicroReadApiStores;

/**
 * Created by Aaron on 2017/1/2.
 */

public class UserCenterViewHolder extends BaseViewHolder<MUFeture.UFeture> {

    @Bind(R.id.item_usercenter_group)
    TextView group;
    @Bind(R.id.item_usercenter_head)
    ImageView head;
    @Bind(R.id.item_usercenter_icon)
    ImageView icon;
    @Bind(R.id.item_usercenter_title)
    TextView title;
    @Bind(R.id.item_usercenter_subtitle)
    TextView subtitle;
    @Bind(R.id.item_usercenter_value)
    TextView value;
    @Bind(R.id.item_usercenter_rrow)
    ImageView rrow;
    @Bind(R.id.item_usercenter_button)
    TextView button;
    @Bind(R.id.item_usercenter_line)
    TextView line;

    public UserCenterViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setData(MUFeture.UFeture data) {
//        super.setData(data);
        group.setVisibility(View.GONE);
        head.setVisibility(View.GONE);
        icon.setVisibility(View.GONE);
        subtitle.setVisibility(View.GONE);
        value.setVisibility(View.GONE);
        rrow.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        line.setVisibility(View.VISIBLE);

        if (data.getGroup_top()==1){
            group.setVisibility(View.VISIBLE);
//            group.setText(data.getGroup());
        }else if (data.getGroup_top()==-1){
            group.setVisibility(View.GONE);
        }
        if (!data.getHead_path().equals("")){
            head.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(MicroReadApiStores.API_MICROREAD_URL+data.getHead_path()).into(head);
        }
        if (!data.getIcon_path().equals("")){
            icon.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(MicroReadApiStores.API_MICROREAD_URL+data.getIcon_path()).into(icon);
        }
        if (!data.getUsername().equals("")){
            subtitle.setVisibility(View.VISIBLE);
            subtitle.setText(data.getUsername());
        }
        if (data.getValue()!=null){
            value.setVisibility(View.VISIBLE);
            value.setText(data.getValue());
        }
        if (data.getShow_rrow()==1){
            rrow.setVisibility(View.VISIBLE);
        }
        if (data.getIs_button()==1){
            button.setVisibility(View.VISIBLE);
            button.setText("退出登录");
        }

        title.setText(data.getName_cn());
    }
}
