package cn.fengxing.com.mcppictureselect.engine.impl;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import cn.fengxing.com.mcppictureselect.engine.ImageEngine;

/**
 * Created by fengxing on 2018/3/28.
 */

public class GlideEngine implements ImageEngine {

    @Override
    public void loadThumbnailForUri(Context context, Uri uri, int size, Drawable placeHolder, ImageView imageView) {
        Glide.with(context)
                .load(uri)
                .asBitmap()
                .override(size, size)
                .placeholder(placeHolder)
                .centerCrop()
                .into(imageView);
    }


    /**
     * todo 和第一个有区别么？
     *
     * @param context
     * @param uri
     * @param size
     * @param placeHolder
     * @param imageView
     */
    @Override
    public void loadGifThumbnailForUri(Context context, Uri uri, int size, Drawable placeHolder, ImageView imageView) {
        Glide.with(context)
                .load(uri)
                .asBitmap()
                .override(size, size)
                .centerCrop()
                .placeholder(placeHolder)
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, Uri uri, int sizeX, int sizeY, ImageView imageView) {
        Glide.with(context)
                .load(uri)
                .override(sizeX, sizeY)
                .priority(Priority.HIGH)
                .into(imageView);

    }

    @Override
    public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .asGif()
                .override(resizeX, resizeY)
                .priority(Priority.HIGH)
                .into(imageView);
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }


}
