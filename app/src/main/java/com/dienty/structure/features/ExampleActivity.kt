package com.dienty.structure.features

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dienty.structure.R
import com.dienty.structure.base.BaseActivity
import com.dienty.structure.common.viewBinding
import com.dienty.structure.custom.PrefetchRecycledViewPool
import com.dienty.structure.data.model.Restaurant
import com.dienty.structure.databinding.ActivityExampleBinding
import com.dienty.structure.databinding.ItemExampleBinding
import com.dienty.structure.extensions.getDimensInt
import com.dienty.structure.extensions.hide
import com.dienty.structure.extensions.show
import com.dienty.structure.extensions.toast
import com.dienty.structure.features.ExampleViewModel.ExampleIntent.GetExampleData
import com.dienty.structure.features.ExampleViewModel.ExampleState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ExampleActivity : BaseActivity<ExampleViewModel.ExampleIntent, ExampleViewModel.ExampleState>() {

    private lateinit var binding: ActivityExampleBinding
    override val viewModel: ExampleViewModel by viewModels()
    private val adapter by lazy { ExampleAdapter() }
    private lateinit var vhPool: PrefetchRecycledViewPool
    private val amountItemToPrefetch by lazy { 5 }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        if (this::vhPool.isInitialized) vhPool.clear()
        super.onDestroy()
    }

    override fun initData() {
        vhPool = PrefetchRecycledViewPool(this, lifecycleScope).apply { prepare() }
        if (this::vhPool.isInitialized) {
            vhPool.setViewsCount(0, 30) { parent, _ ->
                ExampleAdapter.ExampleViewHolder(
                    parent.viewBinding(
                        ItemExampleBinding::inflate
                    ), adapter.eventListener
                )
            }
        }
    }

    override fun bindComponent() {
        binding.rvExample.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom = getDimensInt(com.intuit.sdp.R.dimen._5sdp)
                }
            })
            setRecycledViewPool(vhPool)
            adapter = this@ExampleActivity.adapter
        }
    }

    override fun bindData() {
        adapter.bind(data = List(amountItemToPrefetch) { Restaurant() })
        viewModel.dispatchIntent(GetExampleData)
    }

    override fun ExampleViewModel.ExampleState.toUI() {
        when (this) {
            is Loading -> binding.loading.show()
            is Error -> {
                binding.loading.hide()
                toast(this.message)
            }
            is Done -> binding.loading.hide()

            is ResultRestaurants -> adapter.add(data = this.data, isBind = true)
        }
    }
}
