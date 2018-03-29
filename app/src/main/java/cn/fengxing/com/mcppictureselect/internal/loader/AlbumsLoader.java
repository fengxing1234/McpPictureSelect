package cn.fengxing.com.mcppictureselect.internal.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import cn.fengxing.com.mcppictureselect.internal.entity.Album;
import cn.fengxing.com.mcppictureselect.internal.entity.SelectSpec;


/**
 * Created by fengxing on 2018/3/27.
 * <p>
 * <p>
 * sql查询语句：
 * <p>
 * Select _id,_data,bucket_id,bucket_display_name,COUNT(*) AS count from files where (media_type ="1" or media_type ="2") and _size>0 GROUP BY bucket_id ORDER BY datetaken DESC;
 */

public class AlbumsLoader extends CursorLoader {

    private static final String TAG = "AlbumsLoader";


    public static final String COUNT = "count";
    /**
     * URI查询地址
     */
    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    // TODO: 2018/3/27 测试是否URI是否一样
    private static final Uri QUERY_IMAGE_URI = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    private static final Uri QUERY_VIDEO_URI = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
    private static final Uri QUERY_AUDIO_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    /**
     * PROJECTION 需要查询的列 也就是返回的字段
     */
    private static final String[] PROJECTION = new String[]{
            MediaStore.Files.FileColumns._ID,//主键。图片 id，从 1 开始自增
            MediaStore.Files.FileColumns.DATA,//图片绝对路径
            //MediaStore.Files.FileColumns.SIZE,//文件大小，单位为 byte
            //MediaStore.Files.FileColumns.DISPLAY_NAME,//文件名
            //MediaStore.Files.FileColumns.MIME_TYPE,//类似于 image/jpeg 的 MIME 类型
            //MediaStore.Files.FileColumns.MEDIA_TYPE,//多媒体类型，图片，视频，音频，0代表没有多媒体文件
            "bucket_id",//等于 path.toLowerCase.hashCode()，见 MediaProvider.computeBucketValues()这个是我们这里主要找的字段相册的ID，该ID和下一个表中的ID是对应的
            "bucket_display_name",//直接包含图片的文件夹就是该图片的 bucket，就是文件夹名
            "COUNT(*) AS " + COUNT
    };

    private static final String[] COLUMNS = new String[]{
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DATA,
            "bucket_id",
            "bucket_display_name",
            COUNT
    };

    /**
     * Selection 设置条件，相当于SQL语句中的where。null表示不进行筛选。
     */
    //显示 图片 和 视频 类型
    private static final String SELECTION =
            "(" +
                    MediaStore.Files.FileColumns.MEDIA_TYPE + "=?" +
                    " or " +
                    MediaStore.Files.FileColumns.MEDIA_TYPE + "=?" +
                    ")" + " AND " +//细心的人会发现 >0 后面多个）  bucket_id前面多个( 如果不加上 会发现 报错，错误提示少（，我猜测是系统加入的。解决方法 上面有sql 和错误比对 很容易找到问题。
                    MediaStore.Files.FileColumns.SIZE + ">0)" + " GROUP BY (bucket_id";

    //只显示单一类型 图片 或者 视频
    private static final String SELECTION_FOR_SINGLE_IMAGE_TYPE =
            MediaStore.Files.FileColumns.MEDIA_TYPE + "=?" + "and" +
                    MediaStore.Files.FileColumns.SIZE + ">0)";

    /**
     * selectionArgs，这个参数是要配合selection参数使用的，如果你在第三个参数里面有？，那么你在selectionArgs写的数据就会替换掉？，
     */
    private static final String[] SELECTION_ARGS = new String[]{
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO)
    };

    /**
     * sortOrder，按照什么进行排序，相当于SQL语句中的Order by。
     */
    private static final String SORT_ORDER = "datetaken DESC";


    private AlbumsLoader(Context context, String selection, String[] selectionArgs) {
        super(context, QUERY_URI, PROJECTION, selection, selectionArgs, SORT_ORDER);
        Log.d(TAG, "AlbumsLoader: ");
    }

    //// TODO: 2018/3/27 没有按照知乎做 校验一下 结果是否正确
    public static AlbumsLoader newInstance(Context context) {
        String select;
        String[] selectArgs;
        SelectSpec selectSpec = SelectSpec.newInstance();
        if (selectSpec.onlyShowImages()) {
            select = SELECTION_FOR_SINGLE_IMAGE_TYPE;
            selectArgs = getSelectionArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE);
        } else if (selectSpec.onlyShowVideos()) {
            select = SELECTION_FOR_SINGLE_IMAGE_TYPE;
            selectArgs = getSelectionArgsForSingleMediaType(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO);
        } else {
            select = SELECTION;
            selectArgs = SELECTION_ARGS;
        }
        return new AlbumsLoader(context, select, selectArgs);
    }

    private static String[] getSelectionArgsForSingleMediaType(int type) {
        return new String[]{String.valueOf(type)};
    }

    @Override
    protected Cursor onLoadInBackground() {
        /**
         * 这段代码意思是：插入一条数据，显示的内容是全部照片的数量
         */
        Cursor cursor = super.onLoadInBackground();
        long totalCount = 0;
        String allAlbumCoverPath = "";
        MatrixCursor matrixCursor = new MatrixCursor(COLUMNS);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                totalCount += cursor.getInt(cursor.getColumnIndex(COUNT));
            }

            if (cursor.moveToFirst()) {
                allAlbumCoverPath = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
            }

        }
        matrixCursor.addRow(new String[]{Album.ALBUM_ALL_ID, allAlbumCoverPath, Album.ALBUM_ALL_ID, Album.ALBUM_ALL_NAME, String.valueOf(totalCount)});

        return new MergeCursor(new Cursor[]{matrixCursor, cursor});
    }


    @Override
    public void onContentChanged() {
        super.onContentChanged();
    }
}
