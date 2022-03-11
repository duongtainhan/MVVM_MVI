package com.dienty.structure.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

abstract class BaseActivity<Intent: ViewIntent, State: ViewState>: AppCompatActivity() {

    protected abstract val viewModel: BaseViewModel<Intent, State>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            observeState()
            initData()
            bindComponent()
            bindEvent()
            bindData()
        }
    }

    protected open fun initData() {}
    protected open fun bindComponent() {}
    protected open fun bindEvent() {}
    protected open fun bindData() {}

    protected abstract fun State.toUI()
    protected open fun observeState() {
        if (viewModel.state.hasObservers()) viewModel.resetState()
        viewModel.state.observe(this) { it.toUI() }
    }
}