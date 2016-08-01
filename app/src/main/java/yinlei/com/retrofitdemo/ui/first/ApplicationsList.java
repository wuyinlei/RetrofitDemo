package yinlei.com.retrofitdemo.ui.first;

import java.util.List;


public class ApplicationsList {

    private static ApplicationsList ourInstance = new ApplicationsList();

    private List<AppInfo> mList;

    public List<AppInfo> getList() {
        return mList;
    }

    public void setList(List<AppInfo> list) {
        mList = list;
    }

    private ApplicationsList() {
    }

    public static ApplicationsList getInstance() {
        return ourInstance;
    }
}
