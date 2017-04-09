package cn.lenovo.microreadpro.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.lenovo.microreadpro.R;
import cn.lenovo.microreadpro.base.BaseActivity;
import cn.lenovo.microreadpro.base.MyApplication;

public class AboutAppActivity extends BaseActivity {

    @Bind(R.id.activity_about_app_version)
    AppCompatTextView version;
    @Bind(R.id.activity_about_app_content)
    AppCompatTextView content;
    private MyApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        ButterKnife.bind(this);

        mApp= (MyApplication) MyApplication.getInstance();
        try {
            InputStream is=getAssets().open("aim.txt");
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            String text = new String(buffer, "GB2312");

            version.setText(mApp.getVersionName());
            content.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
