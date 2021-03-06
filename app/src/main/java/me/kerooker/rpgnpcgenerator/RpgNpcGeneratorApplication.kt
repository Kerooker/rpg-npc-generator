package me.kerooker.rpgnpcgenerator

import android.app.Application
import android.provider.Settings
import com.google.android.gms.ads.MobileAds
import me.kerooker.rpgnpcgenerator.legacy.repository.LegacyNpcImporter
import me.kerooker.rpgnpcgenerator.legacy.repository.LegacyNpcRepository
import me.kerooker.rpgnpcgenerator.repository.model.persistence.persistenceModule
import me.kerooker.rpgnpcgenerator.repository.model.random.npc.npcGeneratorsModule
import me.kerooker.rpgnpcgenerator.repository.model.random.npc.randomNpcModule
import me.kerooker.rpgnpcgenerator.viewmodel.viewModelsModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

var isFirebaseDevice = false
private set

class RpgNpcGeneratorApplication : Application() {

    private val legacyNpcImporter by inject<LegacyNpcImporter>()
        
    override fun onCreate() {
        super.onCreate()

        startKoinModules()
        legacyNpcImporter.importAll()
        initializeAds()
        checkFirebaseDevice()
    }

    private fun startKoinModules() {
        startKoin {
            androidContext(this@RpgNpcGeneratorApplication)
            modules(listOf(mainModule,
                           randomNpcModule, persistenceModule, viewModelsModule, npcGeneratorsModule))
        }
    }
    
    private fun initializeAds() {
        MobileAds.initialize(this)
    }
    
    private fun checkFirebaseDevice() {
        isFirebaseDevice = Settings.System.getString(contentResolver, "firebase.test.lab") == "true"
    }
}

val mainModule = module {
    single { LegacyNpcRepository() }
    single { LegacyNpcImporter(get(), get(), get()) }
}
