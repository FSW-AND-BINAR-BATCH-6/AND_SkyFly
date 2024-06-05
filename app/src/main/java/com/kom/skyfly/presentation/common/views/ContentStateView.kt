package com.kom.skyfly.presentation.common.views

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.kom.skyfly.R
import com.kom.skyfly.databinding.LayoutContentStateBinding

/**
Written by Komang Yuda Saputra
Github : https://github.com/YudaSaputraa
 **/
class ContentStateView
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
    ) : ConstraintLayout(context, attrs, defStyleAttr) {
        private var binding: LayoutContentStateBinding

        private var listener: ContentStateListener? = null

        init {
            inflate(context, R.layout.layout_content_state, this)
            binding = LayoutContentStateBinding.bind(this)
        }

        fun setListener(listener: ContentStateListener) {
            this.listener = listener
        }

        fun setState(
            state: ContentState,
            message: String? = null,
            @DrawableRes imgRes: Int? = null,
        ) {
            when (state) {
                ContentState.SUCCESS -> {
                    binding.root.isVisible = false
                    binding.ivError.isVisible = false
                    binding.tvError.isVisible = false
                    listener?.onContentShow(true)
                }

                ContentState.EMPTY -> {
                    binding.root.isVisible = true
                    binding.ivError.isVisible = true
                    binding.tvError.isVisible = true

                    imgRes?.let { binding.ivError.setImageResource(it) }
                        ?: run { binding.ivError.setImageResource(R.drawable.img_empty_data) }
                    message?.let { binding.tvError.text = it } ?: run {
                        binding.tvError.text = context.getString(R.string.text_empty_data)
                    }
                    listener?.onContentShow(false)
                }

                ContentState.ERROR_NETWORK -> {
                    binding.root.isVisible = true
                    binding.ivError.isVisible = true
                    binding.tvError.isVisible = true
                    imgRes?.let { binding.ivError.setImageResource(it) }
                        ?: run { binding.ivError.setImageResource(R.drawable.img_network_error) }
                    message?.let { binding.tvError.text = it } ?: run {
                        binding.tvError.text = context.getString(R.string.no_internet_connection)
                    }
                    listener?.onContentShow(false)
                }

                ContentState.ERROR_GENERAL -> {
                    binding.root.isVisible = true
                    binding.ivError.isVisible = true
                    binding.tvError.isVisible = true
                    imgRes?.let { binding.ivError.setImageResource(it) }
                        ?: run { binding.ivError.setImageResource(R.drawable.img_general_error) }
                    message?.let { binding.tvError.text = it } ?: run {
                        binding.tvError.text =
                            context.getString(R.string.error_when_getting_the_data_please_try_again_later)
                    }
                    listener?.onContentShow(false)
                }
            }
        }
    }

interface ContentStateListener {
    fun onContentShow(isContentShow: Boolean)
}

enum class ContentState {
    SUCCESS,
    EMPTY,
    ERROR_NETWORK,
    ERROR_GENERAL,
}
