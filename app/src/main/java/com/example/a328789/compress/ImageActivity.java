package com.example.a328789.compress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.a328789.compress.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 328789 on 2016/9/17.
 */
public class ImageActivity extends AppCompatActivity {
    @BindView(R.id.des_image)
    ImageView des_image;
    @BindView(R.id.src_image)
    ImageView src_iamge;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_image);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        src_iamge.setImageResource(R.mipmap.deppon);
        Bitmap bitmap = Utils.convertToBlackWhite(BitmapFactory.decodeResource(getResources(), R.mipmap.deppon),500,500);
        des_image.setImageBitmap(bitmap);
    }
}
