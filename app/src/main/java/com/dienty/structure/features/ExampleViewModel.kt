package com.dienty.structure.features

import com.dienty.structure.base.BaseViewModel
import com.dienty.structure.base.ViewIntent
import com.dienty.structure.base.ViewState
import com.dienty.structure.data.model.Restaurant
import com.dienty.structure.data.response.Resource
import com.dienty.structure.data.useCase.ExampleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExampleViewModel @Inject constructor(private val useCase: ExampleUseCase) :
    BaseViewModel<ExampleViewModel.ExampleIntent, ExampleViewModel.ExampleState>() {

    sealed class ExampleState : ViewState {
        data class Loading(val intent: ExampleIntent? = null) : ExampleState()
        data class Error(val message: String, val intent: ExampleIntent? = null) : ExampleState()
        data class Done(val intent: ExampleIntent? = null) : ExampleState()

        data class ResultRestaurants(val data: List<Restaurant>) : ExampleState()
    }

    sealed class ExampleIntent : ViewIntent {
        object GetExampleData : ExampleIntent()
    }

    override fun dispatchIntent(intent: ExampleIntent) {
        safeLaunch {
            when (intent) {
                is ExampleIntent.GetExampleData -> {
                    useCase.getRestaurants().collect {
                        _state.value = it.reduce(intent = intent) { data ->
                            ExampleState.ResultRestaurants(data = data)
                        }
                    }
                }
            }
        }
    }

    override fun <T> Resource<T>.reduce(
        intent: ExampleIntent?,
        successFun: (T) -> ExampleState
    ): ExampleState {
        return when (this) {
            is Resource.Loading -> ExampleState.Loading(intent = intent)
            is Resource.Error -> ExampleState.Error(message = this.errorMsg ?: "", intent = intent)
            is Resource.Done -> ExampleState.Done(intent = intent)
            is Resource.Success -> successFun(this.data)
        }
    }
}