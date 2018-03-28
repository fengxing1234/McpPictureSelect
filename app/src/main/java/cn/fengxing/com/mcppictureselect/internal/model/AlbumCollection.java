package cn.fengxing.com.mcppictureselect.internal.model;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import java.lang.ref.WeakReference;

import cn.fengxing.com.mcppictureselect.internal.loader.AlbumsLoader;
import cn.fengxing.com.mcppictureselect.internal.utils.CursorUtils;

/**
 * Created by fengxing on 2018/3/27.
 */

public class AlbumCollection implements LoaderManager.LoaderCallbacks<Cursor> {


    public static final int LOADER_ID = 1;
    private WeakReference<Context> mContext;
    private LoaderManager mLoaderManager;
    private AlbumCallBack mCallbacks;

    public void onCreate(Activity activity, AlbumCallBack callback) {
        mContext = new WeakReference<Context>(activity);
        mLoaderManager = activity.getLoaderManager();
        mCallbacks = callback;
    }

    public void loadAlbums() {
        mLoaderManager.initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Context context = mContext.get();
        if (context == null) {
            return null;
        }
        return AlbumsLoader.newInstance(context);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Context context = mContext.get();
        if (context == null) {
            return;
        }
        if (mCallbacks != null) {
            mCallbacks.onLoadFinished(data);
        }
        CursorUtils.showDataLog(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void onDestory() {
        mLoaderManager.destroyLoader(LOADER_ID);
        mCallbacks = null;
    }

    public interface AlbumCallBack {
        void onLoadFinished(Cursor cursor);

        void onLoaderReset();

    }
}
