package com.sn.config

import com.sn.utils.apk.AppInfoUtil

object AppFileConfig {

    var FILE_ROOT_PATH = ""

    var FILE_LOG = "$FILE_ROOT_PATH/log"
    var FILE_XLOG = "$FILE_LOG/xlog"
    var FILE_XLOG_CACHE ="$FILE_LOG/cache"

    fun initRootPath(name: String) {
        FILE_ROOT_PATH = name
        FILE_LOG = "$FILE_ROOT_PATH/log"
        FILE_XLOG = "$FILE_LOG/xlog"
        FILE_XLOG_CACHE ="$FILE_LOG/cache"
    }
}