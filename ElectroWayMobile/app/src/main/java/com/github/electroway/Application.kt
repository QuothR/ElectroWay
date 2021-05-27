package com.github.electroway;

import android.os.Handler
import android.os.Looper
import com.github.electroway.network.Session

class Application : android.app.Application() {
    val session = Session(Handler(Looper.getMainLooper()))
}
