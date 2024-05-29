package com.kom.skyfly.presentation.account.editprofile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kom.skyfly.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {
    private val binding: ActivityEditProfileBinding by lazy {
        ActivityEditProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }
}
