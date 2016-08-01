package yinlei.com.retrofitdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: yinlei.com.retrofitdemo.Utils.java
 * @author: myName
 * @date: 2016-07-30 15:24
 */

public class Utils {

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static void storeBitmap(Context context, Bitmap bitmap, String filename) {
        Schedulers.io().createWorker().schedule(() -> {
            blockingStoreBitmap(context, bitmap, filename);
        });
    }

    private static void blockingStoreBitmap(Context context, Bitmap bitmap, String filename) {
        FileOutputStream fOut = null;
        try {
            fOut = context.openFileOutput(filename, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fOut != null) {
                    fOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 把日期毫秒转化为字符串
     *
     * @param millis  要转化的日期毫秒数。
     * @param pattern 要转化为的字符串格式（如：yyyy-MM-dd HH:mm:ss）。
     * @return 返回日期字符串。
     */
    public static String millisToStringDate(long millis, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern,
                Locale.getDefault());
        return format.format(new Date(millis));
    }

    /**
     * 把日期毫秒转化为字符串（文件名）
     *
     * @param millis  要转化的日期毫秒数。
     * @param pattern 要转化为的字符串格式（如：yyyy-MM-dd HH:mm:ss）。
     * @return 返回日期字符串（yyyy_MM_dd_HH_mm_ss）。
     */
    public static String millisToStringFilename(long millis, String pattern) {
        String dateStr = millisToStringDate(millis, pattern);
        return dateStr.replaceAll("[- :]", "_");
    }


    /**
     * @param millis   要转化的毫秒数。
     * @param isWhole  是否强制全部显示小时/分。
     * @param isFormat 时间数字是否要格式化，如果true：少位数前面补全；如果false：少位数前面不补全。
     * @return 返回时间字符串：小时/分/秒/毫秒的格式（如：24903600 --> 06小时55分钟）。
     */
    public static String millisToStringShort(long millis, boolean isWhole,
                                             boolean isFormat) {
        String h = "";
        String m = "";
        if (isWhole) {
            h = isFormat ? "00小时" : "0小时";
            m = isFormat ? "00分钟" : "0分钟";
        }

        long temp = millis;

        long hper = 60 * 60 * 1000;
        long mper = 60 * 1000;
        long sper = 1000;

        if (temp / hper > 0) {
            if (isFormat) {
                h = temp / hper < 10 ? "0" + temp / hper : temp / hper + "";
            } else {
                h = temp / hper + "";
            }
            h += "小时";
        }
        temp = temp % hper;

        if (temp / mper > 0) {
            if (isFormat) {
                m = temp / mper < 10 ? "0" + temp / mper : temp / mper + "";
            } else {
                m = temp / mper + "";
            }
            m += "分钟";
        }

        return h + m;
    }


    /**
     * 转换当前时间为易用时间格式
     * 1小时内用，多少分钟前； 超过1小时，显示时间而无日期； 如果是昨天，则显示昨天 超过昨天再显示日期； 超过1年再显示年。
     */
    public static long oneHourMillis = 60 * 60 * 1000; // 一小时的毫秒数
    public static long oneDayMillis = 24 * oneHourMillis; // 一天的毫秒数
    public static long oneYearMillis = 365 * oneDayMillis; // 一年的毫秒数

    public static String millisToLifeString(long millis) {
        long now = System.currentTimeMillis();
        long todayStart = string2Millis(millisToStringDate(now, "yyyy-MM-dd"),
                "yyyy-MM-dd");

        // 一小时内
        if (now - millis <= oneHourMillis && now - millis > 0l) {
            String m = millisToStringShort(now - millis, false, false);
            return "".equals(m) ? "1分钟内" : m + "前";
        }

        // 大于今天开始开始值，小于今天开始值加一天（即今天结束值）
        if (millis >= todayStart && millis <= oneDayMillis + todayStart) {
            return "今天 " + millisToStringDate(millis, "HH:mm");
        }

        // 大于（今天开始值减一天，即昨天开始值）
        if (millis > todayStart - oneDayMillis) {
            return "昨天 " + millisToStringDate(millis, "HH:mm");
        }

        long thisYearStart = string2Millis(millisToStringDate(now, "yyyy"),
                "yyyy");
        // 大于今天小于今年
        if (millis > thisYearStart) {
            return millisToStringDate(millis, "MM月dd日 HH:mm");
        }

        return millisToStringDate(millis, "yyyy年MM月dd日 HH:mm");
    }


    /**
     * 字符串解析成毫秒数
     *
     * @param str
     * @param pattern
     * @return
     */
    public static long string2Millis(String str, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern,
                Locale.getDefault());
        long millis = 0;
        try {
            millis = format.parse(str).getTime();
        } catch (ParseException e) {
            Log.e("TAG", e.getMessage());
        }
        return millis;
    }

    /**
     * 获取文件名
     *
     * @return
     */
    public static Observable<File> getFileDir() {
        return Observable.create(subscriber -> {
            subscriber.onNext(App.instance.getFilesDir());
            subscriber.onCompleted();
        });
    }


}
