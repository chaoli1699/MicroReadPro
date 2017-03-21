package cn.lenovo.microreadpro.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.adapter.GameRecyclerAdapter;
import cn.lenovo.microreadpro.base.MRFragment;
import cn.lenovo.microreadpro.model.GameBean;
import cn.lenovo.microreadpro.presenter.GamePresenter;
import cn.lenovo.microreadpro.ui.activity.GameDetailActivity;
import cn.lenovo.microreadpro.view.GameView;

/**
 * Created by Aaron on 2017/2/26.
 */

public class GameFragment extends MRFragment<GamePresenter> implements GameView {

    @Bind(R.id.toolbar_for_c_u)
    Toolbar toolbar;
    @Bind(R.id.fg_game_recycleView)
    EasyRecyclerView mEasyRecyclerView;

    private View rootView;
    private GridLayoutManager mGridLayoutManager;
    private GameRecyclerAdapter mGameRecyclerAdapter;
    private List<GameBean> gameBeanList;

    private static DrawerLayout drawer;

    public interface GameFragmenExtra{
        DrawerLayout getDrawer();
    }

    public static void setDrawer(GameFragmenExtra gameFragmenExtra){
        if (drawer==null){
            drawer=gameFragmenExtra.getDrawer();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        if (rootView==null){
            rootView=inflater.inflate(R.layout.fragment_game,container,false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView(){

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        toolbar.setTitle(getString(R.string.game));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mGridLayoutManager=new GridLayoutManager(getActivity(),3);
        mEasyRecyclerView.setLayoutManager(mGridLayoutManager);
        mGameRecyclerAdapter =new GameRecyclerAdapter(getActivity());
        mEasyRecyclerView.setAdapter(mGameRecyclerAdapter);

//        mNewsCollectionRecyclerAdapter.setMore(R.layout.view_more,this);
        mGameRecyclerAdapter.setError(R.layout.view_error);
//        mRecyclerView.setRefreshListener(this);
        mEasyRecyclerView.setEmptyView(R.layout.view_empty);
        mGameRecyclerAdapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getActivity(), GameDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("game",gameBeanList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mPresenter.getGames();

    }

    @Override
    protected GamePresenter createPresenter() {
        return new GamePresenter(this);
    }

    @Override
    public void getGamesSuccess(List<GameBean> games) {
        gameBeanList=games;
        mGameRecyclerAdapter.addAll(games);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("GameFragment");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MobclickAgent.onPageEnd("GameFragment");
    }
}
