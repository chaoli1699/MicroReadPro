package cn.lenovo.microreadpro.adapter.holder;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.ActionBarOverlayLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mcxiaoke.bus.Bus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.ChildcomRecyclerAdapter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.MCollection;
import cn.lenovo.microreadpro.model.MComment;
import cn.lenovo.microreadpro.widget.HintPopupWindow;

/**
 * Created by Aaron on 2016/12/31.
 */

public class CommentViewHolder extends BaseViewHolder<MComment.PComment> {

    @Bind(R.id.item_comment_username)
    TextView username;
    @Bind(R.id.item_comment_content)
    TextView content;
    @Bind(R.id.item_comment_head)
    ImageView head;
    @Bind(R.id.item_comment_time)
    TextView time;
    @butterknife.Bind(R.id.item_comment_remove)
    TextView remove;
    @Bind(R.id.btn_comment_response)
    Button response;
    @Bind(R.id.item_comment_childcom)
    EasyRecyclerView childcom;

    private Context context;
    private MyApplication mApp;
    private String mom_type;

    public CommentViewHolder(ViewGroup parent, @LayoutRes int res, Context context, String mom_type) {
        super(parent, res);
        ButterKnife.bind(this,itemView);
        this.context=context;
        this.mom_type=mom_type;
        mApp= (MyApplication) MyApplication.getInstance();
    }

    @Override
    public void setData(final MComment.PComment data) {
//        super.setData(data);
        username.setText(data.getUsername());
        content.setText(data.getComment());
        time.setText(data.getTime_to_now());
        remove.setVisibility(View.GONE);
        response.setVisibility(View.GONE);

        if (!mom_type.equals("trash")&&mApp.currentUser!=null){
            if (data.getUid()==mApp.currentUser.getUid()||mApp.currentUser.getRole()>5){
                remove.setVisibility(View.VISIBLE);
            }
        }

        if (mom_type.equals("moment")||mom_type.equals("comment")){
            response.setVisibility(View.VISIBLE);
        }

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bus.getDefault().post("mmtot,"+data.getAcid());
            }
        });

        response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bus.getDefault().post("addc,"+data.getAcid()+","+data.getUid());
            }
        });

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        ChildcomRecyclerAdapter childcomRecyclerAdapter=new ChildcomRecyclerAdapter(context);
        childcom.setLayoutManager(linearLayoutManager);
        childcom.setAdapter(childcomRecyclerAdapter);
        childcomRecyclerAdapter.addAll(data.getChild_com());

    }
}
