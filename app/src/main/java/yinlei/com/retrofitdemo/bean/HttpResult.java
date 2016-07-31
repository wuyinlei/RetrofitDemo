package yinlei.com.retrofitdemo.bean;

public class HttpResult<T> {

    //count: 30,
    //err: 0,
    //total: 128,
    // page: 1,
    //refresh: 128
    private int count;
    private int err;
    private int total;
    private int page;
    private int refresh;

    //用来模仿Data
    private T items;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRefresh() {
        return refresh;
    }

    public void setRefresh(int refresh) {
        this.refresh = refresh;
    }

    public T getSubjects() {
        return items;
    }

    public void setSubjects(T subjects) {
        this.items = subjects;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "count=" + count +
                ", err=" + err +
                ", total=" + total +
                ", page=" + page +
                ", refresh=" + refresh +
                ", subjects=" + items +
                '}';
    }
}
