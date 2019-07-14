package com.gojek.mygojekapp.mapper

/**
 * Interface for model mappers. It provides helper methods that facilitate
 * retrieving of models from outer layers
 *
 * @param <V> the view input type
 * @param <D> the view model output type
 */
interface UiMapper<out V, in D> {

    fun mapToUiModel(type: D): V

}