package cn.lenovo.eleccal.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.eleccal.R;
import cn.lenovo.eleccal.base.MyApplication;

import static android.R.id.home;

/**
 * Created by Aaron on 2017/9/9.
 */

public class InputActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.toolbar_normal)
    Toolbar toolbar;
    @Bind(R.id.et_input)
    EditText input;
    @Bind(R.id.btn_ok)
    Button ok;

    private int type;
    private MyApplication mApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        ButterKnife.bind(this);

        mApp= (MyApplication) MyApplication.getInstance();
        type=getIntent().getIntExtra("type",0);
        initView();
    }

    private void initView(){
        if (type==0){
            toolbar.setTitle("新增用户");
            input.setHint("输入用户名");
            input.setInputType(InputType.TYPE_CLASS_TEXT);
        }else if (type==1){
            toolbar.setTitle("费用总计");
            input.setHint("输入账单费用");
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else if (type==2){
            toolbar.setTitle("设置");
            input.setHint("输入用户上限(当前为"+mApp.user_max+"户)");
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ok.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        setResult(1);
        finish();
    }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.btn_ok){
            if (input.getText().length()>0){
                Intent intent=new Intent();
                intent.putExtra("result",input.getText().toString());
                setResult(0,intent);
                finish();
            }
        }
    }
}
