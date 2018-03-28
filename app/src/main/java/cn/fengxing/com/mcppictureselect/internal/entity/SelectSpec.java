package cn.fengxing.com.mcppictureselect.internal.entity;

/**
 * Created by fengxing on 2018/3/27.
 */

public class SelectSpec {

    private SelectSpec() {
    }

    public static SelectSpec newInstance() {
        return new SelectSpec();
    }

    public boolean onlyShowImages() {
        return false;
    }

    public boolean onlyShowVideos() {
        return false;
    }
}
