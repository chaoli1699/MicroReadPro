package cn.lenovo.microreadpro.adapter.holder;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mcxiaoke.bus.Bus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.model.MComment;
import cn.lenovo.microreadpro.widget.HintPopupWindow;

/**
 * Created by Aaron on 2016/12/31.
 */

public class CommentViewHolder extends BaseViewHolder<MComment.Comment> {

    @Bind(R.id.item_comment_username)
    TextView username;
    @Bind(R.id.item_comment_content)
    TextView content;
    @Bind(R.id.item_comment_head)
    ImageView head;

    public CommentViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this,itemView);
    }

    @Override
    public void setData(final MComment.Comment data) {
//        super.setData(data);
        username.setText(data.getUsername());
        content.setText(data.getComment());

    }
}
