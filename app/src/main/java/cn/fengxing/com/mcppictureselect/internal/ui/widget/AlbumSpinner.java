package cn.fengxing.com.mcppictureselect.internal.ui.widget;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import cn.fengxing.com.mcppictureselect.R;
import cn.fengxing.com.mcppictureselect.internal.entity.Album;
import cn.fengxing.com.mcppictureselect.internal.utils.Platform;

/**
 * Created by fengxing on 2018/3/29.
 */

public class AlbumSpinner {

    private static final String TAG = "AlbumSpinner";

    private final ListPopupWindow mListPopup;

    private static final int MAX_SHOW_COUNT = 6;

    private CursorAdapter mAdapter;

    private TextView mSelectView;

    //private AdapterView.OnItemSelectedListener mSelectedListener;

    public AlbumSpinner(Context context, View view) {
        mListPopup = new ListPopupWindow(context, null, R.attr.listPopupWindowStyle);
        mListPopup.setModal(true);
        mListPopup.setAnchorView(view);
        float density = context.getResources().getDisplayMetrics().density;
        mListPopup.setContentWidth((int) (216 * density));
        mListPopup.setHorizontalOffset((int) (16 * density));
        mListPopup.setVerticalOffset((int) (-48 * density));
        mListPopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: " + position);
                onItemSelected(parent.getContext(), position);
//                if (mSelectedListener != null) {
//                    mSelectedListener.onItemSelected(parent, view, position, id);
//                }
            }
        });
    }

//    public void setSelectedListener(AdapterView.OnItemSelectedListener selectedListener) {
//        mSelectedListener = selectedListener;
//    }

    public void setSelection(Context context, int position) {
        mListPopup.setSelection(position);
        onItemSelected(context, position);
    }

    /**
     * 选中某个item
     * 关闭ListPopup
     * 更改标题
     *
     * @param context
     * @param position
     */
    private void onItemSelected(Context context, int position) {
        mListPopup.dismiss();
        Cursor cursor = mAdapter.getCursor();
        cursor.moveToPosition(position);
        Album album = Album.valueOf(cursor);
        String displayName = album.getDisplayName(context);
        if (mSelectView.getVisibility() == View.VISIBLE) {
            mSelectView.setText(displayName);
        } else {
            if (Platform.hasICS()) {
                mSelectView.setAlpha(0.0f);
                mSelectView.setVisibility(View.VISIBLE);
                mSelectView.setText(displayName);
                mSelectView.animate().alpha(1.0f).setDuration(context.getResources().getInteger(android.R.integer.config_longAnimTime)).start();
            } else {
                mSelectView.setVisibility(View.VISIBLE);
                mSelectView.setText(displayName);
            }
        }

    }

    public void setAdapter(CursorAdapter adapter) {
        mListPopup.setAdapter(adapter);
        mAdapter = adapter;
    }

    public void setSelectTextView(TextView view) {
        mSelectView = view;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemHeight = v.getResources().getDimensionPixelSize(R.dimen.album_item_height);
                mListPopup.setHeight(mAdapter.getCount() > MAX_SHOW_COUNT ? itemHeight * MAX_SHOW_COUNT : mAdapter.getCount() * itemHeight);
                mListPopup.show();
            }
        });
    }
}
