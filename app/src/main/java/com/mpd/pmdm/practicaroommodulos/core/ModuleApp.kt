package com.mpd.pmdm.practicaroommodulos.core

import android.app.Application
import com.mpd.pmdm.practicaroommodulos.data.AppRepository
import com.mpd.pmdm.practicaroommodulos.data.database.ModuleDatabase
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ModuleApp(): Application()