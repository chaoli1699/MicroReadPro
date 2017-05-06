package cn.lenovo.microreadpro.adapter.holder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.model.MMessage;
import cn.lenovo.microreadpro.net.MicroReadApiStores;

/**
 * Created by Aaron on 2016/12/31.
 */

public class MessageViewHolder extends BaseViewHolder<MMessage.Message> {

    @Bind(R.id.item_message_head)
    ImageView head;
    @Bind(R.id.item_message_username)
    TextView username;
    @Bind(R.id.item_message_comment)
    TextView comment;
    @Bind(R.id.item_message_time)
    TextView time;
    @Bind(R.id.item_message_readOrNo)
    TextView readOrNo;
    @Bind(R.id.item_message_container)
    RelativeLayout container;

    private Context context;

    public MessageViewHolder(ViewGroup parent, @LayoutRes int res, Context context) {
        super(parent, res);
        ButterKnife.bind(this,itemView);
        this.context=context;
    }

    @Override
    public void setData(final MMessage.Message data) {
//        super.setData(data);
        container.setBackgroundResource(R.color.white);
        readOrNo.setText("未读");
        readOrNo.setTextColor(context.getResources().getColor(R.color.mdColor_green));

        if (data.getStatus()==0){
//            container.setBackgroundResource(R.color.mdColor_grey_light);
            readOrNo.setText("已读");
            readOrNo.setTextColor(context.getResources().getColor(R.color.mdColor_grey));
        }

        Glide.with(context).load(MicroReadApiStores.API_MICROREAD_URL+data.getHead_path()).into(head);
        username.setText(data.getUsername());
        comment.setText(data.getComment());
        time.setText(data.getTime_to_now());

    }
}
