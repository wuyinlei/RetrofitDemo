package yinlei.com.retrofitdemo.ui.first;



/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: AppInfo.java
 * @author: myName
 * @date: 2016-07-30 14:57
 */
public class AppInfo implements Comparable<Object> {

    long mLastUpdateTime;
    String mName;
    String mIcon;

    public AppInfo(long lastUpdateTime, String name, String icon) {
        mLastUpdateTime = lastUpdateTime;
        mName = name;
        mIcon = icon;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getLastUpdateTime() {
        return mLastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        mLastUpdateTime = lastUpdateTime;
    }

    @Override
    public int compareTo(Object another) {
        AppInfo appInfo = (AppInfo) another;
        return getName().compareTo(appInfo.getName());
    }
}
