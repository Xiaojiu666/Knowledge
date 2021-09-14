package com.gx.task.di.demo

import javax.inject.Inject

class Computer @Inject constructor(var cpu: CPU) {
    var phoneName = "Mac"
}