package com.sn.plugin_common.utils.log;


import android.text.TextUtils;


import com.sn.plugin_common.BuildConfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 */
public class LogUtil {

    public static final String TAG = "AccountBook.xlog";

    static {
        System.loadLibrary("c++_shared");
        System.loadLibrary("marsxlog");
    }

    public static void initLog(String logPath,String cachePath) {
//        boolean debug = isLogDebug();
        Xlog.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, logPath, TAG, "");
//        if (BuildConfig.DEBUG) {
//            Xlog.appenderOpen(Xlog.LEVEL_DEBUG, Xlog.AppednerModeAsync, cachePath, logPath, TAG, "");
//            Xlog.setConsoleLogOpen(true);
//
//        } else {
//            Xlog.appenderOpen(Xlog.LEVEL_INFO, Xlog.AppednerModeAsync, cachePath, logPath, TAG, "");
//            Xlog.setConsoleLogOpen(false);
//        }
//
//        Log.setLogImp(new Xlog());
//        Log.appenderFlush(true);
//        android.util.Log.w(TAG, String.format("initLog debug: %b, logPath: %s, cachePath: %s, fileName: %s", false, logPath, cachePath, TAG));
    }

    /**
     * 判断本地是否开启调试日志
     *
     * @return true开启
     */
//    private static boolean isLogDebug() {
//        BufferedReader reader = null;
//        try {
//            File conf = new File(FileAccessor.APPS_ROOT_DIR + File.separator + "logConfig.cnf");
//            if (!conf.exists()) {
//                boolean ret = conf.createNewFile();
//                android.util.Log.i(TAG, "createNewFile ret:" + ret);
//            }
//            reader = new BufferedReader(new FileReader(conf));
//            String line = reader.readLine();
//            if (!TextUtil.isEmpty(line)) {
//                return "true".equals(line);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            CloseQuality.close(reader);
//        }
//        return false;
//    }

    /**
     * 获取进程号对应的进程名
     *
     * @return 进程名
     */
    public static String getProcessName() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + android.os.Process.myPid() + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            String[] split = processName.split(":");
            if (split.length == 2) {
                return "[" + split[1] + "]";
            }
            return "";
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return "";
    }

    public static void close() {
        Log.appenderClose();
    }

    /**
     * 使用默认的TAG 打印日志
     *
     * @param msg 日志内容
     */
    public static void v(String msg) {
        v(TAG, msg);
    }

    /**
     * 根据提高的日志TAG和msg内容打印日志
     *
     * @param tag 日志tag
     * @param msg 日志内容
     */
    public static void v(String tag, String msg) {
        v(tag, msg, (Object[]) null);
    }

    /**
     * 根据提供的日志TAG进行日志格式化输出
     *
     * @param tag    日志tag
     * @param format 格式化占位符
     * @param obj    格式化占位符替代内容
     */
    public static void v(String tag, final String format, final Object... obj) {
        Log.v(tag, format, obj);
    }

    /**
     * 使用默认的TAG 打印日志
     *
     * @param msg 日志内容
     */
    public static void d(String msg) {
        d(TAG, msg);
    }

    /**
     * 根据提高的日志TAG和msg内容打印日志
     *
     * @param tag 日志tag
     * @param msg 日志内容
     */
    public static void d(String tag, String msg) {
        d(tag, msg, (Object[]) null);
    }

    /**
     * 根据提供的日志TAG进行日志格式化输出
     *
     * @param tag    日志tag
     * @param format 格式化占位符
     * @param obj    格式化占位符替代内容
     */
    public static void d(String tag, final String format, final Object... obj) {
        Log.d(tag, format, obj);
    }

    /**
     * 使用默认的TAG 打印日志
     *
     * @param msg 日志内容
     */
    public static void i(String msg) {
        i(TAG, msg);
    }

    /**
     * 根据提高的日志TAG和msg内容打印日志
     *
     * @param tag 日志tag
     * @param msg 日志内容
     */
    public static void i(String tag, String msg) {
        i(tag, msg, (Object[]) null);
    }

    /**
     * 根据提供的日志TAG进行日志格式化输出
     *
     * @param tag    日志tag
     * @param format 格式化占位符
     * @param obj    格式化占位符替代内容
     */
    public static void i(String tag, final String format, final Object... obj) {
        Log.i(tag, format, obj);
    }

    /**
     * 使用默认的TAG 打印日志
     *
     * @param msg 日志内容
     */
    public static void w(String msg) {
        w(TAG, msg);
    }

    /**
     * 根据提高的日志TAG和msg内容打印日志
     *
     * @param tag 日志tag
     * @param msg 日志内容
     */
    public static void w(String tag, String msg) {
        w(tag, msg, (Object[]) null);
    }

    /**
     * 根据提供的日志TAG进行日志格式化输出
     *
     * @param tag    日志tag
     * @param format 格式化占位符
     * @param obj    格式化占位符替代内容
     */
    public static void w(String tag, final String format, final Object... obj) {
        Log.w(tag, format, obj);
    }

    /**
     * 使用默认的TAG 打印日志
     *
     * @param msg 日志内容
     */
    public static void e(String msg) {
        e(TAG, msg);
    }

    /**
     * 根据提高的日志TAG和msg内容打印日志
     *
     * @param tag 日志tag
     * @param msg 日志内容
     */
    public static void e(String tag, String msg) {
        e(tag, msg, (Object[]) null);
    }

    /**
     * 根据提供的日志TAG进行日志格式化输出
     *
     * @param tag    日志tag
     * @param format 格式化占位符
     * @param obj    格式化占位符替代内容
     */
    public static void e(String tag, final String format, final Object... obj) {
        Log.e(tag, format, obj);
    }

    /**
     * 打印程序异常日志
     *
     * @param tag    日志tag
     * @param tr     异常信息
     * @param format 格式化串
     * @param obj    格式化占位
     */
    public static void printErrStackTrace(String tag, Throwable tr, final String format, final Object... obj) {
        Log.printErrStackTrace(tag, tr, format, obj);
    }


    /**
     * 打印程序堆栈日志
     *
     * @param tag 日志tag
     */
    public static void printStackTrace(String tag) {
        StackTraceElement[] element = Thread.currentThread().getStackTrace();
        StringBuilder sb = new StringBuilder(512);
        sb.append("************************************************************").append("\n");
        for (StackTraceElement traceElement : element)
            sb.append("\tat " + traceElement).append("\n");
        ;
        sb.append("************************************************************");
        android.util.Log.e(tag, sb.toString());
    }


    public static String getLogUtilsTag(Class<? extends Object> clazz) {
        return LogUtil.TAG + "." + clazz.getSimpleName();
    }
}
