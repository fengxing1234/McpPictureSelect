package cn.fengxing.com.mcppictureselect.internal.utils;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by fengxing on 2018/3/28.
 */

public class CursorUtils {

    private static final String TAG = "CursorUtils";

    public static void showDataLog(Cursor cursor) {
        while (cursor.moveToNext()) {
            int columnCount = cursor.getColumnCount();
            Log.d(TAG, "columnCount : " + columnCount);

            int idInt = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
            String id = cursor.getString(idInt);

            int bucket_id = cursor.getColumnIndex("bucket_id");
            String bucketId = cursor.getString(bucket_id);

            int bucket_display_name = cursor.getColumnIndex("bucket_display_name");
            String bucketDisplayName = cursor.getString(bucket_display_name);

            int dataInt = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);
            String data = cursor.getString(dataInt);

            int countInt = cursor.getColumnIndex("count");
            String count = cursor.getString(countInt);

            Log.d(TAG, " id : " + id + " bucketId: " + bucketId + " bucketDisplayName : " + bucketDisplayName + " data : " + data + " count :" + count);

        }
    }
}
