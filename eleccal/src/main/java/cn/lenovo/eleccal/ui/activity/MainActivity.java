package cn.lenovo.eleccal.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import butterknife.Bind;
import cn.lenovo.eleccal.R;
import cn.lenovo.eleccal.base.MRActivity;
import cn.lenovo.eleccal.model.User;
import cn.lenovo.eleccal.presenter.MainPresenter;
import cn.lenovo.eleccal.utils.EventUtil;
import cn.lenovo.eleccal.view.MainView;

public class MainActivity extends MRActivity<MainPresenter> implements MainView,View.OnClickListener {

    @Bind(R.id.toolbar_normal)
    Toolbar mToolbar;
    @Bind(R.id.main_last_value)
    AppCompatEditText last_date;
    @Bind(R.id.main_current_value)
    AppCompatEditText current_data;
    @Bind(R.id.main_air_countvalue)
    AppCompatTextView air_count;
    @Bind(R.id.main_air_feevalue)
    AppCompatTextView air_fee;
//    @Bind(R.id.main_total_feevalue)
//    AppCompatEditText total_fee;
    @Bind(R.id.main_submit)
    AppCompatButton submit;
    @Bind(R.id.main_should_payvalue)
    AppCompatTextView should_pay;

    private String lastString;
    private String currentString;
    private String totalString;

    private User user;

    private static final float per_price= (float) 0.55;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        user= (User) getIntent().getSerializableExtra("user");
        mToolbar.setTitle(user.getName());
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lastString=last_date.getText().toString();
        currentString=current_data.getText().toString();
//        totalString=total_fee.getText().toString();

        if (user.getShould_pay()>0){
            last_date.setText(user.getLast_data()+"");
            current_data.setText(user.getCurrent_data()+"");
            air_count.setText(user.getCurrent_data()-user.getLast_data()+"");
            air_fee.setText(user.getAir_fee()+"");
            should_pay.setText(user.getShould_pay()+"元");
        }
        //should_pay.setText(user.getShould_pay()+"元");

        submit.setOnClickListener(this);
        last_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.equals("")&&editable!=null){

                    try {
                        lastString=editable.toString();
                        air_count.setText((Integer.valueOf(currentString)-Integer.valueOf(editable.toString()))+"");
                        String str=(Integer.valueOf(currentString)-Integer.valueOf(editable.toString()))*per_price+"";
                        air_fee.setText(String.format(str,"%.2f"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        current_data.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.equals("")&&editable!=null){

                    try {
                        currentString=editable.toString();
                        air_count.setText((Integer.valueOf(editable.toString())-Integer.valueOf(lastString))+"");
                        String str=(Integer.valueOf(editable.toString())-Integer.valueOf(lastString))*per_price+"";
                        air_fee.setText(String.format(str,"%.2f"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==R.id.main_submit){
            user.setLast_data(Float.valueOf(lastString));
            user.setCurrent_data(Float.valueOf(currentString));
            user.setAir_fee(Float.valueOf(air_fee.getText().toString()));

            mPresenter.submitValues(user);
        }
    }

    @Override
    public void submitSuccess() {
        EventUtil.showToast(this,"提交数据成功");
    }

    @Override
    public void submitFail(String msg) {

    }
}
