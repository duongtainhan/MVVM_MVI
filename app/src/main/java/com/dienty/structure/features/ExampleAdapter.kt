package com.dienty.structure.features

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dienty.structure.R
import com.dienty.structure.base.BaseAdapter
import com.dienty.structure.base.BaseEvent
import com.dienty.structure.common.viewBinding
import com.dienty.structure.data.model.Restaurant
import com.dienty.structure.databinding.ItemExampleBinding

class ExampleAdapter : BaseAdapter<Restaurant, ExampleAdapter.ExampleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ExampleViewHolder(parent.viewBinding(ItemExampleBinding::inflate), eventListener)

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        holder.bind(item = differ.currentList[position])
    }

    class ExampleViewHolder(
        private val binding: ItemExampleBinding,
        private val eventListener: BaseEvent<Restaurant>?
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                eventListener?.onClickedItem(absoluteAdapterPosition)
            }
        }

        fun bind(item: Restaurant) {
            binding.apply {
                tvName.isEnabled = item.name.isNotBlank()
                tvType.isEnabled = item.name.isNotBlank()
                tvPhone.isEnabled = item.phone.isNotBlank()
                tvAddress.isEnabled = item.address.isNotBlank()
                // bind data
                tvName.text = item.name
                tvType.text = item.type
                tvPhone.text = item.phone
                tvAddress.text = item.address
                if (item.id.isBlank()) {
                    Glide.with(root).load(R.drawable.ic_place_holder).into(ivLogo)
                } else {
                    Glide.with(root)
                        .load("https://loremflickr.com/320/240?random=${absoluteAdapterPosition + 1}")
                        .placeholder(R.drawable.ic_place_holder)
                        .transform(CenterCrop())
                        .error(R.drawable.ic_error_image)
                        .transform(RoundedCorners(root.context.resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._10sdp)))
                        .into(ivLogo)
                }
            }
        }
    }
}