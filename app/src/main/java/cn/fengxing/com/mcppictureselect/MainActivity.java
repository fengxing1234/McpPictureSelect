package cn.fengxing.com.mcppictureselect;

import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import cn.fengxing.com.mcppictureselect.internal.model.AlbumCollection;
import cn.fengxing.com.mcppictureselect.internal.ui.adapter.AlbumAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AlbumCollection.AlbumCallBack {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView btn_apple;
    private TextView btn_preview;
    private FrameLayout mContainer;
    private FrameLayout mCustomContainer;
    private FrameLayout mEmptyView;

    private AlbumCollection mAlbumCollection = new AlbumCollection();
    private AlbumAdapter mAlbumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        /**
         * 不知道什么作用
         */
        Drawable navigationIcon = toolbar.getNavigationIcon();
        TypedArray typedArray = getTheme().obtainStyledAttributes(new int[]{R.attr.album_element_color});
        int color = typedArray.getColor(0, 0);
        typedArray.recycle();
        //设置背景颜色
        navigationIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN);

        btn_preview = (TextView) findViewById(R.id.button_preview);
        btn_apple = (TextView) findViewById(R.id.button_apple);

        btn_apple.setOnClickListener(this);
        btn_apple.setOnClickListener(this);

        mContainer = (FrameLayout) findViewById(R.id.container);
        mCustomContainer = (FrameLayout) findViewById(R.id.custom_container);
        mEmptyView = (FrameLayout) findViewById(R.id.empty_view);

        mAlbumCollection.onCreate(this, this);

        mAlbumAdapter = new AlbumAdapter(this, null, false);

        mAlbumCollection.loadAlbums();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_preview:
                break;
            case R.id.button_apple:
                break;
        }
    }

    @Override
    public void onLoadFinished(Cursor cursor) {
        mAlbumAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset() {

    }
}
