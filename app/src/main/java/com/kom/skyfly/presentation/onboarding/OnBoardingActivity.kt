package com.kom.skyfly.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroCustomLayoutFragment
import com.github.appintro.AppIntroPageTransformerType
import com.kom.skyfly.R
import com.kom.skyfly.presentation.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardingActivity : AppIntro2() {
    private val onBoardingViewModel: OnBoardingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addSlide(
            AppIntroCustomLayoutFragment.newInstance(R.layout.layout_on_boarding_first),
        )
        addSlide(
            AppIntroCustomLayoutFragment.newInstance(R.layout.layout_on_boarding_second),
        )
        addSlide(
            AppIntroCustomLayoutFragment.newInstance(R.layout.layout_on_boarding_third),
        )
        setTransformer(
            AppIntroPageTransformerType.Parallax(
                titleParallaxFactor = 1.0,
                imageParallaxFactor = -1.0,
                descriptionParallaxFactor = 2.0,
            ),
        )

        isIndicatorEnabled = true

        setIndicatorColor(
            selectedIndicatorColor = getColor(R.color.white),
            unselectedIndicatorColor = getColor(R.color.grey),
        )
        if (onBoardingViewModel.isOnBoardingShow()) {
            navigateToHome()
        }
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        navigateToHome()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        navigateToHome()
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
