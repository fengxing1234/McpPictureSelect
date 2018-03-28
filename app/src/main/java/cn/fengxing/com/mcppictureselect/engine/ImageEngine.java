package cn.fengxing.com.mcppictureselect.engine;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by fengxing on 2018/3/28.
 */

public interface ImageEngine {

    /**
     * @param context
     * @param uri
     * @param size
     * @param placeHolder
     * @param imageView
     */
    void loadThumbnailForUri(Context context, Uri uri, int size, Drawable placeHolder, ImageView imageView);

    /**
     * 与第一个实现方式一样 没有发现不同的地方
     *
     * @param context
     * @param uri
     * @param size
     * @param placeHolder
     * @param imageView
     */
    void loadGifThumbnailForUri(Context context, Uri uri, int size, Drawable placeHolder, ImageView imageView);

    void loadImage(Context context, Uri uri, int sizeX, int sizeY, ImageView imageView);

    void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri);

    boolean supportAnimatedGif();
}
