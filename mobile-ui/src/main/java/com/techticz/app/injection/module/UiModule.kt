package com.techticz.app.injection.module

import com.gssirohi.techticz.voicebook.ui.voicebook.VoicebookActivity
import com.techticz.domain.executor.PostExecutionThread
import com.techticz.app.UiThread
import com.techticz.app.ui.home.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule{

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeVoiceBookActivity(): VoicebookActivity
}