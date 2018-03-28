package cn.fengxing.com.mcppictureselect.internal.model;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import cn.fengxing.com.mcppictureselect.internal.loader.AlbumsLoader;

/**
 * Created by fengxing on 2018/3/27.
 */

public class AlbumColloection extends LoaderManager {

    public AlbumColloection() {

    }

    @Override
    public <D> Loader<D> initLoader(int id, Bundle args, LoaderCallbacks<D> callback) {
        return AlbumsLoader.newInstance();
    }

    @Override
    public <D> Loader<D> restartLoader(int id, Bundle args, LoaderCallbacks<D> callback) {
        return null;
    }

    @Override
    public void destroyLoader(int id) {

    }

    @Override
    public <D> Loader<D> getLoader(int id) {
        return null;
    }

    @Override
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {

    }
}
