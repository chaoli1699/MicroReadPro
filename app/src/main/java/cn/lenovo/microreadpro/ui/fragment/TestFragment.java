package cn.lenovo.microreadpro.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.lenovo.microreadpro.R;

/**
 * Created by Aaron on 2017/2/11.
 */

public class TestFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        if (rootView==null){
            rootView=inflater.inflate(R.layout.fragment_usercenter_notlogin,null);
        }

        return rootView;
    }
}
