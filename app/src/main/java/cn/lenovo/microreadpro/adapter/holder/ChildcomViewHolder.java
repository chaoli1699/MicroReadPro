package cn.lenovo.microreadpro.adapter.holder;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.model.MComment;

/**
 * Created by Aaron on 2016/12/31.
 */

public class ChildcomViewHolder extends BaseViewHolder<MComment.CComment> {

    @Bind(R.id.item_childcom_username)
    TextView username;
    @Bind(R.id.item_childcom_content)
    TextView content;
    @Bind(R.id.item_childcom_time)
    TextView time;

    public ChildcomViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setData(final MComment.CComment data) {
//        super.setData(data);
        username.setText(data.getUsername());
        content.setText(data.getComment());
        time.setText(data.getTime_to_now());
    }
}
