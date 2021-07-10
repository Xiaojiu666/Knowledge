package com.gx.utils.log

import android.content.Context
import android.os.Build.MODEL
import android.os.Build.VERSION.SDK_INT
import android.os.Process
import android.text.TextUtils
import com.gx.plugin_common.BuildConfig
import com.gx.utils.apk.AppInfoUtil.getAppName
import com.gx.utils.apk.AppInfoUtil.getVersionCode
import com.gx.utils.apk.AppInfoUtil.getVersionName
import com.tencent.mars.xlog.Log
import com.tencent.mars.xlog.Xlog
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

/**
 *
 */
object LogUtil {
    const val TAG = "LogUtil"
    fun initLog(logPath: String?, cachePath: String?) {
        val debug = isLogDebug
        if (BuildConfig.DEBUG || debug) {
            Xlog.appenderOpen(
                Xlog.LEVEL_DEBUG,
                Xlog.AppednerModeAsync,
                cachePath,
                logPath,
                TAG,
                ""
            )
            Xlog.setConsoleLogOpen(true)
        } else {
            Xlog.appenderOpen(
                Xlog.LEVEL_INFO,
                Xlog.AppednerModeAsync,
                cachePath,
                logPath,
                TAG,
                ""
            )
            Xlog.setConsoleLogOpen(false)
        }
        Log.setLogImp(Xlog())
        Log.appenderFlush(true)
        android.util.Log.w(
            TAG,
            String.format(
                "initLog debug: %b, logPath: %s, cachePath: %s, fileName: %s",
                false,
                logPath,
                cachePath,
                TAG
            )
        )
    }

    /**
     * 判断本地是否开启调试日志
     * 通过本地配置文件开启日志
     *
     * @return true开启
     */
    private val isLogDebug: Boolean
        private get() = true

    /**
     * 获取进程号对应的进程名
     *
     * @return 进程名
     */
    val processName: String
        get() {
            var reader: BufferedReader? = null
            try {
                reader =
                    BufferedReader(FileReader("/proc/" + Process.myPid() + "/cmdline"))
                var processName = reader.readLine()
                if (!TextUtils.isEmpty(processName)) {
                    processName = processName.trim { it <= ' ' }
                }
                val split = processName.split(":".toRegex()).toTypedArray()
                return if (split.size == 2) {
                    "[" + split[1] + "]"
                } else ""
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
            } finally {
                try {
                    reader?.close()
                } catch (exception: IOException) {
                    exception.printStackTrace()
                }
            }
            return ""
        }

    fun close() {
        Log.appenderClose()
    }

    /**
     * 使用默认的TAG 打印日志
     *
     * @param msg 日志内容
     */
    fun v(msg: String?) {
        v(TAG, msg)
    }

    /**
     * 根据提高的日志TAG和msg内容打印日志
     *
     * @param tag 日志tag
     * @param msg 日志内容
     */
    fun v(tag: String?, msg: String?) {
        v(tag, msg, null)
    }

    /**
     * 根据提供的日志TAG进行日志格式化输出
     *
     * @param tag    日志tag
     * @param format 格式化占位符
     * @param obj    格式化占位符替代内容
     */
    fun v(tag: String?, format: String?, vararg obj: Any?) {
        Log.v(tag, format, *obj)
    }

    /**
     * 使用默认的TAG 打印日志
     *
     * @param msg 日志内容
     */
    fun d(msg: String?) {
        d(TAG, msg)
    }

    /**
     * 根据提高的日志TAG和msg内容打印日志
     *
     * @param tag 日志tag
     * @param msg 日志内容
     */
    fun d(tag: String?, msg: String?) {
        d(tag, msg, null)
    }

    /**
     * 根据提供的日志TAG进行日志格式化输出
     *
     * @param tag    日志tag
     * @param format 格式化占位符
     * @param obj    格式化占位符替代内容
     */
    fun d(tag: String?, format: String?, vararg obj: Any?) {
        Log.d(tag, format, *obj)
    }

