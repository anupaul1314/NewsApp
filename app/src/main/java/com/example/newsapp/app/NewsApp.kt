package com.example.newsapp.app

import android.app.Application
import android.content.Context
import com.example.newsapp.localization.LocaleHelper
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApp: Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleHelper.setLocale(base, myLang))
    }
    companion object {
        var myLang = "en"
    }
}


