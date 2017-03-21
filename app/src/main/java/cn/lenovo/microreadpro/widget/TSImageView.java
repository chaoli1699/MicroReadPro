package cn.lenovo.microreadpro.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import cn.lenovo.microreadpro.R;

/**
 * Created by Aaron on 2016/12/30.
 */

public class TSImageView extends RelativeLayout{

//    @Bind(R.id.tsimageview_image)
    ImageView imageView;
//    @Bind(R.id.tsimageview_title)
    TextView title;

    public TSImageView(Context context) {
        super(context);
        initView(context);
    }

    public TSImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TSImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.item_tsimageview, this);

        imageView = (ImageView) findViewById(R.id.tsimageview_image);
        title = (TextView) findViewById(R.id.tsimageview_title);
    }

    public void setTSImage(Context context,String url){
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(context).load(url).into(imageView);

    }

    public void setTSTitle(String str){
        title.setText(str);
    }

}
