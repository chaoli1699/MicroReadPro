package cn.lenovo.microreadpro.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mcxiaoke.bus.Bus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.CollectionFragmentPageAdapter;
import cn.lenovo.microreadpro.base.MRActivity;
import cn.lenovo.microreadpro.presenter.CollectionContainerPresenter;
import cn.lenovo.microreadpro.ui.fragment.ArticalCollectionFragment;
import cn.lenovo.microreadpro.ui.fragment.NewsCollectionFragment;
import cn.lenovo.microreadpro.utils.EventUtil;
import cn.lenovo.microreadpro.view.CollectionContainerView;

/**
 * Created by Aaron on 2017/2/11.
 */

public class CollectionActivity extends MRActivity<CollectionContainerPresenter> implements CollectionContainerView {

    @Bind(R.id.toolbar_for_c_u)
    Toolbar toolbar;
    @Bind(R.id.fragment_collection_tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.fragment_collection_viewPager)
    ViewPager mViewPager;

    private List<Fragment> fragmentList=new ArrayList<>();
    private List<String> tabList=new ArrayList<>();
    private CollectionFragmentPageAdapter mCollectionFragmentPageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_collection_container);

        initView();
    }

    @Override
    protected CollectionContainerPresenter createPresenter() {
        return new CollectionContainerPresenter(this);
    }

    private void initView() {

        toolbar.setTitle(getResources().getString(R.string.my_collection));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabList.add("热门话题");
//        tabList.add("精美文章");

        Fragment newsCollectionFragment = new NewsCollectionFragment();
//        Fragment articalCollectionFragment = new ArticalCollectionFragment();
        fragmentList.add(newsCollectionFragment);
//        fragmentList.add(articalCollectionFragment);

//        fragmentList.add(new TestFragment());

        if (tabList.size()<2){
            mTabLayout.setVisibility(View.GONE);
        }

        mCollectionFragmentPageAdapter = new CollectionFragmentPageAdapter(getSupportFragmentManager(), tabList, fragmentList);
        mViewPager.setAdapter(mCollectionFragmentPageAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mCollectionFragmentPageAdapter);

//        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                SystermParams.removeStoriesBeans.clear();
//                SystermParams.removeArticalBeans.clear();
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
////        return super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.activity_comment_menu,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId()==R.id.edit){
//            if (item.getTitle().equals(getResources().getString(R.string.edit))){
//                SystermParams.COLLECTION_STATUS="write";
//                item.setTitle(getResources().getString(R.string.done));
//
//                SystermParams.removeStoriesBeans.clear();
//                SystermParams.removeArticalBeans.clear();
//
//            }else if (item.getTitle().equals(getResources().getString(R.string.done))){
//
//                if (SystermParams.removeStoriesBeans.size()>0){
//                    mPresenter.removeStorieBeans(SystermParams.removeStoriesBeans);
//                }
//
//                if (SystermParams.removeArticalBeans.size()>0){
//                    mPresenter.removeArticalBeans(SystermParams.removeArticalBeans);
//                }
//
//                SystermParams.COLLECTION_STATUS="read";
//                item.setTitle(getResources().getString(R.string.edit));
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void removeNewsCollectionSuccess() {
        EventUtil.showToast(this,"已为您移除所选收藏！");
        Bus.getDefault().post("news_removed");
    }

    @Override
    public void removeArticalCollectionSuccess() {
        EventUtil.showToast(this,"已为您移除所选收藏！");
        Bus.getDefault().post("artical_removed");

    }

    @Override
    protected void onResume() {
        super.onResume();
//        initView();
    }
}
