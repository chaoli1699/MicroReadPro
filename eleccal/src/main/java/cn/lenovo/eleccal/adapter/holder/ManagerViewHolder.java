package cn.lenovo.eleccal.adapter.holder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.eleccal.R;
import cn.lenovo.eleccal.model.User;

/**
 * Created by Aaron on 2017/1/9.
 */

public class ManagerViewHolder extends BaseViewHolder<User> {

    @Bind(R.id.user_name)
    TextView name;

    public ManagerViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(parent, res);
        ButterKnife.bind(this,itemView);
    }


    @Override
    public void setData(User data) {
        super.setData(data);
        name.setText(data.getName());
    }
}
