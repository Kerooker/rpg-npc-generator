package me.kerooker.rpgnpcgenerator.repository.model.persistence

import android.content.Context
import android.util.Log
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import me.kerooker.rpgnpcgenerator.BuildConfig
import org.koin.core.scope.Scope
import org.koin.dsl.module

val objectBoxModule = module {
    single(createdAtStart = true) { createObjectBox() }
    single<NpcRepository>{ NpcBoxRepository(get<BoxStore>().boxFor(NpcEntity::class.java)) }
}

private fun Scope.createObjectBox(): BoxStore {
    val store = MyObjectBox.builder().androidContext(get<Context>()).build()

    if(BuildConfig.DEBUG) {
        val started = AndroidObjectBrowser(store).start(get())
        Log.i("ObjectBrowser", "Started: $started")
    }
    return store
}