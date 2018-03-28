package cn.fengxing.com.mcppictureselect.internal.entity;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import cn.fengxing.com.mcppictureselect.R;
import cn.fengxing.com.mcppictureselect.internal.loader.AlbumsLoader;

/**
 * Created by fengxing on 2018/3/26.
 * <p>
 * 此类封装的是 专辑标题 实体类
 * <p>
 * Parcelable :   https://blog.csdn.net/justin_1107/article/details/72903006
 */

public class Album implements Parcelable {

    /**
     * 负责反序列化
     */
    private static final Creator<Album> CREATOR = new Creator<Album>() {
        /**
         * 从序列化对象中，获取原始的对象
         * @param source
         * @return
         */
        @Override
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        /**
         * 创建指定长度的原始对象数组
         * @param size
         * @return
         */
        @Override
        public Album[] newArray(int size) {
            return new Album[0];
        }
    };


    public static final String ALBUM_ALL_ID = String.valueOf(-1);
    public static final String ALBUM_ALL_NAME = "All";
    /**
     * bucket_id
     */
    private final String mId;
    /**
     * 相当于data
     */
    private final String mCoverPath;
    /**
     * 相当于 bucket_display_name
     */
    private final String mDisplayName;
    private final long mCount;


    Album(String id, String coverPath, String displayName, long count) {
        mId = id;
        mCoverPath = coverPath;
        mDisplayName = displayName;
        mCount = count;
    }

    Album(Parcel source) {
        mId = source.readString();
        mCoverPath = source.readString();
        mDisplayName = source.readString();
        mCount = source.readLong();
    }

    public static Album valueOf(Cursor cursor) {
        return new Album(
                cursor.getString(cursor.getColumnIndex("bucket_id")),
                cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)),
                cursor.getString(cursor.getColumnIndex("bucket_display_name")),
                cursor.getInt(cursor.getColumnIndex(AlbumsLoader.COUNT))
        );
    }

    /**
     * 描述
     * 返回的是内容的描述信息
     * 只针对一些特殊的需要描述信息的对象,需要返回1,其他情况返回0就可以
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 序列化
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mCoverPath);
        dest.writeString(mDisplayName);
        dest.writeLong(mCount);
    }


    public String getId() {
        return mId;
    }

    public String getCoverPath() {
        return mCoverPath;
    }

    public String getDisplayName(Context context) {
        if (isAll()) {
            return context.getString(R.string.album_name_all);
        }
        return mDisplayName;
    }

    public boolean isAll() {
        return ALBUM_ALL_NAME.equals(mDisplayName);
    }

    public long getCount() {
        return mCount;
    }
}
