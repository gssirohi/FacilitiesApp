package com.techticz.app.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.techticz.presentation.ViewModelFactory
import com.techticz.presentation.viewmodel.facility.FacilityViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value:KClass<out ViewModel>)

@Module
abstract class PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(FacilityViewModel::class)
    abstract fun bindFacilityViewModel(viewModel: FacilityViewModel):ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}