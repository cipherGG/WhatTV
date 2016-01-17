package com.gg.whattv.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * author cipherGG
 * Created by Administrator on 2015/12/26.
 * describe
 */
public class PicassoUtils {

    public static void loadImage(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url).into(imageView);
    }

}