    /**
     * 使用默认的TAG 打印日志
     *
     * @param msg 日志内容
     */
    fun i(msg: String?) {
        i(TAG, msg)
    }

    /**
     * 根据提高的日志TAG和msg内容打印日志
     *
     * @param tag 日志tag
     * @param msg 日志内容
     */
    fun i(tag: String?, msg: String?) {
        i(tag, msg, null)
    }

    /**
     * 根据提供的日志TAG进行日志格式化输出
     *
     * @param tag    日志tag
     * @param format 格式化占位符
     * @param obj    格式化占位符替代内容
     */
    fun i(tag: String?, format: String?, vararg obj: Any?) {
        Log.i(tag, format, *obj)
    }

    /**
     * 使用默认的TAG 打印日志
     *
     * @param msg 日志内容
     */
    fun w(msg: String?) {
        w(TAG, msg)
    }

    /**
     * 根据提高的日志TAG和msg内容打印日志
     *
     * @param tag 日志tag
     * @param msg 日志内容
     */
    fun w(tag: String?, msg: String?) {
        w(tag, msg, null)
    }

    /**
     * 根据提供的日志TAG进行日志格式化输出
     *
     * @param tag    日志tag
     * @param format 格式化占位符
     * @param obj    格式化占位符替代内容
     */
    fun w(tag: String?, format: String?, vararg obj: Any?) {
        Log.w(tag, format, *obj)
    }

    /**
     * 使用默认的TAG 打印日志
     *
     * @param msg 日志内容
     */
    fun e(msg: String?) {
        e(TAG, msg)
    }

    /**
     * 根据提高的日志TAG和msg内容打印日志
     *
     * @param tag 日志tag
     * @param msg 日志内容
     */
    fun e(tag: String?, msg: String?) {
        e(tag, msg, null)
    }

    /**
     * 根据提供的日志TAG进行日志格式化输出
     *
     * @param tag    日志tag
     * @param format 格式化占位符
     * @param obj    格式化占位符替代内容
     */
    fun e(tag: String?, format: String?, vararg obj: Any?) {
        Log.e(tag, format, *obj)
    }

    /**
     * 打印程序异常日志
     *
     * @param tag    日志tag
     * @param tr     异常信息
     * @param format 格式化串
     * @param obj    格式化占位
     */
    fun printErrStackTrace(
        tag: String?,
        tr: Throwable?,
        format: String?,
        vararg obj: Any?
    ) {
        Log.printErrStackTrace(tag, tr, format, *obj)
    }

    /**
     * 打印程序堆栈日志
     *
     * @param tag 日志tag
     */
    fun printStackTrace(tag: String?) {
        val element =
            Thread.currentThread().stackTrace
        val sb = StringBuilder(512)
        sb.append("************************************************************").append("\n")
        for (traceElement in element) sb.append("\tat $traceElement")
            .append("\n")
        sb.append("************************************************************")
        android.util.Log.e(tag, sb.toString())
    }

    fun getLogUtilsTag(clazz: Class<out Any?>): String {
        return TAG + "." + clazz.simpleName
    }

    /**
     * 打印设备&APP 信息
     *
     * @param context
     */
    fun printHeader(context: Context?) {
        val versionName = getVersionName(context!!)
        val appName = getAppName(context)
        val versionCode = getVersionCode(context)
        val sb = StringBuilder()
        sb.append("\r\n")
        sb.append(" ---------- $appName  INFO  ---------------")
        sb.append("\r\n")
        sb.append(" DEVICE MODEL:[ $MODEL ] ")
        sb.append("\r\n")
        sb.append(" ANDROID VERSION:[ $SDK_INT ] ")
        sb.append("\r\n")
        sb.append(" APP VERSION NAME:[ $versionName ] ")
        sb.append("\r\n")
        sb.append(" APP VERSION CODE:[ $versionCode ] ")
        sb.append("\r\n")
        i(TAG, sb.toString())
    }

    init {
        System.loadLibrary("stlport_shared")
        System.loadLibrary("marsxlog")
    }
}