package cn.lenovo.microreadpro.adapter.holder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.MeituArtRecyclerAdapter;
import cn.lenovo.microreadpro.adapter.NormalArticalRecycleAdapter;
import cn.lenovo.microreadpro.base.MyApplication;
import cn.lenovo.microreadpro.model.ArticalBox;
import cn.lenovo.microreadpro.net.ArticalMWApiStores;
import cn.lenovo.microreadpro.ui.activity.ArticalDetailActivity;
import cn.lenovo.microreadpro.widget.TSImageView;

/**
 * Created by Aaron on 2017/2/6.
 */

public class ArticalBoxViewHolder extends BaseViewHolder<ArticalBox> {

    @Bind(R.id.item_artbox_title)
    AppCompatTextView title;
    @Bind(R.id.item_artbox_pic_art_container)
    LinearLayout pic_art_conteiner;
    @Bind(R.id.item_artbox_normal_arts)
    EasyRecyclerView mEasyRecyclerView;
    @Bind(R.id.item_artbox_meitu_arts)
    EasyRecyclerView mMeituEasyRecyclerView;

    private Context context;

    public ArticalBoxViewHolder(ViewGroup parent, @LayoutRes int res, Context context) {
        super(parent, res);
        ButterKnife.bind(this,itemView);
        this.context=context;
    }

    @Override
    public void setData(ArticalBox data) {
        super.setData(data);
        //初始化item
        title.setVisibility(View.GONE);
        pic_art_conteiner.setVisibility(View.GONE);
        mEasyRecyclerView.setVisibility(View.GONE);
        mMeituEasyRecyclerView.setVisibility(View.GONE);

        if (data.getBox_title()!=null){
            title.setVisibility(View.VISIBLE);
            if (data.getBox_path()!=null){
                title.setTextColor(context.getResources().getColor(R.color.mdColor_lightBlue));
                title.setText(data.getBox_title()+">>");
            }else {
                title.setTextColor(context.getResources().getColor(R.color.mdColor_grey_dark));
                title.setText(data.getBox_title());
            }
        }

        final List<ArticalBox.Artical> pic_arts=new ArrayList<>();
        final List<ArticalBox.Artical> meitu_arts=new ArrayList<>();
        final List<ArticalBox.Artical> normal_arts=new ArrayList<>();

        for (int i=0;i<data.getArticals().size();i++){  //文章分类
            ArticalBox.Artical art=data.getArticals().get(i);
            if (art.getImagePath()!=null){
                if (art.getOtherAttr().equals("picArt")){
                    pic_arts.add(art);
                }else if(art.getOtherAttr().equals("meitu")){
                    meitu_arts.add(art);
                }
            }else {
                normal_arts.add(art);
            }
        }

        if (pic_arts.size()>0){

            if (pic_art_conteiner.getChildCount()>0){
                pic_art_conteiner.removeAllViews();
            }

            if (pic_arts.size()<2){  //如果图文数量小于1，则新建图文item
                ArticalBox.Artical art=pic_arts.get(0);
                pic_art_conteiner.setVisibility(View.VISIBLE);
                TSImageView iv=new TSImageView(context);
                iv.setTSImage(context, ArticalMWApiStores.API_MEIWEN_URL+art.getImagePath());
                iv.setTSTitle(art.getTitle());
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buildIntent(pic_arts,0);
                    }
                });
                pic_art_conteiner.addView(iv);
            }else { //如果图文数量大于1，则显示为baner
                for (int i=0;i<pic_arts.size();i++){
                    ArticalBox.Artical art=pic_arts.get(i);
                    pic_art_conteiner.setVisibility(View.VISIBLE);
                    ConvenientBanner convenientBanner=new ConvenientBanner(context);
                    convenientBanner.setMinimumHeight(150);
                    convenientBanner.setPages(new CBViewHolderCreator() {
                        @Override
                        public Object createHolder() {
                            return new IndexArticalViewHolder();
                        }
                    },pic_arts).setDrawingCacheEnabled(true);

                    convenientBanner.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            buildIntent(pic_arts,position);
                        }
                    });

                    final int CHANGE_TIME=5000;
                    if (!convenientBanner.isTurning()){
                        convenientBanner.startTurning(CHANGE_TIME);
                    }
                    pic_art_conteiner.addView(convenientBanner);
                }
            }
        }

        if (meitu_arts.size()>0){  //有图文章  grideview
            mMeituEasyRecyclerView.setVisibility(View.VISIBLE);
            GridLayoutManager mGridLayoutManager=new GridLayoutManager(context,3);
            MeituArtRecyclerAdapter meituArtRecyclerAdapter=new MeituArtRecyclerAdapter(context);
            mMeituEasyRecyclerView.setLayoutManager(mGridLayoutManager);
            mMeituEasyRecyclerView.setAdapter(meituArtRecyclerAdapter);
            meituArtRecyclerAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    buildIntent(meitu_arts,position);
                }
            });
            meituArtRecyclerAdapter.addAll(meitu_arts);
        }

        if (normal_arts.size()>0){  //无图文章 listview
            mEasyRecyclerView.setVisibility(View.VISIBLE);
            NormalArticalRecycleAdapter mNormalArticalRecycleAdapter=new NormalArticalRecycleAdapter(context);
            LinearLayoutManager mLinearLayoutManager=new LinearLayoutManager(context);
            mEasyRecyclerView.setLayoutManager(mLinearLayoutManager);
            mEasyRecyclerView.setAdapter(mNormalArticalRecycleAdapter);
            mNormalArticalRecycleAdapter.addAll(normal_arts);

            mNormalArticalRecycleAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    buildIntent(normal_arts,position);
                }
            });
        }

    }

    private void buildIntent(List<ArticalBox.Artical> arts,int position){
        Intent intent=new Intent(context, ArticalDetailActivity.class);
        Bundle b=new Bundle();
        b.putSerializable("artical",arts.get(position));
        intent.putExtras(b);
        context.startActivity(intent);
    }
}
