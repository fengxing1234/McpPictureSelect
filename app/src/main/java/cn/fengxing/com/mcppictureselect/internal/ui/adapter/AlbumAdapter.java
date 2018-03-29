package cn.fengxing.com.mcppictureselect.internal.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import cn.fengxing.com.mcppictureselect.R;
import cn.fengxing.com.mcppictureselect.engine.impl.GlideEngine;
import cn.fengxing.com.mcppictureselect.internal.entity.Album;

/**
 * Created by fengxing on 2018/3/26.
 * 专辑标题Adapter
 */

public class AlbumAdapter extends CursorAdapter {

    public AlbumAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.albums_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Album album = Album.valueOf(cursor);
        ((TextView) view.findViewById(R.id.album_name)).setText(album.getDisplayName(context));
        ((TextView) view.findViewById(R.id.album_media_count)).setText(String.valueOf(album.getCount()));
        ImageView imageView = (ImageView) view.findViewById(R.id.album_cover);

        new GlideEngine().loadThumbnailForUri(context, Uri.fromFile(new File(album.getCoverPath())), context.getResources().getDimensionPixelSize(R.dimen.media_grid_size), context.getResources().getDrawable(R.mipmap.ic_launcher), imageView);
    }
}
