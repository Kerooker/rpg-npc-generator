package me.kerooker.rpgnpcgenerator.viewmodel

import me.kerooker.rpgnpcgenerator.viewmodel.random.npc.RandomNpcViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { RandomNpcViewModel(get(), get(), get(), get(), get()) }
}
