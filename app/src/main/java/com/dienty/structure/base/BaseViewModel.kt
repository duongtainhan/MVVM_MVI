package com.dienty.structure.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dienty.structure.data.response.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel<Intent : ViewIntent, State: ViewState> : ViewModel(){

    // use the state to save the variable temporality
    protected val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State> = _state

    protected fun safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
        }
        viewModelScope.launch(coroutineExceptionHandler) { block() }
    }

    open fun resetState() {}
    abstract fun dispatchIntent(intent: Intent)
    abstract fun <T> Resource<T>.reduce(intent: Intent ?= null, successFun: (T) -> State) : State
}