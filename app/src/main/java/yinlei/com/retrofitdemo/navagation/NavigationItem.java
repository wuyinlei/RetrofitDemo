package yinlei.com.retrofitdemo.navagation;

import android.graphics.drawable.Drawable;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: yinlei.com.retrofitdemo.navagation.NavigationItem.java
 * @author: myName
 * @date: 2016-07-30 15:54
 */

public class NavigationItem {
    private String mText;

    private Drawable mDrawable;

    public NavigationItem(String text, Drawable drawable) {
        mText = text;
        mDrawable = drawable;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        mDrawable = drawable;
    }
}
