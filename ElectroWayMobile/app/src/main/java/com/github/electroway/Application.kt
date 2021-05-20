package com.github.electroway;

import android.os.Handler
import android.os.Looper

class Application : android.app.Application() {
    val session = Session(Handler(Looper.getMainLooper()))
}
