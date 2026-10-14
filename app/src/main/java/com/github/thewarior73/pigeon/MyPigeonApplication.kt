package com.github.thewarior73.pigeon

import android.app.Application
import com.github.thewarior73.pigeon.local.BureauPosteDatabaseHolder

class MyPigeonApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        BureauPosteDatabaseHolder.initialize(this)
    }
}
